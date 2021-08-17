package com.freshworks.giff.ui.fragment_trending

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.freshworks.giff.repositories.TrendingRepository
import com.freshworks.giff.ui.viewmodel.TrendingViewModel

//import com.saagsystems.tabtest.model.TrendingRepository


@Suppress("UNCHECKED_CAST")
class TrendingViewModelFactory(
    val app: Application,
    private val repository: TrendingRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TrendingViewModel(app, repository) as T
    }

}