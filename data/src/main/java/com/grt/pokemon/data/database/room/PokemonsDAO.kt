package com.grt.pokemon.data.database.room

import androidx.room.*
/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 * Interface en el que se define los accesos a datos y se especifican las búsquedas u operaciones
 * a realizar en bbdd
 */
@Dao
interface PokemonsDAO {

    @Query("SELECT * from ${PokemonsDataDB.NAME_DB}")
    fun getPokemons(): List<PokemonsDataDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePokemons(vararg pokemonModel: PokemonsDataDB)

    @Query("DELETE from ${PokemonsDataDB.NAME_DB}")
    suspend fun deletePokemons()

    @Delete
    suspend fun deleteItemPokemon(pokemon: PokemonsDataDB)

    @Update
    suspend fun saveItemPokemon(pokemon: PokemonsDataDB)

}