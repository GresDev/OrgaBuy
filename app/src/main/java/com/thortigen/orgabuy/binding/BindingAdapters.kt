package com.thortigen.orgabuy.binding

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.thortigen.orgabuy.R
import com.thortigen.orgabuy.data.models.CatalogItem
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
fun shopListIsEmpty(view: View, shopListIsEmpty: MutableLiveData<Boolean>) {
    when (shopListIsEmpty.value) {
        true -> view.visibility = View.VISIBLE
        false -> view.visibility = View.INVISIBLE
    }
}

@BindingAdapter("android:cartListIsEmpty")
fun cartListIsEmpty(view: View, cartIsEmpty: MutableLiveData<Boolean>) {
    when (cartIsEmpty.value) {
        true -> view.visibility = View.VISIBLE
        false -> view.visibility = View.INVISIBLE
    }
}

@BindingAdapter("android:sendItemToViewFragment")
fun sendItemToViewFragment(constraintLayout: ConstraintLayout, currentItem: CatalogItem ){
    constraintLayout.setOnClickListener {
        val action = CatalogFragmentDirections.actionCatalogFragmentToItemViewFragment(currentItem)
        constraintLayout.findNavController().navigate(action)
    }
}