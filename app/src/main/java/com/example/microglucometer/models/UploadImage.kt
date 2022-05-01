package com.example.microglucometer.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UploadImage(
    val byteArray: ByteArray,
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UploadImage

        if (!byteArray.contentEquals(other.byteArray)) return false

        return true
    }

    override fun hashCode(): Int {
        return byteArray.contentHashCode()
    }
}