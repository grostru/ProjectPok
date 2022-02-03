package com.grt.pokemon.ui.home

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.grt.pokemon.R
import com.grt.pokemon.databinding.ItemHomeBinding
import com.grt.pokemon.domain.model.PokemonModel
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList
import java.io.File


/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 *
 * Clase manejadora que se encarga de adaptar cada pokemon a la lista
 */
class HomeAdapter(private val pokemonList:List<PokemonModel> = emptyList(),
                  private val listener: (PokemonModel) -> Unit)
    : RecyclerView.Adapter<HomeAdapter.HomePokemonViewHolder>(), Filterable {

    private var mutablePokemonList : MutableList<PokemonModel> = mutableListOf(*pokemonList.toTypedArray())

    inner class HomePokemonViewHolder(val binding : ItemHomeBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(pokemonModel: PokemonModel){
            val context = itemView.context
            with(binding){
                pokemonModel.also {
                    tvPok.text = it.name.uppercase()
                    tvEspecieValue.text = it.species
                    tvPesoValue.text = it.weight.toString()

                    val imgFile = File(it.url_image_default)
                    if (imgFile.exists()) {
                        val myBitmap = BitmapFactory.decodeFile(imgFile.path)
                        sivPok.setImageBitmap(myBitmap)
                    }

                    // Imagen favorita
                    val imgFavorite = if (pokemonModel.favorite) R.drawable.ic_favorite else R.drawable.ic_no_favorite
                    Picasso.with(context).load(imgFavorite).fit().centerCrop()
                        .placeholder(R.drawable.ic_no_image)
                        .error(R.drawable.ic_no_image)
                        .into(ivHomeStar);

                }
            }
            itemView.setOnClickListener {
                listener(pokemonModel)
            }
        }
    }

    fun updateList(list: List<PokemonModel>){
        mutablePokemonList.clear()
        mutablePokemonList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePokemonViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HomePokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomePokemonViewHolder, position: Int) {
        holder.bind(mutablePokemonList[position])
    }

    override fun getItemCount(): Int {
        return mutablePokemonList.size
    }

    // Método que implementa la función de filtrado del SearchView añadido a la lista de Pokemons
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint?.toString() ?: ""
                val filterResults = FilterResults()
               try {
                   if (!charSearch.isNullOrBlank()) {
                       filterResults.values = mutablePokemonList.filter {
                           it.name.lowercase().contains(charSearch.lowercase())
                       }

                   } else {
                       filterResults.values = mutablePokemonList
                   }

               }catch (e:ConcurrentModificationException){
                   filterResults.values = mutablePokemonList
               }

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                mutablePokemonList = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<PokemonModel>
                notifyDataSetChanged()

            }
        }
    }
}