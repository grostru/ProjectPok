package com.grt.pokemon.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.grt.pokemon.R
import com.grt.pokemon.common.BaseFragment
import com.grt.pokemon.databinding.FragmentGalleryBinding
import com.grt.pokemon.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 *
 * Fragmento Detalle de la lista de Pokemon.
 * Se muestra la información detallada de cada pokemon y observamos el cambio de estado del botón
 * favorito
 */
class GalleryFragment : BaseFragment<FragmentGalleryBinding, GalleryViewModel>() {

    override val vm: GalleryViewModel by viewModel()

    val vmHome: HomeViewModel by sharedViewModel()

    private var galleryAdapter: GalleryAdapter = GalleryAdapter()

    override fun provideBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentGalleryBinding {
        return FragmentGalleryBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
    }

    private fun setupBinding() {
        with(binding) {
            rvGallery.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvGallery.adapter = galleryAdapter

            vmHome.obsListPokemons.value?.let {
                galleryAdapter.updateList(it)
            }

        }
    }

    override fun onResume() {
        vm.onShowFab(false)
        super.onResume()
    }
}
