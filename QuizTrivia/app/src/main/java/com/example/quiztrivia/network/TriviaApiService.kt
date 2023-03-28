package com.example.quiztrivia.network

import com.example.quiztrivia.model.Trivia
import com.example.quiztrivia.model.TriviaResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("api.php?amount=100&type=multiple")
    suspend fun getTrivia(): TriviaResponse
}

object ApiClient{
    private const val BASE_URL = "https://opentdb.com"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}