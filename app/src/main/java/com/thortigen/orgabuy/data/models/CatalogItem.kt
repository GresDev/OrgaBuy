package com.thortigen.orgabuy.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "catalog_table", indices = [(Index(value=["name"], unique = true))])
@Parcelize
data class CatalogItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,
) : Parcelable