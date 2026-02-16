package com.example.userauth

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

interface AuthApi {
    @POST("api/auth/login") // This matches your @RequestMapping + @PostMapping
    suspend fun login(@Body request: LoginRequest): Response<User>

    @POST("api/auth/register")
    suspend fun register(@Body request: Map<String, String>): Response<Unit>
}

object RetrofitClient {
    private const val BASE_URL = "http://192.168.1.2:8080/"

    val instance: AuthApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }
}