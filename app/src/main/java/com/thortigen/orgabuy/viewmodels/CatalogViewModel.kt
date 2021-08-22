package com.thortigen.orgabuy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.thortigen.orgabuy.data.AppDatabase
import com.thortigen.orgabuy.data.models.CatalogItem
import com.thortigen.orgabuy.repository.CatalogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CatalogViewModel(application: Application) : AndroidViewModel(application) {
    private val catalogDao = AppDatabase.getDatabase(application).catalogDao()
    private val catalogRepository : CatalogRepository = CatalogRepository(catalogDao)

    val isCatalogEmpty : MutableLiveData<Boolean> = MutableLiveData(false)

    val getAllItems : LiveData<List<CatalogItem>> = catalogRepository.getAllItems

    fun checkForCatalogIsEmpty(itemList: List<CatalogItem>) {
        isCatalogEmpty.value = itemList.isEmpty()
    }

    fun searchDatabase(query: String) : LiveData<List<CatalogItem>> {
        return catalogRepository.searchDatabase(query)
    }

    fun getItemByName(itemName: String): CatalogItem? {
        return catalogRepository.getItemByName(itemName)
    }

    fun editItem(item:CatalogItem) {
        viewModelScope.launch(Dispatchers.IO) {
            catalogRepository.editItem(item)
        }
    }


}