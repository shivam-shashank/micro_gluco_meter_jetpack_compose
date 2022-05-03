package com.example.microglucometer.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String,
    val age: String,
    val gender: String,
    val phoneNumber: String,
    var concentration: String,
) : Parcelable