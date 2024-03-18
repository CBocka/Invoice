package com.hamilton.account.ui.signup.usercase

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamilton.repository.UserRepository
import com.hamilton.entity.users.User
import com.hamilton.entity.accounts.Email
import com.hamilton.entity.users.UserProfile
import com.hamilton.entity.users.UserType
import com.hamilton.network.Resource
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    var displayName = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var repeatPassword = MutableLiveData<String>()

    lateinit var spProfile : String
    lateinit var spUserType: String

    private var state = MutableLiveData<SignUpState>()

    fun getState(): LiveData<SignUpState> {
        return this.state
    }

    fun validateUser() {

        viewModelScope.launch {

            when {
                TextUtils.isEmpty(displayName.value) -> state.value = SignUpState.DisplayNameEmptyError
                TextUtils.isEmpty(email.value) -> state.value = SignUpState.EmailEmptyError
                !Email.validateEmail(email.value!!) -> state.value = SignUpState.EmailFormatError
                TextUtils.isEmpty(password.value) -> state.value = SignUpState.PasswordEmptyError
                TextUtils.isEmpty(repeatPassword.value) -> state.value = SignUpState.RepeatPasswordError
                password.value != repeatPassword.value -> state.value = SignUpState.PasswordNoEqualError
                else -> {

                    val userType = when(spUserType) {
                        "User" -> UserType.USER
                        "Admin" -> UserType.ADMIN
                        "Client" -> UserType.CLIENT
                        "Guest" -> UserType.GUEST
                        else -> UserType.UNDEFINED
                    }

                    val userProfile = when(spProfile) {
                        "Public" -> UserProfile.PUBLIC
                        "Private" -> UserProfile.PRIVATE
                        else -> UserProfile.EMPTY
                    }

                    val result = UserRepository.insert(
                        User(
                            email.value.toString(),
                            password.value.toString(),
                            displayName.value.toString(),
                            userType,
                            userProfile
                        )
                    )
                    when (result) {
                        is Resource.Success<*> -> {
                            state.value = SignUpState.Success
                        }

                        is Resource.Error -> {
                            state.value = SignUpState.EmailRepeat
                            Log.i("_______", result.exception.message!!)
                        }
                    }
                }
            }
        }
    }
}




