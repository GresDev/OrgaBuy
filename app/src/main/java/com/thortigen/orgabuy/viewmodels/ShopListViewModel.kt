package com.thortigen.orgabuy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.thortigen.orgabuy.data.AppDatabase
import com.thortigen.orgabuy.data.models.ShopListItem
import com.thortigen.orgabuy.repository.ShopListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShopListViewModel(application: Application) : AndroidViewModel(application) {

    private val shopListDao = AppDatabase.getDatabase(application).shopListDao()
    private val shopListRepository = ShopListRepository(shopListDao)

    val isShopListEmpty: MutableLiveData<Boolean> = MutableLiveData(false)
    val getAllItems: LiveData<List<ShopListItem>> = shopListRepository.getAllItems

    fun checkForShopListIsEmpty(shopListItemsList: List<ShopListItem>) {
        isShopListEmpty.value = shopListItemsList.isEmpty()
    }

    fun insertItem(shopListItem: ShopListItem) {
        viewModelScope.launch(Dispatchers.IO) { shopListRepository.insertItem(shopListItem) }
    }

    fun deleteItem(shopListItem: ShopListItem) {
        viewModelScope.launch(Dispatchers.IO) { shopListRepository.deleteItem(shopListItem) }
    }

    fun checkForItemIsInList(itemId: Int): Boolean {
        if (shopListRepository.checkForItemIsInList(itemId) != null) {
            return true
        }
        return false
    }

}