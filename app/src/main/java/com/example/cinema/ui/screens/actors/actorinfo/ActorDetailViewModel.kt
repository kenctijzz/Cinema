package com.example.cinema.ui.screens.actors.actorinfo

import android.R.attr.data
import android.util.Log.e
import android.util.Log.i
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.cinema.domain.model.Actor
import com.example.cinema.domain.usecases.actors.GetActorDetailsUseCase
import com.example.cinema.ui.common.BaseViewModel
import com.example.cinema.ui.common.UiState
import com.example.cinema.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.Route
import javax.inject.Inject

@HiltViewModel
class ActorDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getActorDetailsUseCase: GetActorDetailsUseCase
) : BaseViewModel() {
    private val actorId: Int = savedStateHandle.toRoute<Screen.ActorDetail>().id
    private val _state = MutableStateFlow<UiState<Actor>>(UiState.Loading)
    val state: StateFlow<UiState<Actor>> = _state

    override fun load() {
        viewModelScope.launch {

            _state.update {
                UiState.Loading
            }
            getActorDetailsUseCase(actorId).onSuccess { data ->
                _state.update {
                    UiState.Success(data)
                }
            }.onFailure { error ->
                _state.update {
                    UiState.Error("$error")
                }
            }

        }
    }

    init {
        load()
    }
}
