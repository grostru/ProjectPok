package com.grt.pokemon.data

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.random.Random

/**
 * Created por Gema Rosas Trujillo
 * 31/01/2022
 *
 * Clase en el que se definen algunos métodos que pueden ser de utilidad a toda la app
 */
object UtilsPokemons  {

    // Método que almacena en disco la imagen
    fun saveToInternalStorage(name : String, bitmapImage: Bitmap, context: Context): String {
        val cw = ContextWrapper(context.applicationContext)
        val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
        // Create imageDir
        val mypath = File(directory, name+".jpg")

        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(mypath)
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)

            fos.flush()

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return mypath.path
    }

    fun isNetworkAvailable(context: Context) =
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
            getNetworkCapabilities(activeNetwork)?.run {
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            } ?: false
        }

    fun randomList(i:Int, j:Int, size:Int): List<Int>{
        val listaResult = mutableListOf<Int>()
        while (listaResult.size < 40){
            var rand = Random.nextInt(i, j)
            if (!listaResult.contains(rand)) listaResult.add(rand)
        }
        return listaResult
    }
}