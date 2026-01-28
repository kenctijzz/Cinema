package com.example.cinema.ui.screens.user.stats

import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.usecases.user.GetAllUserRatedFilmsAmountUseCase
import com.example.cinema.domain.usecases.user.GetAllUserRatingsSumUseCase
import com.example.cinema.domain.usecases.user.GetUserLikedFilmsAmountUseCase
import com.example.cinema.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class UserStatsViewModel @Inject constructor(
    getAllUserRatedFilmsAmountUseCase: GetAllUserRatedFilmsAmountUseCase,
    getAllUserRatingsSumUseCase: GetAllUserRatingsSumUseCase,
    getUserLikedFilmsAmountUseCase: GetUserLikedFilmsAmountUseCase
) : BaseViewModel() {

    val userRatedFilmsAmount: StateFlow<Int> = getAllUserRatedFilmsAmountUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )
    val userRatingsSum: StateFlow<Int> = getAllUserRatingsSumUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )
    val userLikedFilmsAmount: StateFlow<Int> = getUserLikedFilmsAmountUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )
    override fun load() {

    }
}