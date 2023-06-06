package com.example.comics.data.remote.comics

import com.example.comics.domain.model.Comic

data class ComicListResponse(
    val results: List<Comic>
)