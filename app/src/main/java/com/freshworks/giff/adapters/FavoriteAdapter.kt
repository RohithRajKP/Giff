package com.freshworks.giff.adapters

//import com.saagsystems.tabtest.databinding.RecyclerviewMovieBinding
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.freshworks.giff.R
import com.freshworks.giff.clicklisteners.RecyclerViewClickListenerFav
import com.freshworks.giff.databinding.RecyclerviewFavBinding
import com.freshworks.giff.db.entities.Giff

class FavoriteAdapter(
    private val listener: RecyclerViewClickListenerFav
) : RecyclerView.Adapter<FavoriteAdapter.FavoritesViewHolder>() {

    override fun getItemCount(): Int {
        Log.e("gif size", "total giffs" + differ.currentList.size)
        return differ.currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FavoritesViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recyclerview_fav,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {

        val Giffy = differ.currentList[position]
        holder.recyclerviewGiffBinding.giff = Giffy
        holder.recyclerviewGiffBinding.imgDel.setOnClickListener {
            listener.onRecyclerViewItemClickFav(
                holder.recyclerviewGiffBinding.imgDel,
                Giffy,
                position
            )
        }

    }

    inner class FavoritesViewHolder(
        val recyclerviewGiffBinding: RecyclerviewFavBinding
    ) : RecyclerView.ViewHolder(recyclerviewGiffBinding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Giff>() {
        override fun areItemsTheSame(oldItem: Giff, newItem: Giff): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Giff, newItem: Giff): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

}