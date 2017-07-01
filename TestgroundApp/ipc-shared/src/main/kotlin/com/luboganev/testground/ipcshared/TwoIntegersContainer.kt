package com.luboganev.testground.ipcshared

import android.os.Parcel
import android.os.Parcelable
import com.bragi.dash.sdk.rawmotion.createParcel


data class TwoIntegersContainer(val first: Int, val second: Int) : Parcelable {
    companion object {
        @JvmField @Suppress("unused")
        val CREATOR = createParcel(::TwoIntegersContainer)
    }

    protected constructor(parcelIn: Parcel) : this(
            parcelIn.readInt(),
            parcelIn.readInt()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(first)
        dest.writeInt(second)
    }

    override fun describeContents() = 0
}
