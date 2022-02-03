package com.grt.pokemon.di

import com.grt.pokemon.data.di.InjectionQualifiers
import com.grt.pokemon.domain.usecase.*
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 *
 * Clase en la que definimos todos los casos de uso usados como injección de dependencias
 * en la app
 */
val usecaseModule = module {

    single { GetSavedPokemonsUseCase(get(qualifier = qualifier(InjectionQualifiers.DB))) }

    single { ModifyPokemonUseCase(get()) }

    single { DeletePokemonUseCase(get()) }

    single {
        UpdatePokemonsUseCase(
            remoteRepository = get(qualifier = qualifier(InjectionQualifiers.REMOTE)),
            dbRepository = get()
        )
    }

    single { GetProfileNameUseCase(get()) }

    single { GetProfileSurnameUseCase(get()) }

    single { GetProfileEmailUseCase(get()) }

    single { GetProfilePokStarUseCase(get()) }

    single { GetProfileIdPokStarUseCase(get()) }

    single { SaveProfileNameUseCase(get()) }

    single { SaveProfileSurnameUseCase(get()) }

    single { SaveProfileEmailUseCase(get()) }

    single { SaveProfilePokStarUseCase(get()) }

    single { SaveProfileIdPokStarUseCase(get()) }
}



