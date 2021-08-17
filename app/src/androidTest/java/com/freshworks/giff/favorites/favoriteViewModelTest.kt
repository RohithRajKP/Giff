package com.freshworks.giff.favorites

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.freshworks.giff.db.AppDatabase
import com.freshworks.giff.db.GiffValues
import com.freshworks.giff.db.getOrAwaitValue
import com.freshworks.giff.repositories.FavoriteRepository
import com.freshworks.giff.ui.viewmodel.favoriteViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class favoriteViewModelTest {
    private lateinit var viewModel: favoriteViewModel
    private lateinit var values: GiffValues

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>() //context instsnce
        val db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries().build()//db instsnce
        val repo = FavoriteRepository(db)// repo instnce
        val dataSource = favoriteViewModel(repo)//
        viewModel = dataSource //view model instnce
        values = GiffValues()// dummy values from class
    }

    @Test
    fun testbyIdViewModel() = runBlocking {
        viewModel.getgiffbyId() //getting dummy data with id store in data class giffvalues
        val result = viewModel._favLiveData.getOrAwaitValue().find {
            it.url == values.url
        }
        Truth.assertThat(result != null).isTrue() //if result conatons url from dummy class test is passed
    }

    @Test
    fun testSpdViewModel() = runBlocking {

        /*
        note before running this test run app data base test beacauese the values are inserting
        from appdata bse test.
        in fav view model we are only deleting and selecting from room db
         */

        viewModel.deleteGiff(values.id)
        viewModel.getgiffbyId()
        val result = viewModel._favLiveData.getOrAwaitValue().find {
            it.url == values.url
        }
        Truth.assertThat(result == null).isTrue()

    }

    @Test
    fun loadFavViewModel() = runBlocking {

        val result = viewModel.Fav
        val complted= result.getCompleted().value?.size
        Truth.assertThat(result == null).isTrue()


    }

}