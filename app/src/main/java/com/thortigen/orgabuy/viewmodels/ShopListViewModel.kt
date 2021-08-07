package com.thortigen.orgabuy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thortigen.orgabuy.data.AppDatabase
import com.thortigen.orgabuy.data.models.ShopListItem
import com.thortigen.orgabuy.repository.ShopListRepository

class ShopListViewModel(application: Application) : AndroidViewModel(application){

    private val shopListDao = AppDatabase.getDatabase(application).shopListDao()
    private val shopListRepository = ShopListRepository(shopListDao)

    val isShopListEmpty: MutableLiveData<Boolean> = MutableLiveData(false)
    val getAllItems : LiveData<List<ShopListItem>> = shopListRepository.getAllItems

    fun checkForShopListIsEmpty(shopListItemsList: List<ShopListItem>) {
        isShopListEmpty.value = shopListItemsList.isEmpty()
    }

}