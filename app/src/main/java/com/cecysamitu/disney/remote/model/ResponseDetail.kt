package com.cecysamitu.disney.remote.model

import com.google.gson.annotations.SerializedName

data class ResponseDetail(
    @SerializedName("info")
    val info: Info,
    @SerializedName("data")
    val data: CharacterDetail
)
