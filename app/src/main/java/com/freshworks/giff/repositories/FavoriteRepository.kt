package com.freshworks.giff.repositories
import com.freshworks.giff.db.AppDatabase
class FavoriteRepository(
    private val db: AppDatabase
)  {

    suspend fun deleteGiff(id: String) = db.getGiffDao().deletegiffs(id) //deleting from fav screen with id

    suspend fun getGiff() = db.getGiffDao().getGiffs()//load  fav screen from room

 //  suspend  fun getGiffTest() = db.getGiffDao().getGiffTest()
    suspend fun getgiffbyId(id: String) = db.getGiffDao().getgiffbyId(id) //for unit testing
}