package com.thortigen.orgabuy.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.thortigen.orgabuy.data.dao.ShopListDao
import com.thortigen.orgabuy.data.models.ShopListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShopListRepository(private val shopListDao: ShopListDao) {

    val getAllItems: LiveData<List<ShopListItem>> = shopListDao.getAllItems()

    suspend fun insertItem(shopListItem: ShopListItem) {
        shopListDao.insertItem(shopListItem)
    }

    suspend fun deleteItem(shopListItem: ShopListItem) {
        shopListDao.deleteItem(shopListItem)
    }

    suspend fun deleteItemById(id: Int) {
        shopListDao.deleteItemById(id)
    }

    suspend fun editItem(shopListItem: ShopListItem) {
        shopListDao.editItem(shopListItem)
    }

    fun checkForItemIsInList(itemId: Int): ShopListItem{
        return shopListDao.checkForItemIsInList(itemId)
    }

    suspend fun removeAllItemsFromCart() {
        for (item in shopListDao.getAllItemsIsInCart()){
            item.isInCart = 0
            editItem(item)
        }
    }

    suspend fun deleteAllItems() {
        shopListDao.deleteAllItems()
    }

    fun getItemById(itemId: Int) : ShopListItem{
        return shopListDao.getItemById(itemId)
    }

}