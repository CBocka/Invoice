package com.hamilton.invoice.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

//Función de extensión invocable desde cualquier activity o fragmento desde su activity
//La podemos llamar desde donde sea haciendo requireActivity().showToast("")
fun Context.showToast(message : String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

object Utils {
    fun showSnackBar(message : String, view : View) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }
}