package com.hamilton.account.ui.userlist.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.hamilton.entity.users.User
import com.hamilton.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserListViewModel: ViewModel() {

    private var state = MutableLiveData<UserListState>()
    //La secuencia de usuarios, esta vincualada al ciclo de vida del ViewModel
    var alluser:LiveData<List<User>> =  UserRepository.getUserList().asLiveData()

    fun getState():LiveData<UserListState>{
        return state
    }
    /**
     * Función que pide el listado de usuarios al repostiorio
     * */
    fun getUserList() {
        when{
            alluser.value?.isEmpty() == true -> state.value = UserListState.NoDataError
            else -> state.value = UserListState.Success
        }
    }

    fun delete(user : User) {
        UserRepository.delete(user)
    }
}