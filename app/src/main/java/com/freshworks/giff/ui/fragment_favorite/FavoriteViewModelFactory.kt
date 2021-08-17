package com.freshworks.giff.ui.fragment_favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.freshworks.giff.repositories.FavoriteRepository
import com.freshworks.giff.ui.viewmodel.favoriteViewModel


@Suppress("UNCHECKED_CAST")
class FavoriteViewModelFactory(

    private val repository: FavoriteRepository
) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return favoriteViewModel(repository) as T
    }

}