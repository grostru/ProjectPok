package com.grt.pokemon.domain.usecase

import com.grt.pokemon.domain.model.PokemonModel
import com.grt.pokemon.domain.repository.GetPokemonsRepository
/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 * Caso de uso que obtiene del repositorio la lista de pokemons o en su defecto una lista vacía
 */
class GetSavedPokemonsUseCase(
    private val pokemonsRepository: GetPokemonsRepository
): UseCase<Unit, List<PokemonModel>>() {

    override suspend fun executeUseCase(input: Unit):List<PokemonModel> {
        return pokemonsRepository.getPokemons().getOrDefault(emptyList())
    }
}