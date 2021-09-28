package com.thortigen.orgabuy.repository

import androidx.lifecycle.LiveData
import com.thortigen.orgabuy.data.dao.CatalogDao
import com.thortigen.orgabuy.data.models.CatalogItem

class CatalogRepository(private val catalogDao: CatalogDao) {

    val getAllItems: LiveData<List<CatalogItem>> = catalogDao.getAllItems()

    fun insertItem(catalogItem: CatalogItem) {
        catalogDao.insertItem(catalogItem)
    }

    fun getItemByName(name: String): CatalogItem? {
        return catalogDao.getItemByName(name)
    }

    fun searchDatabase(query: String): LiveData<List<CatalogItem>> {
        return catalogDao.searchDatabase(query)
    }

    suspend fun editItem(item: CatalogItem) {
        catalogDao.updateItem(item)
    }

    suspend fun deleteItem(catalogItem: CatalogItem) {
        catalogDao.deleteItem(catalogItem)
    }


}