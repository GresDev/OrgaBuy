package com.thortigen.orgabuy.binding

import android.content.res.Resources
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.internal.ContextUtils.getActivity
import com.thortigen.orgabuy.R
import com.thortigen.orgabuy.data.models.CartItem
import com.thortigen.orgabuy.data.models.CatalogItem
import com.thortigen.orgabuy.data.models.ShopListItem
import com.thortigen.orgabuy.fragments.cart.CartFragmentDirections
import com.thortigen.orgabuy.fragments.catalog.CatalogFragmentDirections

@BindingAdapter("app:navToAddItem")
fun navigateToAddItem(view: FloatingActionButton, navigate: Boolean) {
    view.setOnClickListener {
        if (navigate) {
            view.findNavController().navigate(R.id.action_catalogFragment_to_itemAddFragment)
        }
    }
}

@BindingAdapter("android:catalogIsEmpty")
fun catalogIsEmpty(view: View, itemListIsEmpty: MutableLiveData<Boolean>) {
    when (itemListIsEmpty.value) {
        true -> view.visibility = View.VISIBLE
        false -> view.visibility = View.INVISIBLE
    }
}

@BindingAdapter("android:shopListIsEmpty")
fun shopListIsEmpty(view: View, shopListIsEmpty: LiveData<Boolean>) {
    when (shopListIsEmpty.value) {
        true -> view.visibility = View.VISIBLE
        false -> view.visibility = View.INVISIBLE
    }
}

@BindingAdapter("android:cartListIsEmpty")
fun cartListIsEmpty(view: View, cartIsEmpty: LiveData<Boolean>) {
    when (cartIsEmpty.value) {
        true -> view.visibility = View.VISIBLE
        false -> view.visibility = View.INVISIBLE
    }
}

@BindingAdapter("android:sendItemToViewFragment")
fun sendItemToViewFragment(constraintLayout: ConstraintLayout, currentItem: CatalogItem) {
    constraintLayout.setOnClickListener {
        val action = CatalogFragmentDirections.actionCatalogFragmentToItemViewFragment(currentItem)
        constraintLayout.findNavController().navigate(action)
    }
}

@BindingAdapter("android:sendItemToPriceFragment")
fun sendItemToPriceFragment(constraintLayout: ConstraintLayout, currentItem: CartItem) {
    constraintLayout.setOnClickListener {
        val action =
            CartFragmentDirections.actionCartFragmentToSetItemPriceFragment(currentItem)
        constraintLayout.findNavController().navigate(action)
    }
}


@BindingAdapter("android:setTextColor")
fun setTextColor(view: TextView, currentItem: CartItem) {
    when ((currentItem.quantity == 0.0) or (currentItem.price == 0.0)) {
        true -> view.setTextColor(view.getResources().getColor(R.color.red))
        false -> view.setTextColor(view.getResources().getColor(R.color.colorAccent))
    }
}