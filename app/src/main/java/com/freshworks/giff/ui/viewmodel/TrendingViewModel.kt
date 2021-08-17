package com.freshworks.giff.ui.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.freshworks.giff.di.Kodein.App
import com.freshworks.giff.db.entities.Giff
import com.freshworks.giff.model.GiffModel
import com.freshworks.giff.repositories.TrendingRepository
import com.freshworks.giff.util.Constants.Companion.DEFAULT_LIMIT
import com.freshworks.giff.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException



class TrendingViewModel(
    app: Application,//Application instance is passed as constructor
    private val repository: TrendingRepository////TrendingRepository instance is passed as constructor
) : AndroidViewModel(app) { //extends app for getting systems services
    ////
    val __gif: MutableLiveData<Resource<GiffModel>> = MutableLiveData()
    var GiffPage = 1 //not needed ,passing current page from view
    var GiffResponse: GiffModel? = null
    /////

    private lateinit var job: Job
    var offset = 1


    suspend fun saveGiff(gifff: Giff) = repository.saveGiff(gifff) //for saving selected items to room

    suspend fun deleteGiff(gifff: String) = repository.deleteGiff(gifff) //deleting by unfavoriting

    fun loadGiffs(currentPage: Int, maxPage: Int, q: String) = viewModelScope.launch {
        offset = currentPage * DEFAULT_LIMIT //offset is seting up by 25,50,75... on each scroll,currnt page passed frm ui
        safeApiCall(currentPage, q)
    }

    private fun handleResponse(response: Response<GiffModel>): Resource<GiffModel> {  //handling response
        if (response.isSuccessful) { //i avoided safeapi call, and accessing direct retrofit response.
            response.body()?.let { resultResponse ->
                if (GiffResponse == null) {
                    GiffResponse = resultResponse
                } else {
                    val oldArticles = GiffResponse?.data
                    val newArticles = resultResponse.data
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(GiffResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private suspend fun safeApiCall(currentPage: Int, q: String) {
        __gif.postValue(Resource.Loading())
        try {
            if (isInternet()) {
                if (q != null && !q.isEmpty()) {
                    if (currentPage == 1) {
                        GiffResponse = null
                    }
                    val response = repository.getSearchGiff(offset, q)
                    __gif.postValue(handleResponse(response))
                } else {
                    if (currentPage == 1) {
                        GiffResponse = null
                    }
                    val response = repository.getTrendingGiffs(offset)
                    __gif.postValue(handleResponse(response))
                }
            } else {
                __gif.postValue(Resource.Error("No connection to the network"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> __gif.postValue(Resource.Error("Network Failure"))
                else -> __gif.postValue(Resource.Error("Error " + t.toString()))
            }
        }
    }

    private fun isInternet(): Boolean {
        val connectivityManager = getApplication<App>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    ConnectivityManager.TYPE_WIFI -> true
                    else -> false
                }
            }
        }
        return false
    }

    /*   fun get_giffs() {
           job = Coroutines.ioThenMain(
               { repository.getTrendingGiff(1) },
               { _giffs.value = it!!.data }
           )
       }*/
    override fun onCleared() {
        super.onCleared()
        if (::job.isInitialized) job.cancel()
        GiffResponse = null
    }
}




