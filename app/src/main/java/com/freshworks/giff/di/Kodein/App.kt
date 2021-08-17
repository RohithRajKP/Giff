package com.freshworks.giff.di.Kodein

import android.app.Application
import com.freshworks.giff.db.AppDatabase
import com.freshworks.giff.ui.fragment_favorite.FavoriteViewModelFactory
import com.freshworks.giff.network.GiffAPI
import com.freshworks.giff.repositories.FavoriteRepository
import com.freshworks.giff.repositories.TrendingRepository
import com.freshworks.giff.ui.fragment_trending.TrendingViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class App : Application() , KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@App)) //aPP instance
        bind() from singleton { AppDatabase(instance()) }// DataBase instance
        bind() from singleton { TrendingRepository(GiffAPI(), instance()) } //Trending repo requires api & Db which supplied here
        bind() from singleton { FavoriteRepository(instance()) }//Fv repo requires appdb which is supplied above
        bind() from provider { TrendingViewModelFactory(this@App, instance()) }//Trendingvwmodel fact requires Applicatn and TrendingRepository
        bind() from provider { FavoriteViewModelFactory(instance()) }//it requires FavoriteRepository which is supplied above
    }
}