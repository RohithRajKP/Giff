package com.freshworks.giff.network


import com.freshworks.giff.model.GiffModel
import com.freshworks.giff.util.Constants.Companion.API_KEY
import com.freshworks.giff.util.Constants.Companion.BASE_URL
import com.freshworks.giff.util.Constants.Companion.DEFAULT_LIMIT
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GiffAPI {
companion object{
    operator fun invoke() : GiffAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL) //declred in constant file
            .build()
            .create(GiffAPI::class.java)
    }
}
/*
  Api call to trending giff
  the rsponse is taking as Response<GiffModel>
 https://api.giphy.com/v1/gifs/trending?api_key=RH6oZROk2Eej9Q8H4CFLyKQ88vJ3qjFT&offset=50&limit=25
 */
    @GET("/v1/gifs/trending")
    suspend  fun getGifs(
        @Query("offset") offset: Int,
        @Query("api_key") apiKey: String = API_KEY,//the genrated key from giff site
        @Query("limit") limit: Int = DEFAULT_LIMIT//limit of items in each call
    ): Response<GiffModel>
/*
Api call to trending giff
 api.giphy.com/v1/gifs/search?api_key=RH6oZROk2Eej9Q8H4CFLyKQ88vJ3qjFT&offset=50&limit=1&q=morning
 */
    @GET("/v1/gifs/search")
    suspend  fun getSerachGifs(
        @Query("offset") offset: Int,
        @Query("q") q: String,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("limit") limit: Int = DEFAULT_LIMIT

    ): Response <GiffModel>

}