package com.grt.pokemon.domain.usecase

import com.grt.pokemon.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 * Caso de uso para obtener el id del pokemon del data store
 */
class GetProfileIdPokStarUseCase(
    private val profileRepository: ProfileRepository
): UseCase<Unit, Flow<String>>() {

    override suspend fun executeUseCase(input: Unit):Flow<String> {
        return profileRepository.getIdPokSuperStarProfile()
    }
}