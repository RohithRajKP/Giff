package com.freshworks.giff.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.freshworks.giff.db.entities.Giff
import net.simplifiedcoding.mvvmsampleapp.data.db.GiffDao


@Database(
    entities = [Giff::class], //i have used only one entity class for this project
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getGiffDao(): GiffDao


    companion object {

        @Volatile
        var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "GiffDB.db"
            ).build()
    }
}