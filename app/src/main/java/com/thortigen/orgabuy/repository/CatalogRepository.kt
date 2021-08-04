package com.thortigen.orgabuy.repository

import androidx.lifecycle.LiveData
import com.thortigen.orgabuy.data.dao.CatalogDao
import com.thortigen.orgabuy.data.models.CatalogItem

class CatalogRepository(private val catalogDao: CatalogDao) {

    suspend fun insertItem(catalogItem: CatalogItem) {
        catalogDao.insertItem(catalogItem)
    }

    fun getItemByName(name: String): CatalogItem {
        return catalogDao.getItemByName(name)
    }

}