package com.grt.pokemon.data.remote.retrofit

import android.content.Context
import com.grt.pokemon.domain.exception.PokemonsExceptions
import com.grt.pokemon.domain.model.PokemonModel
import com.grt.pokemon.domain.repository.GetPokemonsRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import com.google.gson.GsonBuilder
import com.grt.pokemon.data.UtilsPokemons
import kotlin.random.Random

/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 *
 * Clase que implementa la conexión con Retrofit. Excluidos campos gson que no vengan con @Expose para no
 * serializarlos.
 */
class RetrofitPokemonRepository(private val context: Context) : GetPokemonsRepository {


    var gson = GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    private val retrofit: Retrofit =
        Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    //.addInterceptor(MockInterceptor(get()))
                    .build()
            )
            .baseUrl(com.grt.pokemon.data.BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            //.addConverterFactory(GsonConverterFactory.create())
            .build()

    private val pokemonAPI = retrofit.create(PokemonAPI::class.java)


    //Obtenemos de Retrofit 45 Pokemons y los parseamos para mostrarlos
    // Se realiza un bucle en el que si la llamada da Fallo se devuelve la lista a null
    override suspend fun getPokemons(): Result<List<PokemonModel>> {
        val listPokemons = mutableListOf<PokemonModel>()

        // Obtenemos una lista de números aleatorios para obtener los pokemons de Retrofit
        // y que cada vez que obtengamos la lista del servidor no sean los mismos
        var nPok = 1
        while(nPok <= 45) {
            val result = getPokemonById(nPok.toString())
            result.onSuccess { pok ->
                nPok++
                listPokemons.add(pok)
            }.onFailure {
                return Result.failure(it)
            }
        }

        return Result.success(listPokemons.sortedBy {
            it.name
        })
    }

    // Método para obtener de Retrofit un Pokemon
    private suspend fun getPokemonById(idPok:String): Result<PokemonModel>{
        return safeApiCall {
            pokemonAPI.getPokemons(idPok)
        }.map {
            it.toDomain()
        }
    }

    private suspend fun <T> safeApiCall(call: suspend () -> retrofit2.Response<T>): Result<T> {
        return try {
            val response = call()

            when {
                response.isSuccessful -> {
                    Result.success(response.body() as T)
                }
                else -> {
                    Result.failure(PokemonsExceptions.HttpError(response.code(), "Http error"))
                }
            }
        } catch (e: Throwable) {
            when (e) {
                is IOException -> Result.failure(PokemonsExceptions.NetworkError())
                else -> Result.failure(PokemonsExceptions.GenericError(e.message ?: ""))
            }
        }
    }
}