package com.freshworks.giff.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.freshworks.giff.db.entities.Giff
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import net.simplifiedcoding.mvvmsampleapp.data.db.GiffDao
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AppDatabaseTest : TestCase() {

    private lateinit var db: AppDatabase
    private lateinit var dao: GiffDao
    private lateinit var values:GiffValues

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>() //getting context
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build() //room instance
        dao = db.getGiffDao() //dao instance
        values=GiffValues()// sample data class values
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndLoadGiff() = runBlocking {
        val id=values.id
        val title = values.title
        val url = values.url //setting testing parameters
        val giff = Giff(id, title, url, true)

        dao.saveGiff(giff)  //saving dummy data
        val giffs = dao.getgiffbyId(id)//selecting corresponding dummy data by id
        assertThat(giffs.contains(giff)).isTrue() //if dummy data is equal to dummy data we set , test is considered to be passed
    }


}


