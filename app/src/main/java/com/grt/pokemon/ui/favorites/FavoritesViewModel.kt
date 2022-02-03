package com.grt.pokemon.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.grt.pokemon.common.BaseViewModel
import com.grt.pokemon.common.NavData
import com.grt.pokemon.domain.model.PokemonModel
import com.grt.pokemon.domain.usecase.GetSavedPokemonsUseCase
import com.grt.pokemon.ui.home.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 *
 * ViewModel que gestiona la opción de la Lista de Pokeons Favoritos
 */
class FavoritesViewModel(private val getSavedPokemonsUseCase: GetSavedPokemonsUseCase)
    : BaseViewModel() {

    companion object{
        const val NAV_DETAIL = 0
    }

    private val liveListPokemons: MutableLiveData<List<PokemonModel>> = MutableLiveData()
    private val liveListPokemonsFavorites: MutableLiveData<List<PokemonModel>> = MutableLiveData()
    val obsListPokemonsFavorites: LiveData<List<PokemonModel>> = liveListPokemonsFavorites

    override fun onInitialization() {
        onShowFab(false)

        viewModelScope.launch(Dispatchers.IO) {
            executeUseCase {
                val listPokemon = getSavedPokemonsUseCase.execute(Unit)
                liveListPokemons.value = listPokemon
                liveListPokemonsFavorites.value = listPokemon.filter {
                    it.favorite
                }
            }
        }
    }

    fun onActionPokemonClicked(pokemonModel: PokemonModel) {
        navigate(NavData(FavoritesViewModel.NAV_DETAIL, pokemonModel))
    }

    override fun onCleared() {
        onShowFab(true)
        super.onCleared()
    }

    fun onShowFab(show:Boolean){
        mainViewModel.showFab(show)
    }
}