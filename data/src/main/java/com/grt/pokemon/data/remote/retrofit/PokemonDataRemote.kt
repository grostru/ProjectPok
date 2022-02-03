package com.grt.pokemon.data.remote.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.grt.pokemon.domain.model.PokemonModel

/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 * Clase de datos en la que se definen los campos que nos vamos a traer de Retrofit
 */

data class PokemonDataRemote(
    @Expose
    @SerializedName("id")
    val id : Int,
    @Expose
    @SerializedName("name")
    val name : String,
    @Expose
    val weight : Int,
    @Expose
    @SerializedName("height")
    val height : Int,
    @Expose
    @SerializedName("base_experience")
    val base_experience : Int,
    @Expose
    @SerializedName("is_default")
    val is_default : Boolean,
    @Expose
    @SerializedName("forms")
    val forms : List<Forms>,
    @Expose
    @SerializedName("species")
    val species : Species,
    @Expose
    @SerializedName("sprites")
    val sprites : Sprites,
    @Expose
    @SerializedName("favorite")
    val favorite : Boolean,
    @Expose
    @SerializedName("abilities")
    val abilities : List<Abilities>,
    @Expose
    @SerializedName("types")
    val types : List<Types>,
    val order:Int,
    val game_indices : List<GameIndices>,
    val location_area_encounters:String,
    val moves:List<Moves>,
    val pas_types:List<Stats>

)
data class Stats(
    val base_stat:Int,
    val effort:Int,
    val stat:Stat
)
data class Stat(
    val name:String,
    val url:String
)
data class Moves(
    val move:Move,
    val version_group_details:List<VersionGroupDetails>
)
data class VersionGroupDetails(
    val level_learned_at:Int,
    val move_learn_method:MoveLearnMethod
)
data class MoveLearnMethod(
    val name:String,
    val url:String
)
data class Move(
    val name:String,
    val url:String
)
data class GameIndices(
    val game_index : Int,
    val version : Version
)
data class Version(
    val name : String,
    val url : String
)

data class Forms (
    @Expose
    @SerializedName("name")
    val name: String
)

data class Species(
    @Expose
    @SerializedName("name")
    val name: String
)

data class Sprites(
    @Expose
    @SerializedName("other")
    val other: Other
)

data class Other(
    @Expose
    @SerializedName("home")
    val home: Home
)

data class Home(
    @Expose
    @SerializedName("front_default")
    val front_default: String
)

data class Abilities(
    @Expose
    @SerializedName("slot")
    val slot : Int,
    @Expose
    @SerializedName("ability")
    val ability: Ability
)

data class Ability(
    @Expose
    @SerializedName("name")
    val name: String
)

data class Types(
    @Expose
    @SerializedName("slot")
    val slot: Int,
    @Expose
    @SerializedName("type")
    val type : Type
)

data class Type(
    @Expose
    @SerializedName("name")
    val name: String
)

// Método que parsea los datos obtenidos de Retrofil al modelo de datos de la app
fun PokemonDataRemote.toDomain() : PokemonModel{

    return PokemonModel(id, name, weight, height, base_experience, is_default, forms[0].name,
        species.name, sprites.other.home.front_default, favorite = false, getAbilities(abilities), getTypes(types))
}

fun getAbilities(abilities: List<Abilities>):String{
    var s_abilities :String = ""
    for((i, item) in abilities.withIndex()){
        s_abilities += "${abilities[i].ability.name.uppercase()} \n"
    }

    return s_abilities
}

fun getTypes(types: List<Types>):String{
    var s_types :String = ""
    for((i, item) in types.withIndex()){
        s_types += "${types[i].type.name.uppercase()} \n"
    }

    return s_types
}

