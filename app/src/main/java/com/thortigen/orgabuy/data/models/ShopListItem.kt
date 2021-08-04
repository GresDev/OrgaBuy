package com.thortigen.orgabuy.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shoplist_table")
data class ShopListItem(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    var isInCart: Int,
)