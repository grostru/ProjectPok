package com.grt.pokemon.domain.repository

import com.grt.pokemon.domain.model.PokemonModel
/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 * Interface que representa las operaciones a realizar en este caso de uso que va a ser
 * el de bbdd room
 */
interface EditPokemonsRepository {

    suspend fun deletePokemon(pokemonModel: PokemonModel)

    suspend fun deletePokemons()

    suspend fun savePokemons(vararg pokemons:PokemonModel)

    suspend fun modifyPokemon(pokemonModel: PokemonModel)
}