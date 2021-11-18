package com.thortigen.orgabuy.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.text.DecimalFormat
import kotlin.math.roundToInt

@Entity(tableName = "cart_table")
@Parcelize
data class CartItem(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    var price: Double = 0.0,
    var quantity: Double = 0.0,
) : Parcelable