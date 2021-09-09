package com.example.yabichat.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Chat(val memberUID: String, val memberName: String, val lastMsg: String, val timestamp: Long = 0 ): Parcelable
// data class Chat(val chatID: String, val memberUID: String, val lastMsg: String, val timestamp: Long = 0 ): Parcelable
