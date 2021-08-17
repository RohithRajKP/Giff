package com.freshworks.giff.repositories


import com.freshworks.giff.db.AppDatabase
import com.freshworks.giff.db.entities.Giff
import com.freshworks.giff.network.GiffAPI
import net.simplifiedcoding.data.repositories.SafeApiRequest

/*
2 classes are passed as constructor, GiffApi,Appdatabase instce for accessing api and room db
 GiffAPI,
 AppDatabase
 */
class TrendingRepository(
    private val api: GiffAPI,
    private val db: AppDatabase
) : SafeApiRequest() {

    suspend fun getTrendingGiffOld(offset: Int) = apiRequest { api.getGifs(offset) }///not using now

    suspend fun getSearchGiff(offset: Int, q: String) = api.getSerachGifs(offset, q)//api call to Search GIF Endpoint

    suspend fun getTrendingGiffs(offset: Int) = api.getGifs(offset)//api call to Trending GIF Endpoint

    suspend fun saveGiff(giff: Giff) = db.getGiffDao().saveGiff(giff)//save to room db

    suspend fun deleteGiff(id: String) = db.getGiffDao().deletegiffs(id)//delete from room db with giff id

}