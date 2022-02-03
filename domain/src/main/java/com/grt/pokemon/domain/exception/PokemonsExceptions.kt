package com.grt.pokemon.domain.exception

/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 * Clase que representa los errores a manejar en la aplicación
 */
sealed class PokemonsExceptions : Exception() {
    class NetworkError : PokemonsExceptions()
    class HttpError(val code: Int, override val message: String) : PokemonsExceptions()
    class GenericError(override val message: String) : PokemonsExceptions()
}