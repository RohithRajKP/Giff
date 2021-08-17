package com.freshworks.giff.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
/*
this view model is used for communication between fragments(Trending fragment and Favorite fragment
while deleting an item from favorite screen it ill communicate to first screen through shared view model using
live data
so that we can avoid call backs and interface for the comminication,
live data is observed from first fragment and will update the ui
 */
class SharedViewModel() : ViewModel()
{
    val id = MutableLiveData<String>()

    fun sendGiffId(giff_id: String) {
        id.value = giff_id

    }
}