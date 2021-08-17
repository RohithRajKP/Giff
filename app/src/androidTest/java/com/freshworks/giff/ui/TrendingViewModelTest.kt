package com.freshworks.giff.ui

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.freshworks.giff.di.Kodein.App
import com.freshworks.giff.db.AppDatabase
import com.freshworks.giff.db.GiffValues
import com.freshworks.giff.db.entities.Giff
import com.freshworks.giff.network.GiffAPI
import com.freshworks.giff.repositories.TrendingRepository
import com.freshworks.giff.ui.viewmodel.TrendingViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/*
 app: Application,
    private val repository: TrendingRepository
 */
@RunWith(AndroidJUnit4::class)
class TrendingViewModelTest {
    private lateinit var viewModel: TrendingViewModel
    private lateinit var values: GiffValues
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // private val api: GiffAPI,
//    private val db: AppDatabase
    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries().build()
        val app = context as App
        val api = GiffAPI.invoke()
        val repo = TrendingRepository(api, db)
        val dataSource = TrendingViewModel(app, repo)
        viewModel = dataSource
    }


    @Test
    fun testSpendViewModel() = runBlocking {

        val gGiff = Giff(
            values.id, values.title, values.url, true
        )
        //viewModel.saveGiff(gGiff)
      //  viewModel.loadGiffs(1, 1, "")

//        val result = viewModel.__gif.getOrAwaitValue().find {
//
//        }
     //   Truth.assertThat(result != null).isTrue()
    }
}