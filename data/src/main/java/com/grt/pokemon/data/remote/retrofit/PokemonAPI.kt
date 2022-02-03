package com.grt.pokemon.data.remote.retrofit

import com.grt.pokemon.domain.model.PokemonModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 *
 * Interface en la que se define la operación remota a realizar y las operaciones a realizar
 * con sus correspondientes parámetros
 */
interface PokemonAPI {
    // Cuando tiramos de Retrofit del Servidor
    @GET("pokemon/{id}")
    suspend fun getPokemons(@Path("id") id:String): Response<PokemonDataRemote>

    //@GET("pokemons.json")
    //suspend fun getPokemons():List<PokemonModel>
}
