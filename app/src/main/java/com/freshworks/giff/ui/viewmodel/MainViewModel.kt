package com.freshworks.giff.ui.viewmodel

import android.content.DialogInterface
import android.view.View
import androidx.databinding.ObservableField
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.freshworks.giff.adapters.PagerAdapter
import com.freshworks.giff.ui.fragment_favorite.FavoriteFragment
import com.freshworks.giff.ui.fragment_trending.TrendingFragment


/*
this is the view model for view pager.
view pager aapter is accessing from viewmodel(mvvm pattern)
events of viewpager(addOnPageChangeListener,setadapter,setCurrentItem) is handled in activity_mail.xml
data binding and it is working fine
 */
class MainViewModel(val contract: MainActivityContract) {
    interface MainActivityContract {
        fun getFragmentManger(): FragmentManager
    }
    var adapter = PagerAdapter(
        contract.getFragmentManger(),
        listOf("Trending", "Favourite"),
        listOf(
            TrendingFragment(),
            FavoriteFragment()

        )
    )
    var currentPosition = 0 //set to xml throgh binding

    var pageChangeListener = object : ViewPager.OnPageChangeListener {

        override fun onPageScrollStateChanged(p0: Int) {
        }

        override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
        }

        override fun onPageSelected(p0: Int) {
            currentPosition = p0
        }
    }


    ////////////////
    private var count: Int = 0

    var alertMessage = ObservableField<String>()

    var clicksMessage = ObservableField("No clicks")

    fun onBtnClick() {
        alertMessage.set("Exit App?")
    }

    fun onDialogOkClick(dialog: DialogInterface?, which: Int) {
        clicksMessage.set(count.toString() + " clicks")
    }

}