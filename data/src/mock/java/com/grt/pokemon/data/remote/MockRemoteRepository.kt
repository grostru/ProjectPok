package com.grt.pokemon.data.remote

import android.content.Context
import com.grt.pokemon.data.UtilsPokemons
import com.grt.pokemon.data.database.room.toDataDB
import com.grt.pokemon.domain.model.PokemonModel
import com.grt.pokemon.domain.repository.GetPokemonsRepository
import com.squareup.picasso.Picasso
import kotlinx.coroutines.delay
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MockRemoteRepository()  : GetPokemonsRepository {

    override suspend fun getPokemons(): Result<List<PokemonModel>> {
        delay(2000L)
        val listPokemon = mutableListOf<PokemonModel>()
        repeat(40) {
            val factor = if (it % 2 == 0)
                1
            else
                -1

            val pok = PokemonModel(
                id = it,
                name = "Name: $it",
                weight = it,
                height = it,
                base_experience = it,
                is_default = it % 2 == 0,
                forms = "Forma: $it",
                species = "Especie: $it",
                url_image_default="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/$it.png",
                favorite = it % 2 == 0,
                abilities = "Ability: $it: con Slot:$it \n Ability2: $it: con Slot2:$it \n",
                types = "Type: $it: con Slot:$it \n Type2: $it: con Slot2:$it \n"
            )

            listPokemon.add(pok)
        }

        return Result.success(listPokemon)
        //return Result.failure(Throwable("Se ha producido un error"))
    }
}