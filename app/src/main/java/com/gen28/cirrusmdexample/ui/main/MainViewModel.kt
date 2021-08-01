package com.gen28.cirrusmdexample.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cirrusmd.androidsdk.CirrusMD
import com.gen28.cirrusmdexample.BASE_URL
import com.gen28.cirrusmdexample.PATIENT_ID
import com.gen28.cirrusmdexample.SDK_ID
import com.gen28.cirrusmdexample.datasource.TokenDataSource
import com.gen28.cirrusmdexample.entity.Channel
import com.gen28.cirrusmdexample.entity.Token
import com.gen28.cirrusmdexample.model.TokenRequestModel
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class MainViewModel : ViewModel() {
    private val _channels = MutableLiveData<List<Channel>>().apply {
        this.value = CirrusMD.channels().map { item -> Channel(id = item.first, name = item.second) }
    }
    val channels: LiveData<List<Channel>> = _channels

    fun fetchToken() {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl(BASE_URL)
            .build()
        val tokenApi = retrofit.create(TokenDataSource::class.java)
        val request = TokenRequestModel(SDK_ID, PATIENT_ID)

        tokenApi.getSessionJwt(request)?.enqueue(object : Callback<Token> {
            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                response.body()?.token?.let { token ->
                    CirrusMD.setSessionToken(token)
                } ?: Timber.e("No token in response body")
            }

            override fun onFailure(call: Call<Token>, t: Throwable) {
                Timber.e(t)
            }
        })
    }
}