package com.grt.pokemon.domain.model

import java.io.Serializable
/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 * Modelo de datos común a toda la aplicación
 */
data class PokemonModel(
    val id:Int,
    val name:String,
    val weight:Int,
    val height:Int,
    val base_experience:Int,
    val is_default:Boolean,
    val forms:String,
    val species:String,
    val url_image_default:String,
    var favorite:Boolean,
    var abilities:String,
    var types:String
): Serializable