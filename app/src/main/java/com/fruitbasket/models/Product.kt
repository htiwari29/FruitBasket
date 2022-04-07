package com.fruitbasket.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val user_id: String = "zDt0Cp9UkNfBT6pnxmol1NC5jai2",
    val user_name: String = "Himanshu Tiwari",
    val title: String = "",
    val price: String = "",
    val description: String = "",
    val stock_quantity: String = "",
    val image: String = "",
    var product_id: String = "",
) : Parcelable