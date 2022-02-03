package com.grt.pokemon.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.grt.pokemon.common.BaseViewModel
import com.grt.pokemon.domain.usecase.*
import com.grt.pokemon.ui.dialog.DialogData
import android.util.Patterns
import com.grt.pokemon.domain.model.PokemonModel
import java.util.regex.Pattern


/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 * ViewModel encargado de Traer del DataStore los datos del perfil almacenados y para su posterior
 * mostrado en el fragmento. También contiene funciones que se encargan de validar si los datos son
 * correctos
 *
 */
class ProfileViewModel(
    private val getProfileNameUseCase: GetProfileNameUseCase,
    private val getProfileSurnameUseCase: GetProfileSurnameUseCase,
    private val getProfileEmailUseCase: GetProfileEmailUseCase,
    private val getProfilePokStarUseCase: GetProfilePokStarUseCase,
    private val getProfileIdPokStarUseCase: GetProfileIdPokStarUseCase,
    private val saveProfileNameUseCase: SaveProfileNameUseCase,
    private val saveProfileSurnameUseCase: SaveProfileSurnameUseCase,
    private val saveProfileEmailUseCase: SaveProfileEmailUseCase,
    private val saveProfilePokStarUseCase: SaveProfilePokStarUseCase,
    private val saveProfileIdPokStarUseCase: SaveProfileIdPokStarUseCase
): BaseViewModel() {

    private val liveListPokemons: MutableLiveData<List<PokemonModel>> = MutableLiveData()
    val obsListPokemons: LiveData<List<PokemonModel>> = liveListPokemons

    private val liveName = MutableLiveData<String>()
    val obsName: LiveData<String> = liveName

    val obsSurname: LiveData<String> =
        getProfileSurnameUseCase.executeSyncInCurrentThread(Unit).asLiveData()

    val obsEmail: LiveData<String> =
        getProfileEmailUseCase.executeSyncInCurrentThread(Unit).asLiveData()

    val obsNamePokSuperStar: LiveData<String> =
        getProfilePokStarUseCase.executeSyncInCurrentThread(Unit).asLiveData()

    override fun onInitialization() {
        onShowFab(false)
        executeUseCase {
            val name = getProfileNameUseCase.execute(Unit)
            liveName.value = name

        }
    }

    // Si los datos del perfil a almacenar son correctos, se guarda dicho Perfil en DataStore
    fun onActionSaveProfileClick(name: String, surname:String, email:String, namePokSuperStar:String, idPokSuperStar:String) {
        if (datosCorrectos(name, surname, email, namePokSuperStar)){
            savedPerfil(name, surname, email, namePokSuperStar, idPokSuperStar)
        }
    }

    // Función creada para comprobar que los datos del Perfil a almacenar son correctos.
    private fun datosCorrectos(name: String, surname:String, email:String, namePokSuperStar:String):Boolean {
        if (name == "") {
            liveShowDialog.value = DialogData(true,"El campo Nombre está vacío")
            return false
        } else if (surname == "") {
            liveShowDialog.value = DialogData(true,"El campo Apellidos está vacío")
            return false
        } else if (!validarEmail(email) || email=="") {
            liveShowDialog.value = DialogData(true,"Introduzca un Email correcto")
            return false
        } else if (namePokSuperStar == "") {
            liveShowDialog.value = DialogData(true,"No ha seleccionado al Súper Pokemon")
            return false
        }
        return true
    }

    // Función encargada de validar el email
    private fun validarEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    // Función encargada de lanzar los casos de uso que realizan el almacenado de los datos
    // del perfil en el DataStore
    private fun savedPerfil(name: String, surname:String, email:String, namePokSuperStar:String,idPokSuperStar:String) {
        liveName.value = name

        executeUseCase(
            exceptionAction = {
                liveShowDialog.value = DialogData(true,"Se ha producido un Error")
            },
            finalAction = {
                liveShowDialog.value = DialogData(true,"Perfil guardado correctamente")
            }
        ) {
            saveProfileNameUseCase.execute(name)
            saveProfileSurnameUseCase.executeSyncInCurrentThread(surname)
            saveProfileEmailUseCase.executeSyncInCurrentThread(email)
            saveProfilePokStarUseCase.executeSyncInCurrentThread(namePokSuperStar)
            saveProfileIdPokStarUseCase.executeSyncInCurrentThread(idPokSuperStar)
        }
    }

    fun onShowFab(show:Boolean){
        mainViewModel.showFab(show)
    }

    override fun onCleared() {
        onShowFab(true)
        super.onCleared()
    }
}