package com.ngonzano.delivery.routes

import com.ngonzano.delivery.models.ResponseHttp
import com.ngonzano.delivery.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UsersRoutes {
    @POST("users/create")
    fun register(@Body user: User): Call<ResponseHttp>

    @FormUrlEncoded
    @POST("users/login")
    // el "email" y "password" son los mismos del backend
    fun login(@Field("email") email:String, @Field("password") password:String): Call<ResponseHttp>
}