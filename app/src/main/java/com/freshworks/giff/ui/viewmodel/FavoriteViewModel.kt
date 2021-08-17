package com.freshworks.giff.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freshworks.giff.db.entities.Giff

import com.freshworks.giff.repositories.FavoriteRepository
import com.freshworks.giff.util.lazyDeferred
import kotlinx.coroutines.launch

class favoriteViewModel(
    val favRepository: FavoriteRepository
) : ViewModel() {
////// created for unit testing/////
    private val favLiveData = MutableLiveData<List<Giff>>()
    val _favLiveData: LiveData<List<Giff>>
        get() = favLiveData

    fun getgiffbyId() = viewModelScope.launch {
        favLiveData.value = favRepository.getgiffbyId("abcd1450qwe")
/*   note ::
        this id is inserted from app database test details are listed in GiffValues class
        please do unit test for db before testing favorite screen
*/
    }
////// end of unit testing/////////////////

    suspend fun deleteGiff(gifff: String) =
        favRepository.deleteGiff(gifff)//deleting from fav screen

    val Fav by lazyDeferred {
        favRepository.getGiff() //observing with lazydeffer
    }

}












