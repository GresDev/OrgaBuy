package com.thortigen.orgabuy.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.thortigen.orgabuy.data.models.CartItem

@Dao
interface CartDao {

    @Query("SELECT * FROM cart_table")
    fun getAllItems(): LiveData<List<CartItem>>

    @Query("SELECT * FROM cart_table WHERE id LIKE :id")
    fun getItemById(id: Int): CartItem

    @Query("SELECT COUNT(*) FROM cart_table")
    fun getItemsNum(): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(cartItem: CartItem)

    @Delete
    suspend fun deleteItem(cartItem: CartItem)

    @Query("DELETE FROM cart_table")
    suspend fun deleteAllItems()

    @Update
    suspend fun editItem(cartItem: CartItem)

    @Query("DELETE FROM cart_table WHERE id LIKE :id")
    fun deleteItemById(id: Int)

    @Query("SELECT TOTAL(price*quantity) FROM (SELECT price, quantity FROM cart_table) ")
    fun getTotalCartCost() : LiveData<Double>

}