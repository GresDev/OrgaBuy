package com.thortigen.orgabuy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class CartItemPriceViewModel(application: Application) : AndroidViewModel(application) {

    val itemCost = MutableLiveData<String>("0")

    fun getItemCost(itemPrice: Double, itemQuantity: Double) {

        itemCost.value = String.format("%.2f", itemPrice * itemQuantity).replace(".", " руб. ") + " коп."

    }

}