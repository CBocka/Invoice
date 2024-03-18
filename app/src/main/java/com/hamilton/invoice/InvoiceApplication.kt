package com.hamilton.invoice

import android.app.Application
import com.google.firebase.FirebaseApp

class InvoiceApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        //Se inicializan las preferencias y DataStore
        Locator.initWith(this)

        //Se inicializa la conexión a Firebase
        FirebaseApp.initializeApp(this)
    }
}