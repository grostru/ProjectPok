package com.grt.pokemon.ui.detail

import androidx.lifecycle.MutableLiveData
import com.grt.pokemon.common.BaseViewModel
import com.grt.pokemon.domain.model.PokemonModel
import com.grt.pokemon.domain.usecase.ModifyPokemonUseCase

/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 *
 * ViewModel del Detalle de la lista de pokemon. Aquí manejamos los Livedata tanto del pokemon
 * si ha cambiado su estado como del botón favorito y su correspondiente modificación en la
 * base de datos
 */
class DetailViewModel(private val modifyPokemonUseCase: ModifyPokemonUseCase)
    : BaseViewModel() {

    private val livePokemon = MutableLiveData<PokemonModel>()
    val obsPokemon = livePokemon

    private val liveStarPokemon = MutableLiveData<PokemonModel>()
    val obsPokemonStar = liveStarPokemon

    fun onAttachPokemon(pokemonModel: PokemonModel) {
        livePokemon.value = pokemonModel
    }

    fun onActionStarClick(pokemonModel: PokemonModel){
        executeUseCase(
            finalAction = {
                liveStarPokemon.value = pokemonModel
            }
        ) {
            modifyPokemonUseCase.execute(pokemonModel)
        }
    }

    override fun onInitialization() {
        mainViewModel.showFab(false)
    }

    override fun onCleared() {
        mainViewModel.showFab(true)
        super.onCleared()
    }
}