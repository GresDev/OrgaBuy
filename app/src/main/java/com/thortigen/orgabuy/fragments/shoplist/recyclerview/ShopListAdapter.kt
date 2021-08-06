package com.thortigen.orgabuy.fragments.shoplist.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thortigen.orgabuy.data.models.ShopListItem
import com.thortigen.orgabuy.databinding.FragmentShoplistRvItemBinding


class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ItemViewHolder>() {

    var shopListItemList = emptyList<ShopListItem>()

     class ItemViewHolder(private val binding: FragmentShoplistRvItemBinding ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(shopListItem: ShopListItem) {
            binding.shopListItem = shopListItem
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ItemViewHolder {

                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentShoplistRvItemBinding.inflate(layoutInflater, parent, false)
                return ItemViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListAdapter.ItemViewHolder {
        return ShopListAdapter.ItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ShopListAdapter.ItemViewHolder, position: Int) {

        val currentShopListItem = shopListItemList[position]
        holder.bind(currentShopListItem)
    }

    override fun getItemCount(): Int {
        return shopListItemList.size
    }

    fun setData(shopListItems: List<ShopListItem>) {
        val shopListAdapterDiffUtils = ShopListAdapterDiffUtils(shopListItemList, shopListItems)
        val shopListAdapterDiffResult = DiffUtil.calculateDiff(shopListAdapterDiffUtils)
        shopListItemList = shopListItems
        shopListAdapterDiffResult.dispatchUpdatesTo(this)
    }

}