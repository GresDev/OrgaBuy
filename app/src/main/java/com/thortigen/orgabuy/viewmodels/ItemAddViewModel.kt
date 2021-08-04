package com.thortigen.orgabuy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.thortigen.orgabuy.data.AppDatabase
import com.thortigen.orgabuy.data.models.CatalogItem
import com.thortigen.orgabuy.repository.CatalogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemAddViewModel(application: Application) : AndroidViewModel(application) {

    private val catalogDao = AppDatabase.getDatabase(application).catalogDao()
    private val catalogRepository: CatalogRepository = CatalogRepository(catalogDao)

    fun insertItem(catalogItem: CatalogItem) {
        viewModelScope.launch(Dispatchers.IO) {
            catalogRepository.insertItem(catalogItem)
        }
    }

    fun getItemByName(name: String) : CatalogItem{
        return catalogRepository.getItemByName(name)
    }

}