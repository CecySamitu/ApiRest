package com.cecysamitu.disney.remote.model

data class Info (
    val count: Int,
    val totalPages: Int,
    val previousPage: String?,
    val nextPage: String?
)