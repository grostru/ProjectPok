package com.grt.pokemon.ui.detail

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.navArgs
import com.grt.pokemon.R
import com.grt.pokemon.common.BaseFragment
import com.grt.pokemon.databinding.FragmentDetailBinding
import com.grt.pokemon.domain.model.PokemonModel
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 *
 * Fragmento Detalle de la lista de Pokemon.
 * Se muestra la información detallada de cada pokemon y observamos el cambio de estado del botón
 * favorito
 */
class DetailFragment : BaseFragment<FragmentDetailBinding, DetailViewModel>() {

    override val vm: DetailViewModel by viewModel()

    val args: DetailFragmentArgs by navArgs()

    override fun provideBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentDetailBinding {
        return FragmentDetailBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Podemos venir de la búsqueda del SearchView de la lista de Pokemon y lo primer
        // que hacemos es ocultar el teclado.
        hideKeyboard(view);

        // Iniciamos el fragmento ocultando el botón de descarga
        vm.onInit()

        // Obtenemos el Pokemon que hemos clickeado en la lista para mostrarlo,
        // ya que lo hemos pasado como argumento en el Navigation
        val pokemon = args.pokemon
        vm.onAttachPokemon(pokemon)
        observeData(vm.obsPokemon,::onObservePokemon)
        // Ponemos un observador a la estrella para que cambie la imagen cuando cambia su estado
        observeData(vm.obsPokemonStar,::onObservePokemonStar)
        // Capturamos el click de la estrella Favorita
        binding.ivStartFavorita.setOnClickListener { view ->
            pokemon.favorite = !pokemon.favorite
            vm.onActionStarClick(pokemon)
        }
    }

    private fun onObservePokemonStar(pokemonModel: PokemonModel){
        changeImageStar(pokemonModel.favorite)
    }

    // Establecemos los datos de Detalle del Pokemon Seleccionado en la lista
    private fun onObservePokemon(pokemonModel: PokemonModel) {
        with(binding){
            tvDetailName.text = pokemonModel.name.uppercase()
            tvDetailEspecieValue.text = pokemonModel.species
            tvDetailFormaValue.text = pokemonModel.forms
            tvDetailPesoValue.text = pokemonModel.weight.toString()
            tvDetailAlturaValue.text = pokemonModel.height.toString()
            tvDetailExperienceValue.text = pokemonModel.base_experience.toString()
            tvDetailDefaultValue.text = if (pokemonModel.is_default) "Si" else "No"
            tvDetailAbilitiesValue.text = pokemonModel.abilities
            tvDetailTypesValue.text = pokemonModel.types

            val imgFile = File(pokemonModel.url_image_default)
            if (imgFile.exists()) {
                val myBitmap = BitmapFactory.decodeFile(imgFile.path)
                ivDetailImage.setImageBitmap(myBitmap)
            }

            // Establecemos la imagen de favorito dependiendo del valor de este
            changeImageStar(pokemonModel.favorite)
        }
    }

    // Método que cambia la imagen de la Estrella Favorita en función de si se ha establecido como Favorito
    // el Pokemon o no
    private fun changeImageStar(isFavorite:Boolean){
        val imageFavoriteSelect = if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_no_favorite
        Picasso.with(context).load(imageFavoriteSelect).fit().centerInside()
            .placeholder(R.drawable.progress_image_animation)
            .error(R.drawable.ic_no_image)
            .into(binding.ivStartFavorita)
    }
}
