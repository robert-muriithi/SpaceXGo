package dev.robert.spacexgo.features.capsules.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.robert.spacexgo.core.utils.Resource
import dev.robert.spacexgo.core.utils.UiEvents
import dev.robert.spacexgo.features.capsules.domain.model.Capsule
import dev.robert.spacexgo.features.capsules.domain.usecase.GetAllCapsulesUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CapsulesViewModel @Inject constructor(
    private val useCase: GetAllCapsulesUseCase
) : ViewModel() {

    private val _eventsFlow = MutableSharedFlow<UiEvents>()
    val eventsFlow = _eventsFlow


    private val _capsuleState = mutableStateOf(CapsulesState())
    val capsuleState = _capsuleState as State<CapsulesState>

    private fun getAllCapsules() {
        _capsuleState.value = capsuleState.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
            useCase().collectLatest { result ->
                when(result){
                    is Resource.Error -> {
                        _capsuleState.value = capsuleState.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                        _eventsFlow.emit(
                            UiEvents.ErrorEvent(
                                message = result.message ?: "Unknown Error Occurred"
                            )
                        )
                    }

                    is Resource.Success -> {
                        _capsuleState.value = capsuleState.value.copy(
                            isLoading = false,
                            data = result.data ?: emptyList()
                        )


                    }else -> {
                       capsuleState
                    }
                }
            }
        }
    }
    init {
        getAllCapsules()
    }
}

data class CapsulesState(
    val isLoading : Boolean = false,
    val error : String? = null,
    val data: List<Capsule> = emptyList()
)