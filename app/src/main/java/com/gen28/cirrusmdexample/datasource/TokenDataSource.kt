package com.gen28.cirrusmdexample.datasource

import com.gen28.cirrusmdexample.entity.Token
import com.gen28.cirrusmdexample.model.TokenRequestModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface TokenDataSource {

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("sandbox/sessions")
    fun getSessionJwt(@Body request: TokenRequestModel): Call<Token>?
}