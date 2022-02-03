package com.grt.pokemon.ui.profile

import android.content.Context
import android.graphics.BitmapFactory
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.grt.pokemon.databinding.FragmentProfileBinding
import com.grt.pokemon.domain.model.PokemonModel
import java.io.File

/**
 * Created por Gema Rosas Trujillo
 * 30/01/2022
 * Clase que gestiona la adaptación de los Datos al Spinner y cambia la imagen
 * según el valor del mismo seleccionado.
 */
class SpinnerPokemonAdapter(context: Context, textViewResourceId: Int, val list: List<PokemonModel>,val binding : FragmentProfileBinding) : ArrayAdapter<PokemonModel>(
    context,
    textViewResourceId,
    list
) {
    override fun getCount() = list.size

    override fun getItem(position: Int) = list[position]

    override fun getItemId(position: Int) = if (getCount()== 0) 0 else list[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return (super.getDropDownView(position, convertView, parent) as TextView).apply {
            text = list[position].name.uppercase()

            //Cada vez que seleccionemos un elemento de la lista, mostramos la foto del Pokemon
            // Super Super Star seleccionado
            val imgFile = File(list[position].url_image_default)
            if (imgFile.exists()) {
                val myBitmap = BitmapFactory.decodeFile(imgFile.path)
                binding.ivPokSSStar.setImageBitmap(myBitmap)
            }
        }
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return (super.getDropDownView(position, convertView, parent) as TextView).apply {
            text = list[position].name.uppercase()
        }
    }
}