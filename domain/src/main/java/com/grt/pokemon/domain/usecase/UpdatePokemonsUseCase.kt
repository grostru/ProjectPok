package com.grt.pokemon.domain.usecase

import com.grt.pokemon.domain.model.PokemonModel
import com.grt.pokemon.domain.repository.EditPokemonsRepository
import com.grt.pokemon.domain.repository.GetPokemonsRepository
import java.lang.Exception
/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 * Caso de uso que se encarga de obtener de retrofit la lista de pokemons y almacenarla en
 * bbdd room
 */
class UpdatePokemonsUseCase(
    private val remoteRepository: GetPokemonsRepository,
    private val dbRepository: EditPokemonsRepository
) : UseCase<Unit, Result<List<PokemonModel>>>() {

    override suspend fun executeUseCase(input: Unit): Result<List<PokemonModel>> {

        return  remoteRepository.getPokemons().map { listFormatted->
            dbRepository.savePokemons(*listFormatted.toTypedArray())
            listFormatted
        }
    }
}
