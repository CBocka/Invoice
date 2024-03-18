package com.hamilton.firebase

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.hamilton.entity.accounts.Account
import com.hamilton.entity.accounts.AccountId
import com.hamilton.entity.accounts.AccountState
import com.hamilton.entity.accounts.Email
import com.hamilton.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthFirebase() {

    private var authFirebase = FirebaseAuth.getInstance()
    suspend fun login(email : String, password: String) : Resource {
            //Este código se ejecuta en un hilo especifico para acciones de E/S
            //delay(3000) //Simulamos que esperamos respuesta del servidor
            //Se ejecutará el código necesario para realizar la consulta a Firebase que podría tardar más de 5 segundos.
            // Si eso pasa se obtiene el error ANR (Android Not Responding) porque podría bloquear la vista al usuario
            return withContext(Dispatchers.IO){
                try {
                    val authResult: AuthResult =
                        authFirebase.signInWithEmailAndPassword(email, password).await()
                    val user = authResult.user
                    val account: Account = Account.create(
                        AccountId(user.hashCode()),
                        Email(email),
                        password,
                        user?.displayName,
                        1,
                        AccountState.VERIFIED
                    )
                    Resource.Success(account)
                } catch (e: Exception) {
                    Resource.Error(e)
                }
            }
        }

    }

