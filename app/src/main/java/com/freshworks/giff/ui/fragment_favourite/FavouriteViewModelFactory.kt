package com.freshworks.giff.ui.fragment_favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.freshworks.giff.repositories.FavouriteRepository
import com.freshworks.giff.ui.viewmodel.favoriteViewModel


@Suppress("UNCHECKED_CAST")
class FavoriteViewModelFactory(

    private val repository: FavouriteRepository
) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return favoriteViewModel(repository) as T
    }

}