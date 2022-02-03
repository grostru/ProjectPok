package com.grt.pokemon.ui.share

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.grt.pokemon.common.BaseFragment
import com.grt.pokemon.databinding.FragmentShareBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.grt.pokemon.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.grt.pokemon.data.UtilsPokemons
import com.grt.pokemon.domain.model.PokemonModel
import com.grt.pokemon.ui.dialog.DialogFragment
import kotlinx.coroutines.launch

import java.io.*

/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 *
 * Clase encargada de poder Compartir la imagen del Pokemon Super Favorito almacenada
 * en el Perfil con tus amigos por cualquier medio elegido que posea el dispositivo
 */
class ShareFragment : BaseFragment<FragmentShareBinding, ShareViewModel>() {

    private val dialogMessage by lazy { DialogFragment.newInstance() }

    override val vm: ShareViewModel by viewModel()

    val vmHome: HomeViewModel by sharedViewModel()

    override fun provideBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentShareBinding {
        return FragmentShareBinding.inflate(inflater,container,false)
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

                // Evento del botón Añadir ingrediente
                binding.btShare.setOnClickListener {
                    lifecycleScope.launch {
                        if (UtilsPokemons.isNetworkAvailable(requireContext())){
                            share()
                        } else {
                            dialogMessage.show(parentFragmentManager, ShareFragment::class.java.name, "Revise su conexión a Internet"){
                                dialogMessage.dismiss(parentFragmentManager)
                            }
                        }

                    }
                }

        } else {
                dialogMessage.show(parentFragmentManager, ShareFragment::class.java.name, "Seleccione antes su Pókemon Super Favorito"){
                    findNavController().navigate(ShareFragmentDirections.actionNavShareToNavHome())
                    dialogMessage.dismiss(parentFragmentManager)
                }
            }
        } else {
            dialogMessage.show(parentFragmentManager, ShareFragment::class.java.name, "Seleccione antes su Pókemon Super Favorito"){
                findNavController().navigate(ShareFragmentDirections.actionNavShareToNavHome())
                dialogMessage.dismiss(parentFragmentManager)
            }
        }
    }

    // Función que realiza la llamada al Intent de Compartir con la imagen del Pokemon
    // Super favorito que el usuario tenía persistida en DataStore
    fun share() {
        var drawable = binding.ivSSStar.drawable as BitmapDrawable
        var bitmap = drawable.bitmap
        try {
            var file = File(requireContext().externalCacheDir, "pokemonSuperStar.jpg")
            var fout = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout)
            fout.flush()
            fout.close()
            file.setReadable(true, false)
            var uri = FileProvider.getUriForFile(requireContext(), "com.grt.pokemon.provider", file)
            var intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            startActivity(intent)
        } catch (e: FileNotFoundException) {
            Log.d("TAG", "share: Something went wrong")
        }
    }
}