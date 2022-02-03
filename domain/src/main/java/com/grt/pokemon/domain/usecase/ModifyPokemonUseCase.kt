package com.grt.pokemon.domain.usecase

import com.grt.pokemon.domain.model.PokemonModel
import com.grt.pokemon.domain.repository.EditPokemonsRepository

/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 * Caso de uso para modificar en el repositorio un pokemon
 */
class ModifyPokemonUseCase(
    private val pokemonRepository: EditPokemonsRepository
) : UseCase<PokemonModel,Unit>() {

    override suspend fun executeUseCase(input: PokemonModel) {
        pokemonRepository.modifyPokemon(input)
    }
}