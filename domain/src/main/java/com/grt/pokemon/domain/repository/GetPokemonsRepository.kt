package com.grt.pokemon.domain.repository

import com.grt.pokemon.domain.model.PokemonModel
/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 * Interface del caso de uso que obtiene la lista de Pokemon dependiendo de si es de bbdd room o
 * remoto
 */
interface GetPokemonsRepository {
    suspend fun getPokemons(): Result<List<PokemonModel>>
}