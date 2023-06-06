package com.example.comics.data.remote.comics

import retrofit2.http.GET
import retrofit2.http.Query

interface ComicsAPI {

    @GET("comics")
    suspend fun getComics(
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String
    ) : ComicListResponseContainer
}