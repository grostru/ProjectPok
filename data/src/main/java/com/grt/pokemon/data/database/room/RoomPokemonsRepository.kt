package com.grt.pokemon.data.database.room

import android.content.Context
import android.graphics.BitmapFactory
import androidx.room.Room
import com.grt.pokemon.data.BuildConfig
import com.grt.pokemon.data.UtilsPokemons
import com.grt.pokemon.domain.model.PokemonModel
import com.grt.pokemon.domain.repository.EditPokemonsRepository
import com.grt.pokemon.domain.repository.GetPokemonsRepository
import com.squareup.picasso.Picasso

/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 *
 * Clase que implementa la conexión a la BBDD Room y todas sus operaciones
 */
class RoomPokemonsRepository(private val context: Context):
    EditPokemonsRepository,GetPokemonsRepository {

    private val appDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        BuildConfig.DB_NAME
    ).build()

    override suspend fun deletePokemon(pokemonModel: PokemonModel) {
        appDatabase.pokemonsDao().deleteItemPokemon(pokemonModel.toDataDB())
    }

    override suspend fun deletePokemons() {
        appDatabase.pokemonsDao().deletePokemons()
    }

    override suspend fun savePokemons(vararg pokemons: PokemonModel) {
        appDatabase.pokemonsDao().savePokemons(*pokemons.map {
            // Obtenemos la imagen de la Url obtenida de retrofit para almacenarla en disco
            // ya que son imagenes pesadas y en la bbdd no es recomendable este uso
            val imageBitmap = Picasso.with(context).load(it.url_image_default).get()
            val pathImage = UtilsPokemons.saveToInternalStorage(it.name,imageBitmap,context)
            val listCopy = it.copy(url_image_default = pathImage)
            listCopy.toDataDB()
        }.toTypedArray())
    }

    override suspend fun modifyPokemon(pokemonModel: PokemonModel) {
        appDatabase.pokemonsDao().saveItemPokemon(pokemonModel.toDataDB())
    }

    override suspend fun getPokemons(): Result<List<PokemonModel>> {
        val list = appDatabase.pokemonsDao().getPokemons().map {
            it.toDomain()
        }.sortedBy {
            it.name
        }

        return Result.success(list)
    }
}
