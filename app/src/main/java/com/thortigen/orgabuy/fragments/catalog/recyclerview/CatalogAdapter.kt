package com.thortigen.orgabuy.fragments.catalog.recyclerview

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thortigen.orgabuy.R
import com.thortigen.orgabuy.data.models.CartItem
import com.thortigen.orgabuy.data.models.CatalogItem
import com.thortigen.orgabuy.data.models.ShopListItem
import com.thortigen.orgabuy.databinding.FragmentCatalogRvItemBinding
import com.thortigen.orgabuy.viewmodels.CartViewModel
import com.thortigen.orgabuy.viewmodels.ShopListViewModel

class CatalogAdapter : RecyclerView.Adapter<CatalogAdapter.ItemViewHolder>() {

    var catalogItemList = emptyList<CatalogItem>()

    val mShopListViewModel = ShopListViewModel(application = Application())
    val mCartViewModel = CartViewModel(application = Application())

    class ItemViewHolder(private val binding: FragmentCatalogRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val addToShopListButton: ImageButton =
            binding.root.findViewById(R.id.rv_btn_item_add_to_shop_list)

        val removeFromShopListButton: ImageButton =
            binding.root.findViewById(R.id.rv_btn_item_remove_from_shop_list)

        val textView: TextView = binding.root.findViewById(R.id.rv_tv_item_name)

        fun bind(catalogItem: CatalogItem) {
            binding.catalogItem = catalogItem
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ItemViewHolder {

                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentCatalogRvItemBinding.inflate(layoutInflater, parent, false)
                return ItemViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val currentCatalogItem = catalogItemList[position]
        val shopListItem = ShopListItem(currentCatalogItem.id, currentCatalogItem.name, currentCatalogItem.description, 0)

        holder.addToShopListButton.setOnClickListener {
            mShopListViewModel.insertItem(shopListItem)
            SetItemStatus(holder, true)
        }

        holder.removeFromShopListButton.setOnClickListener {
            mShopListViewModel.deleteItem(shopListItem)

            val cartItem = CartItem(currentCatalogItem.id, currentCatalogItem.name, currentCatalogItem.description, 0.0, 0.0)
            mCartViewModel.deleteItem(cartItem)

            SetItemStatus(holder, false)
        }

        if (mShopListViewModel.checkForItemIsInList(shopListItem.id)) {
            SetItemStatus(holder, true)
        } else {
            SetItemStatus(holder, false)
        }


        holder.bind(currentCatalogItem)
    }

    override fun getItemCount(): Int {
        return catalogItemList.size
    }

    fun setData(catalogItems: List<CatalogItem>) {
        val catalogAdapterDiffUtils = CatalogAdapterDiffUtils(catalogItemList, catalogItems)
        val catalogAdapterDiffResult = DiffUtil.calculateDiff(catalogAdapterDiffUtils)
        catalogItemList = catalogItems
        catalogAdapterDiffResult.dispatchUpdatesTo(this)
    }

    fun SetItemStatus(holder: ItemViewHolder, addToList: Boolean) {
        if (addToList) {
            holder.addToShopListButton.visibility = View.INVISIBLE
            holder.removeFromShopListButton.visibility = View.VISIBLE
            holder.textView.setTextColor(
                holder.itemView.getResources().getColorStateList(R.color.design_default_color_primary_variant)
            )
        } else {
            holder.addToShopListButton.visibility = View.VISIBLE
            holder.removeFromShopListButton.visibility = View.INVISIBLE
            holder.textView.setTextColor(
                holder.itemView.getResources().getColorStateList(R.color.design_default_color_primary)
            )
        }
    }

}