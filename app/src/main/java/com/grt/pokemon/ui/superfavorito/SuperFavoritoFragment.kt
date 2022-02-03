package com.grt.pokemon.ui.superfavorito

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.grt.pokemon.common.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.grt.pokemon.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.grt.pokemon.data.UtilsPokemons
import com.grt.pokemon.databinding.FragmentSuperfavoritoBinding
import com.grt.pokemon.ui.dialog.DialogFragment
import kotlinx.coroutines.launch

import java.io.*

/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 *
 * Clase encargada de mostrar la imagen del Pokemon Super Favorito almacenado en el perfil
 * del Data Store
 */
class SuperFavoritoFragment : BaseFragment<FragmentSuperfavoritoBinding, SuperFavoritoViewModel>() {

    private val dialogMessage by lazy { DialogFragment.newInstance() }

    override val vm: SuperFavoritoViewModel by viewModel()

    val vmHome: HomeViewModel by sharedViewModel()

    override fun provideBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentSuperfavoritoBinding {
        return FragmentSuperfavoritoBinding.inflate(inflater,container,false)
    }

    override fun onResume() {
        vm.onShowFab(false)
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.onInit()
        observeData(vm.obsIdPok,::onObserveIdPok)
    }

    private fun onObserveIdPok(idPok:String) {

        if (!idPok.isNullOrEmpty()){
            val listPokemons = vmHome.obsListPokemons.value
            if (!listPokemons.isNullOrEmpty()) {

                val listaIdPok = listPokemons.filter {
                    it.id == idPok.toInt()
                }

                val imgFile = File(listaIdPok[0]?.url_image_default)
                if (imgFile.exists()) {
                    val myBitmap = BitmapFactory.decodeFile(imgFile.path)
                    binding.ivSSStar.setImageBitmap(myBitmap)
                }

                // Evento del botón Compartir con Whatsapp tu imagen de tu Pokemon Super Favorito
                binding.btShare.setOnClickListener {
                    lifecycleScope.launch {
                        if (UtilsPokemons.isNetworkAvailable(requireContext())){
                            share()
                        } else {
                            dialogMessage.show(parentFragmentManager, SuperFavoritoFragment::class.java.name, "Revise su conexión a Internet"){
                                dialogMessage.dismiss(parentFragmentManager)
                            }
                        }

                    }
                }

        } else {
                dialogMessage.show(parentFragmentManager, SuperFavoritoFragment::class.java.name, "Seleccione antes su Pókemon Super Favorito"){
                    findNavController().navigate(SuperFavoritoFragmentDirections.actionNavShareToNavHome())
                    dialogMessage.dismiss(parentFragmentManager)
                }
            }
        } else {
            dialogMessage.show(parentFragmentManager, SuperFavoritoFragment::class.java.name, "Seleccione antes su Pókemon Super Favorito"){
                findNavController().navigate(SuperFavoritoFragmentDirections.actionNavShareToNavHome())
                dialogMessage.dismiss(parentFragmentManager)
            }
        }
    }

    // Función que permite compartir la imagen del botón Super Favorito con tus amigos del Whatsapp
    fun share() {

        val bitMap : Bitmap = binding.ivSSStar.drawable.toBitmap()
        val bos : ByteArrayOutputStream = ByteArrayOutputStream()
        bitMap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        val file = File(requireContext().externalCacheDir, "imagenPokemon.png")
        try {
            file.createNewFile()
            val fos : FileOutputStream = FileOutputStream(file)
            fos.write(bos.toByteArray())
        } catch (e : IOException) {
            e.printStackTrace();
        }

        val intent= Intent(Intent.ACTION_SEND)
        intent.setType("image/jpeg")
        intent.setPackage("com.whatsapp")
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(file.path))

        startActivity(Intent.createChooser(intent, "Share Image"))
    }
}