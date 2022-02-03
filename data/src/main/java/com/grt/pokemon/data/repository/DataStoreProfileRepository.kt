package com.grt.pokemon.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.grt.pokemon.domain.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext


/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 *
 * Clase encargada de la persistencia de los datos del DataStore
 */
class DataStoreProfileRepository(private val context:Context) : ProfileRepository {

    private val Context.storeData: DataStore<Preferences> by preferencesDataStore(name = "ProfileSettings")
    private val KEY_PROFILE_NAME = stringPreferencesKey("KEY_PROFILE_NAME")
    private val KEY_PROFILE_SURNAME = stringPreferencesKey("KEY_PROFILE_SURNAME")
    private val KEY_PROFILE_EMAIL = stringPreferencesKey("KEY_PROFILE_EMAIL")
    private val KEY_PROFILE_POK_STAR = stringPreferencesKey("KEY_PROFILE_POK_STAR")
    private val KEY_ID_PROFILE_POK_STAR = stringPreferencesKey("KEY_ID_PROFILE_POK_STAR")

    // Save and Get Nombre
    override suspend fun saveNameProfile(name: String) {
        withContext(Dispatchers.IO) {
            context.storeData.edit { preferences->
                preferences[KEY_PROFILE_NAME] = name

            }
        }
    }

    override suspend fun getNameProfile(): String {
        return context.storeData.data.map { preferences->
            preferences[KEY_PROFILE_NAME]?:""
        }.first()
    }

    // Save and Get Apellidos
    override suspend fun saveSurnameProfile(name: String) {
        withContext(Dispatchers.IO) {
            context.storeData.edit { preferences->
                preferences[KEY_PROFILE_SURNAME] = name

            }
        }
    }

    override suspend fun getSurnameProfile(): Flow<String> {
        return context.storeData.data.map { preferences->
            preferences[KEY_PROFILE_SURNAME]?:""
        }
    }

    // Save and Get Email
    override suspend fun saveEmailProfile(name: String) {
        withContext(Dispatchers.IO) {
            context.storeData.edit { preferences->
                preferences[KEY_PROFILE_EMAIL] = name

            }
        }
    }

    override suspend fun getEmailProfile(): Flow<String> {
        return context.storeData.data.map { preferences->
            preferences[KEY_PROFILE_EMAIL]?:""
        }
    }

    // Save and Get Nombre Pokemon Super Favorito
    override suspend fun saveNamePokSuperStarProfile(name: String) {
        withContext(Dispatchers.IO) {
            context.storeData.edit { preferences->
                preferences[KEY_PROFILE_POK_STAR] = name

            }
        }
    }

    override suspend fun getNamePokSuperStarProfile(): Flow<String> {
        return context.storeData.data.map { preferences->
            preferences[KEY_PROFILE_POK_STAR]?:""
        }
    }

    // Save and Get Id Pokemon Super Favorito
    override suspend fun saveIdPokSuperStarProfile(idPok: String) {
        withContext(Dispatchers.IO) {
            context.storeData.edit { preferences->
                preferences[KEY_ID_PROFILE_POK_STAR] = idPok

            }
        }
    }

    override suspend fun getIdPokSuperStarProfile(): Flow<String> {
        return context.storeData.data.map { preferences->
            preferences[KEY_ID_PROFILE_POK_STAR]?:""
        }
    }
}