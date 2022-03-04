package com.ngonzano.delivery.models

import com.google.gson.annotations.SerializedName

class User (
    //id es de la columna de la bd y Id es la variable que va apuntar a esa columna id
    @SerializedName("id") val Id: String? = null,
    @SerializedName("name") val Name: String,
    @SerializedName("lastname") val Lastname: String,
    @SerializedName("phone") val Phone: String,
    @SerializedName("email") val Email: String,
    @SerializedName("password") val Password: String,
    @SerializedName("image") val Image: String? = null,
    @SerializedName("session_token") val Session_token: String? = null,
    @SerializedName("is_available") val Is_available: Boolean? = null
    ){
    override fun toString(): String {
        return "User(Id=$Id, Name='$Name', Lastname='$Lastname', Phone='$Phone', Email='$Email', Password='$Password', Image=$Image, Session_token=$Session_token, Is_available=$Is_available)"
    }
}