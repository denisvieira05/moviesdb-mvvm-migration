package com.example.comics.data.remote

import com.example.comics.data.remote.comics.ComicsAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {

    private const val BASE_URL = "https://gateway.marvel.com/v1/public/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getMoviesAPI(): ComicsAPI = retrofit.create(ComicsAPI::class.java)
}