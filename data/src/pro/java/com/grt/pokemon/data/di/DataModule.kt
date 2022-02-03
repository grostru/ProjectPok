package com.grt.pokemon.data.di

import com.grt.pokemon.data.database.room.RoomPokemonsRepository
import com.grt.pokemon.data.remote.retrofit.RetrofitPokemonRepository
import com.grt.pokemon.data.repository.DataStoreProfileRepository
import com.grt.pokemon.domain.repository.EditPokemonsRepository
import com.grt.pokemon.domain.repository.GetPokemonsRepository
import com.grt.pokemon.domain.repository.ProfileRepository
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 *
 * Modulo de Datos del inyector de Dependencias
 */
val dataModule = module {

    single { RoomPokemonsRepository(get()) }

    single<GetPokemonsRepository>(qualifier = qualifier(InjectionQualifiers.DB)) {
       get<RoomPokemonsRepository>()
    }

    single<GetPokemonsRepository>(qualifier = qualifier(InjectionQualifiers.REMOTE)) {
        RetrofitPokemonRepository(get())
    }

    single<EditPokemonsRepository> {
        get<RoomPokemonsRepository>()
    }

    single<ProfileRepository> {
        DataStoreProfileRepository(get())
    }
}