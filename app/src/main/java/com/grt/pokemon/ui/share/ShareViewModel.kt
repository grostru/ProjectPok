package com.grt.pokemon.ui.share

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.grt.pokemon.common.BaseViewModel
import com.grt.pokemon.common.NavData
import com.grt.pokemon.domain.model.PokemonModel
import com.grt.pokemon.domain.usecase.GetProfileIdPokStarUseCase
import com.grt.pokemon.domain.usecase.GetSavedPokemonsUseCase
import com.grt.pokemon.ui.dialog.DialogData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 */
class ShareViewModel(private val getProfileIdPokStarUseCase: GetProfileIdPokStarUseCase) : BaseViewModel() {

    val obsIdPok: LiveData<String> =
        getProfileIdPokStarUseCase.executeSyncInCurrentThread(Unit).asLiveData()

    override fun onInitialization() {
        onShowFab(false)
    }

    override fun onCleared() {
        onShowFab(true)

        super.onCleared()
    }

    fun onShowMessage(message:String){
        liveShowDialog.value = DialogData(true,message)
    }

    fun onShowFab(show:Boolean){
        mainViewModel.showFab(show)
    }
}