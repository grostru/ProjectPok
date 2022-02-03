package com.grt.pokemon.ui.superfavorito

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.grt.pokemon.common.BaseViewModel
import com.grt.pokemon.domain.usecase.GetProfileIdPokStarUseCase
import com.grt.pokemon.ui.dialog.DialogData

/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 */
class SuperFavoritoViewModel(private val getProfileIdPokStarUseCase: GetProfileIdPokStarUseCase) : BaseViewModel() {

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