package com.thortigen.orgabuy.fragments.catalog.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.thortigen.orgabuy.data.models.CatalogItem

class CatalogAdapterDiffUtils(
    private val oldList: List<CatalogItem>, private
    val newList: List<CatalogItem>
) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }
}