package com.grt.pokemon.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.grt.pokemon.R
import com.grt.pokemon.common.BaseActivity
import com.grt.pokemon.common.NavData
import com.grt.pokemon.data.UtilsPokemons
import com.grt.pokemon.databinding.ActivityMainBinding
import com.grt.pokemon.domain.model.PokemonModel
import com.grt.pokemon.ui.dialog.DialogData
import com.grt.pokemon.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 * Actividad principal de la App
 * En ella se inicializan los menos, se maneja la pulsación del botón de descarga
 * se gestiona que el usuario tenga internet cuando quiere descargar dicha lista
 */
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val vm by viewModel<HomeViewModel>()

    private val vmMain by viewModel<MainViewModel>()

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.appBarMain.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)

        with(binding){
            // Botón para descargar los pokemon otra vez
            appBarMain.fab.setOnClickListener { view ->
                comprobarConnectionInternet()
            }

            appBarConfiguration = AppBarConfiguration(
                setOf(R.id.nav_home, R.id.nav_favorites, R.id.nav_profile, R.id.nav_gallery, R.id.nav_superfavorito), drawerLayout
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)

        }

        observeData(vmMain.obsShowFab,::onObserveFab)
        observeData(vmMain.obsNavigate,::onObserveNav)
    }

    // Método privado para comprobar la conexión a internet.
    // Si el usuario no tiene conexión, mostramos un mensaje de aviso indicándolo
    // y no se podrá descargar de nuevo la lista de Pokemon de internet
    private fun comprobarConnectionInternet(){
        if (UtilsPokemons.isNetworkAvailable(this)){
            vm.onActionDownloadClicked()
        } else {
            vm.showDialogWarning("Acción no Permitida. No tiene conexión a internet.")
        }
    }

    private fun onObserveNav(navData: NavData?) {
        navData?.also {

        }?:also {
            if(!findNavController(R.id.nav_host_fragment_content_main).navigateUp())
                finish()
        }
    }

    private fun onObserveFab(show: Boolean) {
        binding.appBarMain.fab.visibility = if(show) View.VISIBLE else View.INVISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        hideKeyboard()

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun showLoading() {
        binding.appBarMain.flMainLoading.visibility = View.VISIBLE
    }

    fun hideLoading() {
        binding.appBarMain.flMainLoading.visibility = View.GONE
    }
}