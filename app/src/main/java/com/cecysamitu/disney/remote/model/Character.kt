package com.cecysamitu.disney.remote.model

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("_id")
    var id: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("createdAt")
    var cratedAt: String,
    @SerializedName("imageUrl")
    var imageUrl: String?
)
