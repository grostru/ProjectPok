package com.grt.pokemon.di

import com.grt.pokemon.ui.detail.DetailViewModel
import com.grt.pokemon.ui.favorites.FavoritesViewModel
import com.grt.pokemon.ui.gallery.GalleryViewModel
import com.grt.pokemon.ui.home.HomeViewModel
import com.grt.pokemon.ui.main.MainViewModel
import com.grt.pokemon.ui.profile.ProfileViewModel
import com.grt.pokemon.ui.share.ShareViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 *
 * Modulo en el que iniciamos todos los ViewModel que vamos a usar como Injección de dependencias
 */
val uiModule = module {

    viewModel {
        MainViewModel()
    }

    viewModel {
        HomeViewModel(get(), get(), get())
    }

    viewModel {
        FavoritesViewModel(get())
    }

    viewModel {
        ProfileViewModel(get(),get(),get(),get(),get(),get(),get(),get(),get(),get())
    }

    viewModel {
        DetailViewModel(get())
    }

    viewModel {
        GalleryViewModel()
    }
    viewModel {
        ShareViewModel(get())
    }
}