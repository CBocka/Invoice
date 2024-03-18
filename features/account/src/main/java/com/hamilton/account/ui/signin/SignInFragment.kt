package com.hamilton.account.ui.signin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.hamilton.account.R
import com.hamilton.account.databinding.FragmentAccountSignInBinding
import com.hamilton.account.ui.signin.usecase.SignInState
import com.hamilton.account.ui.signin.usecase.SignInViewModel

class SignInFragment : Fragment() {

    private val viewModel : SignInViewModel by viewModels()

    private var _binding: FragmentAccountSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountSignInBinding.inflate(inflater, container, false)

        //Pasamos a la interfaz la instancia del ViewModel para que actualice los valores del email y password
        binding.viewmodel = this.viewModel //viewmodel es la variable que hemos creado en el xml para el databinding, aquí se la estamos "pasando" a la interfaz
        binding.lifecycleOwner = this //Esta línea es clave para que funcione el databinding

        binding.tieEmailSignIn.addTextChangedListener(SignInErrorsWatcher(binding.tilEmailSignIn))
        binding.tiePasswordSignIn.addTextChangedListener(SignInErrorsWatcher(binding.tilPasswordSignIn))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegisterAccount.setOnClickListener {
            findNavController().navigate(R.id.action_SignInFragment_to_SignUpFragment)
        }

        viewModel.getState().observe(viewLifecycleOwner, Observer {
            when (it) {
                SignInState.EmailEmptyError -> setEmailEmptyError()
                SignInState.PasswordEmptyError -> setPasswordEmptyError()
                //El is se pone cuando es data class
                is SignInState.AutenthicationError -> showMessage(it.message)
                is SignInState.Loading -> showProgressbar(it.value)
                else -> onSuccess()
            }
        })
    }

    fun setEmailEmptyError() {
        binding.tilEmailSignIn.error = getString(R.string.errEmailEmpty)
        binding.tilEmailSignIn.requestFocus()
    }

    fun setPasswordEmptyError() {
        binding.tilPasswordSignIn.error = getString(R.string.errEmailEmpty)
        binding.tilPasswordSignIn.requestFocus()
    }

    fun showProgressbar(value : Boolean) {
        if (value)
            findNavController().navigate(R.id.action_signInFragment_to_fragmentProgressDialog)
        else
            findNavController().popBackStack()
    }

    /**
     * Función que muestra al usuario un mensaje
     */
    fun showMessage(message: String) {
        val action = SignInFragmentDirections.actionSignInFragmentToBaseFragmentDialog("Error", message)

        findNavController().navigate(action)
    }

    fun onSuccess() {
        Toast.makeText(requireActivity(), "Éxito en el login", Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Clase para crear instancias watcher
     * Es interna para acceder a las propiedades y funciones de la clase externa
     */
    inner class SignInErrorsWatcher(val til : TextInputLayout) : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            til.error = null
        }
    }
}