package com.thortigen.orgabuy.repository

import androidx.lifecycle.LiveData
import com.thortigen.orgabuy.data.dao.CartDao
import com.thortigen.orgabuy.data.models.CartItem

class CartRepository(private val cartDao: CartDao) {

    val getAllItems: LiveData<List<CartItem>> = cartDao.getAllItems()

    suspend fun insertItem(cartItem: CartItem) {
        cartDao.insertItem(cartItem)
    }

    suspend fun deleteItem(cartItem: CartItem) {
        cartDao.deleteItem(cartItem)
    }

}