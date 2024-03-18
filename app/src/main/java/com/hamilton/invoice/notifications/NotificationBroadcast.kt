package com.hamilton.invoice.notifications

import android.Manifest
import android.app.Activity
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.hamilton.invoice.MainActivity
import com.hamilton.invoice.R

class NotificationBroadcast(val context: Context) :
    BroadcastReceiver() {
    var title: String = "Title"
    var message: String = "Message"

    companion object {
        const val NOTIFICATION_ID = 1
        const val REQUEST_NOTIFICATION_PERMISSION = 1001
    }

    override fun onReceive(context: Context, intent: Intent?) {
        createSimpleNotification()
    }

    /**
    * Método que envia una notificación
    * */
    private fun createSimpleNotification() {
        if (hasNotificationPermission()) {
            // Tenemos el permiso para enviar notificaciones
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            val flag =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
            val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, flag)


            val notification = NotificationCompat.Builder(context, MainActivity.MY_CHANNEL_ID)
                .setSmallIcon(R.drawable.invoices)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            val manager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(NOTIFICATION_ID, notification)
        } else {
            //No tengo el permiso
            Log.d("Notification", "No hay permisos para mostrar notificaciones")
        }
    }

    /**
    * Método que devuelve True si tengo permiso para enviar notificaciones
    * y false en caso contrario
    * */
    fun hasNotificationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
    * Método en el que compruebo si no tengo los permisos para enviar las notificaciones
    * y si no es el caso se los solicito al usuario
    * */
    fun requestNotificationPermission() {
        if (!hasNotificationPermission()) {
            if (context is Activity) {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    REQUEST_NOTIFICATION_PERMISSION
                )
            }
        }
    }
}