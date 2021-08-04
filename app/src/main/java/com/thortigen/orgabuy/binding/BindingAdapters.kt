package com.thortigen.orgabuy.binding

import android.app.Notification
import androidx.databinding.BindingAdapter
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