package com.thortigen.orgabuy.fragments.cart.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thortigen.orgabuy.data.models.CartItem
import com.thortigen.orgabuy.databinding.FragmentCartRvItemBinding

class CartAdapter : RecyclerView.Adapter<CartAdapter.ItemViewHolder>() {

    var cartItemList = emptyList<CartItem>()

    class ItemViewHolder(private val binding: FragmentCartRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(cartItem: CartItem) {
                binding.cartItem = cartItem
                binding.executePendingBindings()
            }

        companion object {
            fun from(parent: ViewGroup) : ItemViewHolder{

                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentCartRvItemBinding.inflate(layoutInflater, parent, false)
                return ItemViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return CartAdapter.ItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentCartItem = cartItemList[position]
        holder.bind(currentCartItem)
    }

    override fun getItemCount(): Int {
        return cartItemList.size
    }

    fun setData(cartItems : List<CartItem>) {
        val cartAdapterDiffUtils = CartAdapterDiffUtils(cartItemList, cartItems)
        val cartAdapterDiffResult = DiffUtil.calculateDiff(cartAdapterDiffUtils)
        cartItemList = cartItems
        cartAdapterDiffResult.dispatchUpdatesTo(this)
    }

}