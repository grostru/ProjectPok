package com.grt.pokemon.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.grt.pokemon.common.BaseDialogFragment
import com.grt.pokemon.databinding.FragmentDialogBinding

/**
 * Created por Gema Rosas Trujillo
 * 31/01/2022
 *
 * Clase que se encarga de mostrar o ocultar el Fragmento encargado del Dialogo
 * que se muestra para avisos o errores
 */
class DialogFragment : BaseDialogFragment<FragmentDialogBinding>() {

    companion object{
        fun newInstance(): DialogFragment{
            return DialogFragment()
        }
    }

    private var acceptListener: (()->Unit)?=null
    private var message:String?=null

    override fun provideBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentDialogBinding {
        return FragmentDialogBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            tvDialogErrorDescription.text = message
            btDialogErrorTitle.setOnClickListener {
                acceptListener?.invoke()
            }
        }
    }

    fun show(fragmentManager: FragmentManager,tag:String,message:String,acceptListener:()->Unit){
        (fragmentManager.findFragmentByTag(tag) as? DialogFragment)?.dismiss()
        this.message = message
        this.acceptListener = acceptListener
        show(fragmentManager,tag)
    }

    fun dismiss(fragmentManager: FragmentManager){
        (fragmentManager.findFragmentByTag(tag) as? DialogFragment)?.also {
            it.dismiss()
        }
    }
}