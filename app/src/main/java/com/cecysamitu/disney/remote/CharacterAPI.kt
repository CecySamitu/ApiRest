package com.cecysamitu.disney.remote

import com.cecysamitu.disney.remote.model.APIResponse
import com.cecysamitu.disney.remote.model.ResponseDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface CharacterAPI {


    @GET
    fun getCharacter(
        @Url url: String?
    ): Call<APIResponse>


    @GET("/character/{id}")
    fun getCharacterDetail(
        @Path("id") id: String?
    ): Call<ResponseDetail>

}