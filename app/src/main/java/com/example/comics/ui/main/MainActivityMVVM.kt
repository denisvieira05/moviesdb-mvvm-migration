package com.example.comics.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.comics.data.mappers.MovieMapper
import com.example.comics.data.remote.NetworkService
import com.example.comics.data.remote.comics.ComicsRepository
import com.example.comics.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivityMVVM : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    private val viewModel by lazy {
        MainViewModel(ComicsRepository(NetworkService.getMoviesAPI(), MovieMapper()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        configureViews()
        setupObservers()
        viewModel.loadComics()
    }

    private fun configureViews() {
        with(binding?.swipeRefresh) {
            this?.setOnRefreshListener {
                viewModel.loadComics()
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is MainUIState.Loading -> onLoadingComics()
                        is MainUIState.Success ->
                            onLoadComicsSuccess(state.data)

                        is MainUIState.Error -> onLoadComicsError()
                        else -> {}
                    }
                }
            }
        }
    }


    private fun onLoadingComics() {
        binding?.swipeRefresh?.isRefreshing = true
    }

    private fun onLoadComicsSuccess(data: List<ComicVO>) {
        with(binding) {
            this?.errorTV?.visibility = View.GONE
            this?.listItem?.visibility = View.VISIBLE
            this?.listItem?.adapter = MvvmAdapter(data)
            this?.listItem?.layoutManager = LinearLayoutManager(this@MainActivityMVVM)
            this?.swipeRefresh?.isRefreshing = false
        }
    }

    private fun onLoadComicsError() {
        with(binding) {
            this?.listItem?.visibility = View.GONE
            this?.errorTV?.visibility = View.VISIBLE
            this?.swipeRefresh?.isRefreshing = false
        }
    }


}