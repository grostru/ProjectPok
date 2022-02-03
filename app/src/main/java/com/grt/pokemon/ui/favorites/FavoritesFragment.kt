package com.grt.pokemon.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.grt.pokemon.R
import com.grt.pokemon.common.BaseFragment
import com.grt.pokemon.common.NavData
import com.grt.pokemon.databinding.FragmentFavoritesBinding
import com.grt.pokemon.domain.model.PokemonModel
import com.grt.pokemon.ui.home.HomeAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
/**
 * Created por Gema Rosas Trujillo
 * 28/01/2022
 *
 * Clase encargada del fragmento en el que se muestran los Pokemon marcados como favoritos
 * Si se realiza onClick en cada uno de los elementos vamos también a la lista de Detalles
 * usando el mismo fragmento y funcionalidad
 */
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding, FavoritesViewModel>() {

    override val vm: FavoritesViewModel by viewModel()

    private val homeAdapter by lazy {
        HomeAdapter(){
            // Capturamos la acción de pulsar en un elemento de la lista
            vm.onActionPokemonClicked(it)
        }
    }

    override fun provideBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentFavoritesBinding {
        return FragmentFavoritesBinding.inflate(inflater,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.onInit()
        // Iniciamos la ReciclyerView
        setupRecyclerFavorites()
        // Observamos la lista obtenida
        setupBindingFavorites()
    }

    private fun setupRecyclerFavorites() {
        with(binding) {
            rvFavorites.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvFavorites.adapter = homeAdapter

            // Añadimos al recycler una decoración divisoria definida en un Drawable personalizado
            val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            requireContext().getDrawable(R.drawable.line_divider)
                ?.let { dividerItemDecoration.setDrawable(it) }
            rvFavorites.addItemDecoration(dividerItemDecoration)

        }
    }

    private fun setupBindingFavorites() {
        observeData(vm.obsListPokemonsFavorites,::onObserveList)
    }

    private fun onObserveList(list: List<PokemonModel>) {
        homeAdapter.updateList(list)
    }

    override fun onNavigate(navData: NavData) {
        when(navData.id){
            FavoritesViewModel.NAV_DETAIL ->{

                val pokemon = navData.data as PokemonModel

                findNavController().navigate(FavoritesFragmentDirections.actionNavFavoritesToDetailFragment(pokemon))
            }
        }
    }

    override fun onResume() {
        vm.onShowFab(false)
        super.onResume()
    }
}