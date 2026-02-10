package com.erguncoban.cryptoexchangeapp.data.entity

import com.google.gson.annotations.SerializedName

data class ImageUrl(
    @SerializedName("small") val small: String?,
    @SerializedName("large") val large: String?
)