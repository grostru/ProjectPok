package com.grt.pokemon.ui.gallery

import com.grt.pokemon.common.BaseViewModel

/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 *
 * ViewModel encargado de la lista que muestra la Galeria de imágenes
 */
class GalleryViewModel : BaseViewModel() {
    override fun onInitialization() {
    }

    override fun onCleared() {
        onShowFab(true)
        super.onCleared()
    }

    fun onShowFab(show:Boolean){
        mainViewModel.showFab(show)
    }
}