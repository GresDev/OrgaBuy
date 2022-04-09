package com.thortigen.orgabuy.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.thortigen.orgabuy.data.AppDatabase
import com.thortigen.orgabuy.data.models.CatalogItem
import com.thortigen.orgabuy.repository.CatalogRepository
import com.thortigen.orgabuy.utils.ItemFullName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemAddViewModel(application: Application) : AndroidViewModel(application) {

    private val catalogDao = AppDatabase.getDatabase(application).catalogDao()
    private val catalogRepository: CatalogRepository = CatalogRepository(catalogDao)
    private lateinit var items: List<CatalogItem>


    fun insertItem(catalogItem: CatalogItem) {
        catalogRepository.insertItem(catalogItem)
    }

    fun getItemByName(name: String): CatalogItem? {
        return catalogRepository.getItemByName(name)
    }

    fun getFullNameItems(): List<ItemFullName> {

        items = catalogRepository.getAllItemsList()

        val itemsFullName = mutableListOf<ItemFullName>()

        if (items != null) {
            for (item in items) {
                val newItem = ItemFullName(item.name, item.description)
                itemsFullName.add(newItem)
            }
        }

        return itemsFullName
    }


}