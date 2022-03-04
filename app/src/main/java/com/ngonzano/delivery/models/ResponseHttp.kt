package com.ngonzano.delivery.models

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

class ResponseHttp (
    @SerializedName("message") val Message: String,
    @SerializedName("success") val Success: Boolean,
    @SerializedName("data") val Data: JsonObject,
    @SerializedName("error") val Error: String
    ){
    override fun toString(): String {
        return "ResponseHttp(Message='$Message', Success='$Success', Data=$Data, Error=$Error)"
    }
}