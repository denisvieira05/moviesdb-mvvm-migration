package com.example.comics.data.mappers

import com.example.comics.data.remote.comics.ComicListResponse
import com.example.comics.domain.model.Comic

class MovieMapper {
    fun mapResponseList(response: ComicListResponse): List<Comic> {
        return response.results
    }
}

