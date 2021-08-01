package com.gen28.cirrusmdexample.model

import com.google.gson.annotations.SerializedName

data class TokenRequestModel(
    @SerializedName("sdk_id") var sdkId: String,
    @SerializedName("patient_id") var patientId: String
)