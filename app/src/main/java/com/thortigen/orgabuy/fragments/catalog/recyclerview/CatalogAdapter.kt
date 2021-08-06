package com.thortigen.orgabuy.fragments.catalog.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thortigen.orgabuy.data.models.CatalogItem
import com.thortigen.orgabuy.databinding.FragmentCatalogRvItemBinding

class CatalogAdapter : RecyclerView.Adapter<CatalogAdapter.ItemViewHolder>() {

    var catalogItemList = emptyList<CatalogItem>()

    class ItemViewHolder(private val binding: FragmentCatalogRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

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

}