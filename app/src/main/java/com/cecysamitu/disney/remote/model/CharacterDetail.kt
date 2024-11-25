package com.cecysamitu.disney.remote.model

import com.google.gson.annotations.SerializedName

data class CharacterDetail(
    @SerializedName("_id")
    var id: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("films")
    var films: List<String>,
    @SerializedName("imageUrl")
    var imageUrl: String?
)
