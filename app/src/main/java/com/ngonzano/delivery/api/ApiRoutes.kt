package com.ngonzano.delivery.api

import com.ngonzano.delivery.routes.UsersRoutes

class ApiRoutes {
    val API_URL = "http://192.168.3.105:3000/api/"
    val retrofit = RetrofitClient()

    fun getUserRoutes(): UsersRoutes{
        return retrofit.getClient(API_URL).create(UsersRoutes::class.java)
    }
}