package com.indisp.astrogallery.favourites.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.indisp.astrogallery.databinding.LayoutFavouriteItemBinding
import com.indisp.astrogallery.core.domain.model.Apod
import java.time.format.DateTimeFormatter
import java.util.*

class FavouriteListAdapter(val onItemClick: (Apod) -> Unit, val removeFromFav: (Apod) -> Unit) :
    ListAdapter<Apod, FavouriteListAdapter.FavouriteItemViewHolder>(FavouriteItemDiffUtil) {

    private val dateFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.getDefault())

    inner class FavouriteItemViewHolder(private val viewBinding: LayoutFavouriteItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(apod: Apod) {
            with(viewBinding) {
                author.text = if(apod.author.isBlank()) "\u00a9 N/A" else "\u00a9 ${apod.author}"
                title.text = apod.title
                date.text = "Shot on ${dateFormatter.format(apod.date)}"
                Glide.with(viewBinding.root).load(apod.url).into(viewBinding.pictureOfTheDay)
                removeFromFav.setOnClickListener {
                    removeFromFav(apod)
                }
                root.setOnClickListener {
                    onItemClick(apod)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteItemViewHolder {
        return FavouriteItemViewHolder(
            LayoutFavouriteItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavouriteItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

private object FavouriteItemDiffUtil : DiffUtil.ItemCallback<Apod>() {
    override fun areItemsTheSame(oldItem: Apod, newItem: Apod): Boolean {
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: Apod, newItem: Apod): Boolean {
        return oldItem == newItem
    }

}