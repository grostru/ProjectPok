package com.grt.pokemon.domain.repository

import kotlinx.coroutines.flow.Flow

/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 *
 * Interface en la que se definen los métodos que vamos a usar para almacenar y obtener
 * los datos persistidos en DataStore
 */
interface ProfileRepository {

    // Nombre
    suspend fun saveNameProfile(name:String)
    suspend fun getNameProfile(): String

    // Apellidos
    suspend fun saveSurnameProfile(surname:String)
    suspend fun getSurnameProfile(): Flow<String>

    // Email
    suspend fun saveEmailProfile(email:String)
    suspend fun getEmailProfile(): Flow<String>

    // Nombre Pokemon Super Favorito
    suspend fun saveNamePokSuperStarProfile(pokStar:String)
    suspend fun getNamePokSuperStarProfile(): Flow<String>

    // Id del Pokemon Seleccionado
    suspend fun saveIdPokSuperStarProfile(pokStar:String)
    suspend fun getIdPokSuperStarProfile(): Flow<String>
}