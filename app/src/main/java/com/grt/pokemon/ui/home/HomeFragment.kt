package com.grt.pokemon.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grt.pokemon.R
import com.grt.pokemon.common.BaseFragment
import com.grt.pokemon.common.NavData
import com.grt.pokemon.databinding.FragmentHomeBinding
import com.grt.pokemon.domain.model.PokemonModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 * Clase encargada de la opción de Menu Inicio, en el que se muestra la lista de Pokemons
 * obtenida de Retrofit y se muestra en el Recicler.
 * También se encarga de gestionar el buscador que aparece encima del recicler y que filtra los
 * resultados
 */
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val vm: HomeViewModel by sharedViewModel()

    private var oldFilterText =""

    private val homeAdapter by lazy {
        HomeAdapter(){
            // Capturamos la acción de pulsar en un elemento de la lista
            vm.onActionPokemonClicked(it)
        }
    }
    override fun provideBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observamos la lista obtenida
        setupBinding()

        // Iniciamos la ReciclyerView
        setupRecycler()

    }

    private fun setupBinding() {
        observeData(vm.obsListPokemons,::onObserveList)
    }

    private fun onObserveList(list: List<PokemonModel>) {
        homeAdapter.updateList(list)
    }

    private fun setupRecycler() {
        with(binding) {
            rvHome.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvHome.adapter = homeAdapter

            // Añadimos al recycler una decoración divisoria
            val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            requireContext().getDrawable(R.drawable.line_divider)
                ?.let { dividerItemDecoration.setDrawable(it) }
            rvHome.addItemDecoration(dividerItemDecoration)

            // Creamos el listener que nos va a permitir buscar un elemento en la lista de Pokemon
            pokemonSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText == null || newText == ""){
                        vm.onInitialization()
                    } else {
                        if (oldFilterText.length >= newText.length) {
                            setupBinding()
                            homeAdapter.filter.filter(newText)

                        } else {
                            homeAdapter.filter.filter(newText)
                        }
                    }
                    oldFilterText = newText!!
                    return false
                }
            })

            // Creamos el objeto que permite el borrado de uno de los elementos de la lista de Pokemon
            val deleteHelper = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    vm.onActionOnItemSwiped(viewHolder.adapterPosition)
                }
            }
            ItemTouchHelper(deleteHelper).attachToRecyclerView(rvHome)
        }
    }

    override fun onNavigate(navData: NavData) {
        when(navData.id){
            HomeViewModel.NAV_DETAIL ->{

                val pokemon = navData.data as PokemonModel

                findNavController().navigate(HomeFragmentDirections.actionNavHomeToDetailFragment(pokemon))
            }
        }
    }
}