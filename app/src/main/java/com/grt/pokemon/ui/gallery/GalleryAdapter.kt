package com.grt.pokemon.ui.gallery

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grt.pokemon.databinding.ItemImageBinding
import com.grt.pokemon.domain.model.PokemonModel
import java.io.File


/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 *
 * Clase manejadora que se encarga de adaptar cada pokemon a la lista creada que posee
 * un CardView con un Motionlayout. Cada vez que se pulsa una de las imágenes esta es ampliada
 *
 */
class GalleryAdapter(private val pokemonList:List<PokemonModel> = emptyList())
    : RecyclerView.Adapter<GalleryAdapter.GalleryPokemonViewHolder>() {

    private var mutablePokemonList : MutableList<PokemonModel> = mutableListOf(*pokemonList.toTypedArray())

    inner class GalleryPokemonViewHolder(val binding : ItemImageBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(pokemonModel: PokemonModel){

            with(binding){
                pokemonModel.also {
                    tvGallery.text = it.name.uppercase()

                    val imgFile = File(it.url_image_default)
                    if (imgFile.exists()) {
                        val myBitmap = BitmapFactory.decodeFile(imgFile.path)
                        ivGallery.setImageBitmap(myBitmap)
                    }
                }
            }
        }
    }

    fun updateList(list: List<PokemonModel>){
        mutablePokemonList.clear()
        mutablePokemonList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryPokemonViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return GalleryPokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryPokemonViewHolder, position: Int) {
        holder.bind(mutablePokemonList[position])
    }

    override fun getItemCount(): Int {
        return mutablePokemonList.size
    }
}