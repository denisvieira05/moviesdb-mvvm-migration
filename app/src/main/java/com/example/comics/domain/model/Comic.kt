package com.example.comics.domain.model

data class Comic(
    val title: String,
    val description: String?,
    val thumbnail: ComicThumbnail
)