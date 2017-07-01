package com.bragi.dash.sdk.rawmotion

import android.os.Parcel
import android.os.Parcelable

inline fun <reified T : Parcelable> createParcel(
        crossinline createFromParcel: (Parcel) -> T?): Parcelable.Creator<T> =
        object : Parcelable.Creator<T> {
            override fun createFromParcel(source: Parcel): T? = createFromParcel(source)
            override fun newArray(size: Int): Array<out T?> = arrayOfNulls(size)
        }

inline fun Parcel.writeByteArrayWithLength(byteArray: ByteArray) {
    writeInt(byteArray.size)
    writeByteArray(byteArray)
}

inline fun Parcel.readByteArrayWithLength(): ByteArray {
    val byteArrayResult = ByteArray(readInt())
    readByteArray(byteArrayResult)
    return byteArrayResult
}

