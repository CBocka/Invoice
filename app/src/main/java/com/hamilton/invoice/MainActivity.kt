package com.hamilton.invoice

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import com.hamilton.invoice.databinding.ActivityMainBinding
import com.hamilton.invoice.notifications.NotificationBroadcast

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    val toolbar: Toolbar get() = binding.content.toolbar

    val drawer: DrawerLayout get() = binding.drawerLayout

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        val darkTheme: Boolean = Locator.settingsPreferencesRepository.getBoolean(
            getString(R.string.key_dark_theme),
            false
        )
        setTheme(darkTheme)

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.content.toolbar)

        //DRAWER Opción 1:
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //supportActionBar?.setHomeButtonEnabled(true)
        //supportActionBar?.setHomeAsUpIndicator(android.R.drawable.ic_menu_info_details)
        //navController = findNavController(R.id.nav_host_fragment_content_main)

        //DRAWER Opción 2:
        //1. Métodos que permiten acceder al controlador del grafo de navegación
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration =
            AppBarConfiguration.Builder(navController.graph).setOpenableLayout(binding.drawerLayout)
                .build()
        //2. Que sincronice el DrawerLayout con la AppBar
        NavigationUI.setupWithNavController(binding.content.toolbar, navController)

        //Configurar evento click del menu nav_view
        setUpNavigationView()

        //Notificaciones
        createChannel()

        //Solicito los permiso al principio, si no los tengo
        val alarmManager = NotificationBroadcast(this)
        alarmManager.requestNotificationPermission()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                navController.navigate(R.id.settingsFragment)
                true
            }

            android.R.id.home -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun setAppBarGone() {
        supportActionBar!!.hide()
    }

    fun setAppBarVisible() {
        supportActionBar!!.show()
    }

    fun updateAppBar(title: String?) {
        setAppBarVisible()
        supportActionBar!!.title = title
    }

    fun setTheme(darkTheme: Boolean) {
        when (darkTheme) {
            true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    /**
     * Implementar el listener de las opciones del menú del componente nav_view
     */
    private fun setUpNavigationView() {
        binding.navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_invoice -> {
                    navController.navigate(R.id.nav_graph_invoice)
                }

                R.id.action_item -> {
                    navController.navigate(R.id.nav_graph_item)
                }

                R.id.action_customer -> {
                    navController.navigate(R.id.nav_graph_customer)
                }

                R.id.action_task -> {
                    navController.navigate(R.id.nav_graph_task)
                }

                else -> {}
            }

            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START))
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()  //Se deja que el sistema operativo haga uso de la opción predeterminada
    }

    companion object {
        const val MY_CHANNEL_ID = "myActivity"
    }

    /**
     * Método que crea un canal de notificaciones
     */
    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                MY_CHANNEL_ID,
                "GrupoHamilton",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Hamilton Channel"
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)

            Log.d("Notification", "El canal se crea correctamente")
        }
    }

    fun sendNotification(message : String, title : String) {
        val notificationBroadcast = NotificationBroadcast(this)
        notificationBroadcast.title = title
        notificationBroadcast.message = message
        notificationBroadcast.onReceive(this, null)
    }
}