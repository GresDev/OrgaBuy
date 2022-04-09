package com.thortigen.orgabuy.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.thortigen.orgabuy.data.models.ShopListItem

@Dao
interface ShopListDao {

    @Query("SELECT * FROM shoplist_table ORDER BY isInCart ASC")
    fun getAllItems() : LiveData<List<ShopListItem>>

    @Query("SELECT * FROM shoplist_table WHERE isInCart == 1")
    fun getAllItemsIsInCart(): List<ShopListItem>

    @Query("SELECT COUNT(*) FROM shoplist_table")
    fun getItemsNum(): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(shopListItem: ShopListItem)

    @Delete
    suspend fun deleteItem(shopListItem: ShopListItem)

    @Query("DELETE FROM shoplist_table WHERE id == :id")
    suspend fun deleteItemById(id: Int)

    @Query("DELETE FROM shoplist_table")
    suspend fun deleteAllItems()

    @Update
    suspend fun editItem(shopListItem: ShopListItem)

    @Query("SELECT * FROM shoplist_table WHERE id == :itemId")
    fun checkForItemIsInList(itemId: Int): ShopListItem

    @Query("SELECT * FROM shoplist_table WHERE id == :itemId")
    fun getItemById(itemId: Int): ShopListItem

}