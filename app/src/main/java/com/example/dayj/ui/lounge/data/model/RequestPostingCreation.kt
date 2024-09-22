package com.example.dayj.ui.lounge.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestPostingCreation(
    val postTitle: String,
    val postContent: String,
    val postTag: String,
    val isAnonymous: Boolean
): Parcelable