package com.example.tarkovloot.app.screen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.tarkovloot.R
import com.example.tarkovloot.app.model.Item
import com.example.tarkovloot.databinding.ItemItemBinding


class MainAdapter : RecyclerView.Adapter<MainAdapter.ItemViewHolder>() {
    class ItemViewHolder(
        val binding: ItemItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    var itemList = listOf<Item>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemItemBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]

        with(holder.binding) {

            itemNameTextView.text = item.name
            itemPriceTextView.text = item.basePrice.toString()

            if (item.iconLink.isNotBlank()) {
                Glide.with(itemImageView.context)
                    .load(item.iconLink)
                    //.circleCrop()
                    .transform(CenterInside(), RoundedCorners(24))
                    .placeholder(R.drawable.ic_baseline_hide_image)
                    .error(R.drawable.ic_baseline_hide_image)
                    .into(itemImageView)
            } else {
                itemImageView.setImageResource(R.drawable.ic_baseline_hide_image)
            }
        }
    }

    override fun getItemCount() = itemList.size
}