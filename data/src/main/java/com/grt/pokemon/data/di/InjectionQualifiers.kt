package com.grt.pokemon.data.di

/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 *
 * Clase definida para que en la injección de dependencia realizada el injector sepa si tenemos
 * que acceder a retrofit o a room
 */
object InjectionQualifiers {
    const val DB = "DB"
    const val REMOTE = "REMOTE"
}