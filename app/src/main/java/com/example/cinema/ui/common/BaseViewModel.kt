package com.example.cinema.ui.common

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    abstract fun load()
}