package com.grt.pokemon.data.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 * Clase abstracta encargada de la implemetación de la bbdd room
 */
@Database(
    entities = [
        PokemonsDataDB::class
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pokemonsDao(): PokemonsDAO
}