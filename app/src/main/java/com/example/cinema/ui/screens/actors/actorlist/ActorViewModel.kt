package com.example.cinema.ui.screens.actors.actorlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.cinema.core.ui.UiEvent
import com.example.cinema.domain.model.Actor
import com.example.cinema.domain.model.Film
import com.example.cinema.domain.usecases.actors.GetPopularActorsUseCase
import com.example.cinema.domain.usecases.actors.ToggleActorLikeUseCase
import com.example.cinema.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.cache
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorViewModel @Inject constructor(
    private val getPopularActorsUseCase: GetPopularActorsUseCase,
    private val toggleActorLikeUseCase: ToggleActorLikeUseCase
) : BaseViewModel() {
    private val _snackBarEvent = MutableSharedFlow<UiEvent.ShowSnackBar>(
        replay = 0,
        extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val snackBarEvent: MutableSharedFlow<UiEvent.ShowSnackBar> = _snackBarEvent

    suspend fun showSnackBar(message: String) {
        _snackBarEvent.emit(UiEvent.ShowSnackBar(message))
    }
    val popularActorsList: Flow<PagingData<Actor>> =
        getPopularActorsUseCase().cachedIn(viewModelScope)

    fun toggleActorLike(actor: Actor) {
        viewModelScope.launch {
            val favoriteStatus = toggleActorLikeUseCase(actor)
            if (favoriteStatus) {
                showSnackBar("${actor.name} добавлен в избранное")
            } else {
                showSnackBar("${actor.name} удален из избранного")
            }
        }
    }

    override fun load() {

    }
}