package com.example.comics.presenter

import com.example.comics.domain.model.ItemModel

interface IPresenter {

    fun setupList(list: ItemModel)

    fun error()
}