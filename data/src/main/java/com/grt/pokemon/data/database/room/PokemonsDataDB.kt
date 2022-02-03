package com.grt.pokemon.data.database.room

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.grt.pokemon.domain.model.PokemonModel
import java.io.ByteArrayOutputStream
import java.io.Serializable
/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 *
 * Clase de Datos en la que se definen los campos que va a tener nuestra BBDD Room
 */
@Entity(tableName = PokemonsDataDB.NAME_DB)
data class PokemonsDataDB(
    @PrimaryKey
    @ColumnInfo(name = ID)
    val id:Int,
    @ColumnInfo(name = NAME)
    val name:String,
    @ColumnInfo(name = WEIGHT)
    val weight:Int,
    @ColumnInfo(name = HEIGHT)
    val height:Int,
    @ColumnInfo(name = BASE_EXPERIENCE)
    val base_experience:Int,
    @ColumnInfo(name = IS_DEFAULT)
    val is_default:Boolean,
    @ColumnInfo(name = FORMS)
    val forms:String,
    @ColumnInfo(name = SPECIES)
    val species:String,
    @ColumnInfo(name = URL_IMAGE_DEFAULT)
    val url_image_default:String,
    @ColumnInfo(name = FAVORITE)
    val favorite:Boolean,
    @ColumnInfo(name = ABILITIES)
    val abilities:String,
    @ColumnInfo(name = TYPES)
    val types:String

): Serializable{

    companion object TABLE{
        const val NAME_DB = "pokemons"
        const val ID = "id"
        const val NAME = "name"
        const val WEIGHT = "weight"
        const val HEIGHT = "height"
        const val BASE_EXPERIENCE = "base_experience"
        const val IS_DEFAULT = "is_default"
        const val FORMS = "forms"
        const val SPECIES = "species"
        const val URL_IMAGE_DEFAULT = "url_image_default"
        const val FAVORITE = "favorite"
        const val ABILITIES = "abilities"
        const val TYPES = "types"
    }
}


fun PokemonModel.toDataDB() : PokemonsDataDB {

    return PokemonsDataDB(id, name, weight, height, base_experience, is_default, forms,
        species, url_image_default, favorite, abilities, types)
}

fun PokemonsDataDB.toDomain() : PokemonModel{
    return PokemonModel(id, name, weight, height, base_experience, is_default, forms,
        species, url_image_default, favorite, abilities, types)
}

