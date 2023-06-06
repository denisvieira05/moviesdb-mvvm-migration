package com.example.comics.ui.main


sealed interface MainUIState {
    data class Success(val data: List<ComicVO>) : MainUIState
    data class Error(val exception: Throwable) : MainUIState
    object Loading : MainUIState
}