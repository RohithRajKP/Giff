package com.freshworks.giff.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.freshworks.giff.R
//import com.freshworks.giff.databinding.RecyclerviewMovieBinding
import com.freshworks.giff.model.Data
import com.freshworks.giff.clicklisteners.RecyclerViewClickListener
import com.freshworks.giff.databinding.RecyclerviewTrendingBinding


class TrendingAdapter(
    private val listener: RecyclerViewClickListener
) : RecyclerView.Adapter<TrendingAdapter.TrendingViewHolder>(){
    override fun getItemCount(): Int {
        Log.e("gif size","total giffs"+differ.currentList.size)
        return differ.currentList.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TrendingViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recyclerview_trending,
                parent,
                false
            )
        )
    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        val Giffy = differ.currentList[position]
        holder.recyclerviewGiffBinding.giff = Giffy
            holder.recyclerviewGiffBinding.imgFav.setOnClickListener {
            listener.onRecyclerViewItemClick(holder.recyclerviewGiffBinding.imgFav, Giffy,position)
        }
        holder.recyclerviewGiffBinding.imgUnfav.setOnClickListener {
            listener.onRecyclerViewItemClick(holder.recyclerviewGiffBinding.imgUnfav, Giffy,position)
        }
    }


    inner class TrendingViewHolder(
        val recyclerviewGiffBinding: RecyclerviewTrendingBinding
    ) : RecyclerView.ViewHolder(recyclerviewGiffBinding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.url == newItem.url
        }
        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

}