package com.hamilton.account.ui.userlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hamilton.account.R
import com.hamilton.account.adapter.UserAdapter
import com.hamilton.account.databinding.FragmentUserListBinding
import com.hamilton.account.ui.userlist.usecase.UserListState
import com.hamilton.account.ui.userlist.usecase.UserListViewModel
import com.hamilton.entity.users.User
import com.hamilton.invoice.MainActivity
import com.hamilton.invoice.base.BaseFragmentDialog
import com.hamilton.invoice.utils.showToast

class UserListFragment : Fragment(), UserAdapter.OnUserClick, MenuProvider {

    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!

    private val viewmodel : UserListViewModel by viewModels()

    private lateinit var userAdapter : UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpToolBar()

        setUpUserRecycler()

        viewmodel.getState().observe(viewLifecycleOwner, Observer{
            when(it) {
                UserListState.NoDataError -> showNoDataError()
                is UserListState.Success -> onSuccess()
                else -> {}
            }
        })
        //Este observador se ejecutara SIEMPRE que haya cambios en la tabla
        //user de la base de datos. El adapter se actualiza a través del
        //Comparator del adapter
        viewmodel.alluser.observe(viewLifecycleOwner) {
            it.let { userAdapter.submitList(it) }
        }

        viewmodel.getUserList()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setUpToolBar() {
        //Modismo apply de kotlin. No hace falta ni crear la variable usando el modismo apply
        (requireActivity() as MainActivity).toolbar.apply {
            visibility = View.VISIBLE
        }

        val menuhost : MenuHost = requireActivity()
        menuhost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_user_list, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.action_refresh -> {
                viewmodel.getUserList()
                return true
            }
            R.id.action_sort -> {
                userAdapter.sort()
                return true
            }
            else -> return false
        }
    }

    private fun showNoDataError() {
        binding.animationViewItemList.visibility = View.VISIBLE
        binding.rvUser.visibility = View.GONE
    }

    private fun onSuccess() {
        binding.animationViewItemList.visibility = View.GONE
        binding.rvUser.visibility = View.VISIBLE
    }

    /**
     * Funcion que inicializa el RecyclerView que muestra el listado de usuarios de la app
     */
    private fun setUpUserRecycler(){
        //Crear el Adapter con los valores en el constructor primario
        userAdapter = UserAdapter (this)

        //1. ¿Como quiero que se muestren los elementos de la lista?
        with(binding.rvUser){
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter=userAdapter
        }
    }

    /**
     * Esta funcion se llama de forma asincrona cuando el usuario pulse sobre un elemento del RecyclerView
     */
    override fun userClick(user: User) {
        requireActivity().showToast("Pulsacion cota en el usuario $user")
    }

    override fun userOnLongClick(user: User) {
        val dialog = BaseFragmentDialog.newInstance(
            "Atención",
            "¿Seguro que quiere eliminar el usuario?"
        )

        dialog.show((context as AppCompatActivity).supportFragmentManager, "TAG")

        dialog.parentFragmentManager.setFragmentResultListener(
            BaseFragmentDialog.request, viewLifecycleOwner
        ) { _, bundle ->
            val result = bundle.getBoolean(BaseFragmentDialog.result)
            if (result) {
                viewmodel.delete(user)

                viewmodel.getUserList()

                Toast.makeText(requireContext(), "Usuario borrado con éxito", Toast.LENGTH_SHORT).show()
            }
        }
    }
}