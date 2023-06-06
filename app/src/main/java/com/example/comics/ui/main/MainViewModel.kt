package com.example.comics.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comics.data.remote.comics.ComicsRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModel(
    private val comicsRepository: ComicsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<MainUIState?>(null)
    val uiState: StateFlow<MainUIState?> = _uiState.asStateFlow()

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        _uiState.value = MainUIState.Error(throwable)
        println("dns: error load : $throwable")
    }

    fun loadComics() {
        viewModelScope.launch(errorHandler) {
            println("dns: loadComics")
            _uiState.value = MainUIState.Loading
            comicsRepository.getMovies().mapLatest {
                it.map {
                    ComicVO(
                        image = "${it.thumbnail.path}.${it.thumbnail.extension}",
                        title = it.title,
                        subtitle = it.description ?: "Sem descricao"
                    )
                }
            }.collect { comics ->
                _uiState.value = MainUIState.Success(comics)
            }
        }
    }
}