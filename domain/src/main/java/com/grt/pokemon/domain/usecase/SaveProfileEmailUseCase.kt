package com.grt.pokemon.domain.usecase

import com.grt.pokemon.domain.repository.ProfileRepository

/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 * Caso de uso para almacenar en data store el email
 */
class SaveProfileEmailUseCase(
    private val profileRepository: ProfileRepository
): UseCase<String, Unit>() {

    override suspend fun executeUseCase(input: String) {
        return profileRepository.saveEmailProfile(input)
    }
}