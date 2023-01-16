package dev.robert.spacexgo.features.rockets.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.robert.spacexgo.core.utils.Resource
import dev.robert.spacexgo.core.utils.UiEvents
import dev.robert.spacexgo.features.rockets.domain.model.Rocket
import dev.robert.spacexgo.features.rockets.domain.usecase.GetAllRocketsUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class RocketsViewModel @Inject constructor(
    private val useCase: GetAllRocketsUseCase
): ViewModel() {
    private val _rocketState = mutableStateOf(RocketsState())
    val rocketsState = _rocketState as State<RocketsState>

    private val _eventsFlow = MutableSharedFlow<UiEvents>()
    val events = _eventsFlow

    fun getAllRockets() {
       _rocketState.value = rocketsState.value.copy(
           isLoading = true
       )
        viewModelScope.launch {
            useCase().collectLatest { result->
                when(result){
                    is Resource.Error -> {
                        _eventsFlow.emit(
                            UiEvents.ErrorEvent(
                                message = result.message ?: "Unknown Error Occurred"
                            )
                        )
                        _rocketState.value = rocketsState.value.copy(
                            error = result.message,
                            isLoading = false
                        )
                    }
                    is Resource.Success -> {
                        _rocketState.value = rocketsState.value.copy(
                            rockets = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    else -> {
                        rocketsState
                    }
                }
            }
        }
    }

    init {
        getAllRockets()
    }
}

data class RocketsState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val rockets : List<Rocket> = emptyList()
)