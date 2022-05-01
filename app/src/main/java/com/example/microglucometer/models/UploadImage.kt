package com.example.microglucometer.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UploadImage(
    val originalImageByteArray: ByteArray,
    val regionOfInterestImageByteArray: ByteArray,
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UploadImage

        if (!originalImageByteArray.contentEquals(other.originalImageByteArray)) return false
        if (!regionOfInterestImageByteArray.contentEquals(other.regionOfInterestImageByteArray)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = originalImageByteArray.contentHashCode()
        result = 31 * result + regionOfInterestImageByteArray.contentHashCode()
        return result
    }

}