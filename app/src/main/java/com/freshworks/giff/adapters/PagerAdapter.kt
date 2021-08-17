package com.freshworks.giff.adapters
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/*
this is the class for viewpager adapter
it is accessing from mainviewmodel class and passing required credentials like
fragment manger,fragmnet names,list of fragments etc..
 */

class PagerAdapter(fragmentManager: FragmentManager, private val titles: List<CharSequence>, private val conetent: List<Fragment>)
    : FragmentStatePagerAdapter(fragmentManager) {
    private val numOfTabs: Int = titles.size
    private val PAGE1 = 0
    private val PAGE2 = 1
    private lateinit var fragment: Fragment
    override fun getItem(position: Int): Fragment {
        when (position) {
            PAGE1 -> fragment = conetent[0]//page 1 for fragment 0 from the list
            PAGE2 -> fragment = conetent[1]
        }
        return fragment //note must use lateinint var property to return fragments
    }

    override fun getCount(): Int {
        return numOfTabs //this case it is 2

    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

}