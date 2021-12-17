package com.thortigen.orgabuy.fragments.cart.recyclerview

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thortigen.orgabuy.binding.cartListIsEmpty
import com.thortigen.orgabuy.data.models.CartItem
import com.thortigen.orgabuy.data.models.ShopListItem
import com.thortigen.orgabuy.databinding.FragmentCartRvItemBinding
import com.thortigen.orgabuy.viewmodels.CartViewModel
import com.thortigen.orgabuy.viewmodels.ShopListViewModel

class CartAdapter : RecyclerView.Adapter<CartAdapter.ItemViewHolder>() {

    var cartItemList = emptyList<CartItem>()

    private val mCartViewModel = CartViewModel(application = Application())
    private val mShopListViewModel = ShopListViewModel(application = Application())

    class ItemViewHolder(private val binding: FragmentCartRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val imageButton: ImageButton = binding.rvIbRemoveFromCart

        fun bind(cartItem: CartItem) {
            binding.cartItem = cartItem
            binding.cartItemCost = String.format("%.2f", cartItem.price * cartItem.quantity)

            if (cartItem.quantity - cartItem.quantity.toInt() == 0.0) {

                binding.cartItemCostDetailed =
                    " = " + String.format("%.2f", cartItem.price) + "x" + String.format(
                        "%.0f",
                        cartItem.quantity
                    )
            }
            else {

                binding.cartItemCostDetailed =
                    " = " + String.format("%.2f", cartItem.price) + "x" + String.format(
                        "%.3f",
                        cartItem.quantity
                    )
            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ItemViewHolder {

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

        holder.imageButton.setOnClickListener {
            mCartViewModel.deleteItem(currentCartItem)
            val shopListItem = ShopListItem(
                currentCartItem.id,
                currentCartItem.name,
                currentCartItem.description,
                0
            )
            mShopListViewModel.editItem(shopListItem)
        }


        holder.bind(currentCartItem)
    }

    override fun getItemCount(): Int {
        return cartItemList.size
    }

    fun setData(cartItems: List<CartItem>) {
        val cartAdapterDiffUtils = CartAdapterDiffUtils(cartItemList, cartItems)
        val cartAdapterDiffResult = DiffUtil.calculateDiff(cartAdapterDiffUtils)
        cartItemList = cartItems
        cartAdapterDiffResult.dispatchUpdatesTo(this)
    }

}