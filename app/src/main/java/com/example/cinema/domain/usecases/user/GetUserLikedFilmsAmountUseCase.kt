package com.example.cinema.domain.usecases.user

import com.example.cinema.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserLikedFilmsAmountUseCase @Inject constructor(
    private val repository: FilmRepository
){
    operator fun invoke(): Flow<Int> {
        return repository.getLikedFilmsAmount()
    }
}