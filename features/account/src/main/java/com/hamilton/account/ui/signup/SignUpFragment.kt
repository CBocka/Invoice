package com.hamilton.account.ui.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.hamilton.account.R
import com.hamilton.account.databinding.FragmentAccountSignUpBinding
import com.hamilton.account.ui.signup.usercase.SignUpState
import com.hamilton.account.ui.signup.usercase.SignUpViewModel

class SignUpFragment : Fragment() {
    private var _binding : FragmentAccountSignUpBinding? = null
    private val binding get() = _binding!!

    val viewModel : SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Usamos esta función para inicializar o dar lógica a componentes que ya se han creado
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        initSpinners()

        binding.tieNameSignUp.addTextChangedListener(SignUpErrorsWatcher(binding.tilNameSignUp))
        binding.tieEmailSignUp.addTextChangedListener(SignUpErrorsWatcher(binding.tilEmailSignUp))
        binding.tiePasswordSignUp.addTextChangedListener(SignUpErrorsWatcher(binding.tilPasswordSignUp))
        binding.tieConfirmPasswordSignUp.addTextChangedListener(SignUpErrorsWatcher(binding.tilConfirmPasswordSignUp))

        viewModel.getState().observe(viewLifecycleOwner, Observer {
            when (it) {
                SignUpState.DisplayNameEmptyError -> setDisplayNameEmptyError()
                SignUpState.EmailEmptyError -> setEmailEmptyError()
                SignUpState.EmailFormatError -> setEmailFormatError()
                SignUpState.PasswordEmptyError -> setPasswordEmptyError()
                SignUpState.RepeatPasswordError -> setRepeatPasswordEmptyError()
                SignUpState.PasswordNoEqualError -> setPasswordNoEqualError()
                SignUpState.EmailRepeat -> showError()
                SignUpState.Success -> onSuccess()
            }
        })

        binding.btnCreateAccount.setOnClickListener {

            viewModel.spProfile = binding.spProfile.selectedItem.toString()
            viewModel.spUserType = binding.spType.selectedItem.toString()

            viewModel.validateUser()
        }
    }

    private fun initSpinners() {

        val userType = arrayOf("User", "Admin", "Client", "Guest")
        val userProfile = arrayOf("Public", "Private", "Empty")

        val spUserType: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, userType)
        val spUserProfile: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, userProfile)

        spUserType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spUserProfile.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spType.adapter = spUserType
        binding.spProfile.adapter = spUserProfile
    }

    private fun showError() {
        Toast.makeText(requireContext(),"EMAIL EXISTENTE",Toast.LENGTH_SHORT).show()
    }

    private fun setDisplayNameEmptyError() {
        binding.tilNameSignUp.error = "Debes introducir un nombre"
        binding.tilNameSignUp.requestFocus()
    }

    private fun setEmailEmptyError() {
        binding.tilEmailSignUp.error = getString(R.string.errEmailEmpty)
        binding.tilEmailSignUp.requestFocus()
    }

    private fun setEmailFormatError() {
        binding.tilEmailSignUp.error = getString(R.string.errEmailFormat)
        binding.tilEmailSignUp.requestFocus()
    }

    private fun setPasswordEmptyError() {
        binding.tilPasswordSignUp.error = getString(R.string.errPasswordEmpty)
        binding.tilPasswordSignUp.requestFocus()
    }

    private fun setRepeatPasswordEmptyError() {
        binding.tilConfirmPasswordSignUp.error = getString(R.string.errConfirmPasswordEmpty)
        binding.tilConfirmPasswordSignUp.requestFocus()
    }

    private fun setPasswordNoEqualError() {
        binding.tilConfirmPasswordSignUp.error = getString(R.string.errPasswordNoEqual)
        binding.tilConfirmPasswordSignUp.requestFocus()
    }

    private fun onSuccess() {

        Toast.makeText(requireContext(), "Usuario añadido con éxito", Toast.LENGTH_SHORT).show()

        findNavController().navigateUp()
    }

    inner class SignUpErrorsWatcher(val til : TextInputLayout) : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            til.error = null
        }
    }
}
