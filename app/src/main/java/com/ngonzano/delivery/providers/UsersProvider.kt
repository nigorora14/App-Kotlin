package com.ngonzano.delivery.providers

import com.ngonzano.delivery.api.ApiRoutes
import com.ngonzano.delivery.models.ResponseHttp
import com.ngonzano.delivery.models.User
import com.ngonzano.delivery.routes.UsersRoutes
import retrofit2.Call

class UsersProvider {
    private var usersRoutes: UsersRoutes? = null

    init {
        val api = ApiRoutes()
        usersRoutes = api.getUserRoutes()
    }
    fun register(user: User): Call<ResponseHttp>? {
        return usersRoutes?.register(user)
    }
    fun login(email: String, password: String): Call<ResponseHttp>? {
        return usersRoutes?.login(email, password)
    }
}