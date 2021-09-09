package com.example.yabichat.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class
User(val uid: String, var name:String = "UserName", var email: String? = null, var phone: String? = null, var contacts: MutableList<String>? = mutableListOf()) : Parcelable
