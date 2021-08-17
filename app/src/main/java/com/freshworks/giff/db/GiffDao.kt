package net.simplifiedcoding.mvvmsampleapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.freshworks.giff.db.entities.Giff


@Dao
interface GiffDao { //data access object in local db

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGiff(giff: Giff) //for saving to room

    @Query("SELECT * FROM Giff")
    fun getGiffs(): LiveData<List<Giff>>// //Resource<LiveData<List<Giff>>> getting room db

    @Query("DELETE FROM Giff WHERE id = :id")
    suspend fun deletegiffs(id: String) //dlete fro room


    @Query("SELECT * FROM Giff WHERE id = :id")
    suspend fun getgiffbyId(id: String): List<Giff> //get by specific id, for unit testing

}