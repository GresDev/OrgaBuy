package com.thortigen.orgabuy.binding

import android.app.Notification
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.thortigen.orgabuy.R

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