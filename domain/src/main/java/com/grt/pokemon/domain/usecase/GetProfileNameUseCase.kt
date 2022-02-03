package com.grt.pokemon.domain.usecase

import com.grt.pokemon.domain.repository.ProfileRepository

/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 * Caso de uso para obtener el nombre del data store
 */
class GetProfileNameUseCase(
    private val profileRepository: ProfileRepository
): UseCase<Unit, String>() {

    override suspend fun executeUseCase(input: Unit):String {
        return profileRepository.getNameProfile()
    }
}