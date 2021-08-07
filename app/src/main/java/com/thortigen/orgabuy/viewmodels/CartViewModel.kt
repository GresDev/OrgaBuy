package com.thortigen.orgabuy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thortigen.orgabuy.data.AppDatabase
import com.thortigen.orgabuy.data.models.CartItem
import com.thortigen.orgabuy.repository.CartRepository

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val cartDao = AppDatabase.getDatabase(application).cartDao()
    private val cartRepository = CartRepository(cartDao)

    val isCartEmpty: MutableLiveData<Boolean> = MutableLiveData(false)
    val getAllItems: LiveData<List<CartItem>> = cartRepository.getAllItems

    fun checkForCartIsEmpty(cartItemsList: List<CartItem>) {
        isCartEmpty.value = cartItemsList.isEmpty()
    }

}