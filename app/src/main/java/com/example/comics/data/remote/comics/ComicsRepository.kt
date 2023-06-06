package com.example.comics.data.remote.comics

import com.example.comics.data.mappers.MovieMapper
import com.example.comics.domain.model.Comic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val apiKey = "b7e14bab409c70a5c4e7c2b319c09d7b"
private const val ts =  "1682982412"
private const val hash = "3482f01e9bf207a437a4b621c91339ad"

class ComicsRepository(
    private val comicsAPI: ComicsAPI,
    private val movieMapper: MovieMapper
) {

    fun getMovies(): Flow<List<Comic>> = flow {
        val response = comicsAPI.getComics(
            apikey = apiKey,
            ts = ts,
            hash = hash
        )
        println("dns: response: $response")
        emit(movieMapper.mapResponseList(response.data))
    }

}