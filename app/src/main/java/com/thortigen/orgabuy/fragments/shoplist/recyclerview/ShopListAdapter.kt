package com.thortigen.orgabuy.fragments.shoplist.recyclerview

import android.app.Application
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thortigen.orgabuy.R
import com.thortigen.orgabuy.data.models.CartItem
import com.thortigen.orgabuy.data.models.ShopListItem
import com.thortigen.orgabuy.databinding.FragmentShoplistRvItemBinding
import com.thortigen.orgabuy.viewmodels.CartViewModel
import com.thortigen.orgabuy.viewmodels.ShopListViewModel


class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ItemViewHolder>() {

    var shopListItemList = emptyList<ShopListItem>()

    private val mCartViewModel = CartViewModel(application = Application())
    private val mShopListViewModel = ShopListViewModel(application = Application())

    class ItemViewHolder(private val binding: FragmentShoplistRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val addToCartButton: ImageButton = binding.rvIbAddToCart
        val itemIsInCartButton: ImageButton = binding.rvIbIsInCart
        val textView: TextView = binding.rvTvItemName

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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShopListAdapter.ItemViewHolder {
        return ShopListAdapter.ItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ShopListAdapter.ItemViewHolder, position: Int) {

        val currentShopListItem = shopListItemList[position]
        val cartItem = CartItem(
            currentShopListItem.id,
            currentShopListItem.name,
            currentShopListItem.description,
            0.0,
            0.0
        )

        holder.addToCartButton.setOnClickListener {
            mCartViewModel.insertItem(cartItem)
            currentShopListItem.isInCart = 1
            mShopListViewModel.editItem(currentShopListItem)
        }

        if (currentShopListItem.isInCart == 1) {
            holder.addToCartButton.visibility = View.GONE
            holder.itemIsInCartButton.visibility = View.VISIBLE
            holder.textView.setTextColor(
                holder.itemView.getResources().getColorStateList(R.color.textInactiveColor)
            )
            holder.textView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.itemIsInCartButton.visibility = View.INVISIBLE
            holder.addToCartButton.visibility = View.VISIBLE
            holder.textView.setTextColor(
                holder.itemView.getResources().getColorStateList(R.color.textMainColor)
            )
            holder.textView.paintFlags = 0

        }

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