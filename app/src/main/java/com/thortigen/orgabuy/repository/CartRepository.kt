package com.thortigen.orgabuy.repository

import androidx.lifecycle.LiveData
import com.thortigen.orgabuy.data.dao.CartDao
import com.thortigen.orgabuy.data.models.CartItem

class CartRepository(private val cartDao: CartDao) {

    val getAllItems: LiveData<List<CartItem>> = cartDao.getAllItems()
    val getTotalCartCost: LiveData<Double> = cartDao.getTotalCartCost()

    suspend fun insertItem(cartItem: CartItem) {
        cartDao.insertItem(cartItem)
    }

    suspend fun deleteItem(cartItem: CartItem) {
        cartDao.deleteItem(cartItem)
    }

    suspend fun deleteAllItems() {
        cartDao.deleteAllItems()
    }

    fun deleteItemById(id: Int) {
        cartDao.deleteItemById(id)
    }

   fun getItemById(id: Int): CartItem {
        return cartDao.getItemById(id)
    }

    suspend fun editItem(cartItem: CartItem) {
        cartDao.editItem(cartItem)
    }



}