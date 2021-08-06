package com.thortigen.orgabuy.repository

import androidx.lifecycle.LiveData
import com.thortigen.orgabuy.data.dao.ShopListDao
import com.thortigen.orgabuy.data.models.ShopListItem

class ShopListRepository(private val shopListDao: ShopListDao) {

    val getAllItems: LiveData<List<ShopListItem>> = shopListDao.getAllItems()

    suspend fun insertItem(shopListItem: ShopListItem) {
        shopListDao.insertItem(shopListItem)
    }

}