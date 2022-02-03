package com.grt.pokemon.common

import android.app.Application
import com.grt.pokemon.data.di.dataModule
import com.grt.pokemon.di.uiModule
import com.grt.pokemon.di.usecaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 *
 * Clase en la que inciamos Koin y el contexto de la Aplicación. Esta clase siempre ha de estar
 * definida en el manifest
 */
class PokemonApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PokemonApplication)
            modules(
                uiModule,
                dataModule,
                usecaseModule
            )
        }
    }
}