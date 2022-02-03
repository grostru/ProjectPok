package com.grt.pokemon.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.grt.pokemon.common.BaseViewModel
import com.grt.pokemon.common.NavData
import com.grt.pokemon.domain.exception.PokemonsExceptions
import com.grt.pokemon.domain.model.PokemonModel
import com.grt.pokemon.domain.usecase.DeletePokemonUseCase
import com.grt.pokemon.domain.usecase.GetSavedPokemonsUseCase
import com.grt.pokemon.domain.usecase.UpdatePokemonsUseCase
import com.grt.pokemon.ui.dialog.DialogData

/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 * ViewModel de la pagina de Inicio, encargado de los datos de la lista de Pokemon y sus observadores
 *
 */
class HomeViewModel(
    private val updatePokemonsUseCase : UpdatePokemonsUseCase,
    private val getSavedPokemonUseCase : GetSavedPokemonsUseCase,
    private val deletePokemonUseCase: DeletePokemonUseCase
) : BaseViewModel() {

    companion object{
        const val NAV_DETAIL = 0
    }

    private val liveListPokemons: MutableLiveData<List<PokemonModel>> = MutableLiveData()
    val obsListPokemons: LiveData<List<PokemonModel>> = liveListPokemons

    // Función que obtiene la lista de Pokemons de BBDD para su posterior pintado
    override fun onInitialization() {
        executeUseCase {
            liveListPokemons.value = getSavedPokemonUseCase.execute(Unit)
        }
    }

    // Función llamada cuando pulsamos el botón de Descarga. LLama al caso de uso que
    // baja de Retrofit la lista, la almacena en bbdd y después la muestra
    fun onActionDownloadClicked() {
        showLoading()
        executeUseCase(
            finalAction = {
                hideLoading()
                onInitialization()
            }
        ) {
            val result = updatePokemonsUseCase.execute(Unit)
            result.onFailure {
                handleUpdateException(it)
            }
        }
    }

    fun showDialogWarning(msg:String){
        liveShowDialog.value = DialogData(true,"Acción no Permitida. No tiene conexión a internet.")
    }

    // Función encargada del manejo de haber hecho click en uno de los elementos de la lista de
    // Pokemon y navega hasta la pantalla de Detalles
    fun onActionPokemonClicked(pokemonModel: PokemonModel) {
        navigate(NavData(NAV_DETAIL, pokemonModel))
    }

    // Función que se encarga del borrado del elemento al que le hemos realizado swipe en la
    // lista de Pokemons
    fun onActionOnItemSwiped(itemPosition: Int) {
        executeUseCase {
            val pokemon = obsListPokemons.value?.get(itemPosition)
            pokemon?.also {
                deletePokemonUseCase.execute(it)
                liveListPokemons.value = getSavedPokemonUseCase.execute(Unit)
            }
        }
    }

    // Función encargada del manejo de errores que pueden ocurrir al obtener la lista
    // de Pokemon de Retrofit. Muestra un dialogo con dicho Error
    private fun handleUpdateException(it: Throwable) {
        if (it is PokemonsExceptions) {
            when (it) {
                is PokemonsExceptions.GenericError -> {
                    liveShowDialog.value = DialogData(true, it.message)
                }
                is PokemonsExceptions.HttpError -> {
                    liveShowDialog.value = DialogData(true, "Http: ${it.code} ${it.message}")

                }
                is PokemonsExceptions.NetworkError -> {
                    liveShowDialog.value = DialogData(true, "Network error")
                }
            }
        } else {
            liveShowDialog.value = DialogData(true, it.toString())
        }
    }
}