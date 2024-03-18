package com.hamilton.account.ui.signin.usecase

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamilton.entity.accounts.Account
import com.hamilton.firebase.AuthFirebase
import com.hamilton.invoice.Locator
import com.hamilton.network.Resource
import kotlinx.coroutines.launch

class SignInViewModel: ViewModel() {

    val TAG = "ViewModel"

    //Live Data que controlan los datos
    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()

    //LiveData que tendrá su observador en el fragment y controla las excepciones/casos de uso de la operación login
    private var state = MutableLiveData<SignInState>()

    /**
     * Esta función se ejecuta directamente en el código xml usando el databinding
     */
    fun validateCredentials() {

        when {
            TextUtils.isEmpty(email.value) -> state.value = SignInState.EmailEmptyError
            TextUtils.isEmpty(password.value) -> state.value = SignInState.PasswordEmptyError
            else -> {
                //Se crea una corrutina que suspende el hilo principal hasta que el bloque
                // withContext del repositorio termina de ejecutarse
                viewModelScope.launch {
                    //Vamos a ejecutar el login del repositorio que pregunta a la capa de la infraestructura
                    state.value = SignInState.Loading(true)

                    val authfirebase: AuthFirebase = AuthFirebase()
                    val result = authfirebase.login(email.value!!, password.value!!)

                    state.value = SignInState.Loading(false)

                    when (result) {
                        is Resource.Success<*> -> {
                            //Aquí tenemos que hacer un casting seguro porque el tipo de dato es genérico
                            val account = result.data as Account
                            Log.i(TAG, "Información del dato ${result.data}")
                            state.value = SignInState.Success(result.data as Account)

                            //Guardar la información del usuario en el almacén de datos user_preferences
                            Locator.userPreferencesRepository.saveUser(account.email.value, account.password.toString(), account.id.value)
                        }
                        is Resource.Error -> {
                            Log.i(TAG, "Información del dato ${result.exception.message}")
                            state.value =
                                SignInState.AutenthicationError(result.exception.message!!)
                        }
                    }
                }
            }
        }
    }

    /**
     * Se crea un getter para la variable state, pero no permitimos que nadie pueda modificar su valor fuera del ViewModel
     */
    fun getState() : LiveData<SignInState> {
        return state
    }
}