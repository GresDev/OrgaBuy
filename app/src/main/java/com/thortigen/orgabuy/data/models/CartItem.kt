package com.thortigen.orgabuy.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_table")
data class CartItem(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    var price: Double = 0.0,
    var quantity: Double,
)