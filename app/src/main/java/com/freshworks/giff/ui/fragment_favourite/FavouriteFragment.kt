package com.freshworks.giff.ui.fragment_favourite

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.freshworks.giff.R
import com.freshworks.giff.adapters.FavoriteAdapter
import com.freshworks.giff.clicklisteners.RecyclerViewClickListenerFav
import com.freshworks.giff.db.entities.Giff
import com.freshworks.giff.ui.viewmodel.SharedViewModel
import com.freshworks.giff.ui.viewmodel.favoriteViewModel
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class FavouriteFragment : Fragment(R.layout.fragment_favourites), RecyclerViewClickListenerFav,
    KodeinAware {
    override val kodein by kodein() //dependency injection
    lateinit var viewModel: favoriteViewModel
    lateinit var favAdapter: FavoriteAdapter
    /*
       private lateinit var factory: FavoriteViewModelFactory
       facory is replaced with dependecncy injection kodein
       we can access the viewmodel factory by calling instance().
  */
    private val factory: FavoriteViewModelFactory by instance()
    lateinit var model: SharedViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
/*
        val db = AppDatabase.invoke(requireContext())
        val repository = FavouriteRepository(db)
        factory = FavoriteViewModelFactory(repository)
        note: with out Kodein we require these object for accessing viewmodel , but kodein simplifies the
        dependency.
*/
        viewModel = ViewModelProvider(this, factory).get(favoriteViewModel::class.java)
        setupRecyclerView()
        lifecycleScope.launch {
            try {
                viewModel.Fav.await().observe(viewLifecycleOwner, Observer { Giff ->
                    if (Giff.size == 0) {
                        favourites_view_empty.visibility = View.VISIBLE///Animation visible
                    } else {
                        favourites_view_empty.visibility = View.INVISIBLE ///Aniamtion  INVISIBLE
                    }
                    favAdapter.differ.submitList(Giff.toList())

                })
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private fun setupRecyclerView() {
        favAdapter = FavoriteAdapter(this)
        rv_fav.apply {
            adapter = favAdapter
            layoutManager = GridLayoutManager(activity, 2)//LinearLayoutManager(activity)
        }
    }

    override fun onRecyclerViewItemClickFav(view: View, giff: Giff, position: Int) {
        when (view.id) {
            R.id.img_del -> {
                model = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
                model.sendGiffId(giff.id)
                lifecycleScope.launch {
                    try {
                        Log.d("", giff.id)
                        viewModel.deleteGiff(giff.id)
                        // viewModel.loadGiffs()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                Toast.makeText(
                    requireContext(),
                    giff.title + " removed from Favourites",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }
}