package com.freshworks.giff.clicklisteners

import android.view.View
import com.freshworks.giff.db.entities.Giff
import com.freshworks.giff.model.Data

interface RecyclerViewClickListener {
    fun onRecyclerViewItemClick(view: View, giff: Data,position:Int)
}

interface RecyclerViewClickListenerFav {
    fun onRecyclerViewItemClickFav(view: View, giff: Giff,position:Int)
}

