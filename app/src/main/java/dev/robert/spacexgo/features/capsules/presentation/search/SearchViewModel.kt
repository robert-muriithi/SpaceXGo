package dev.robert.spacexgo.features.capsules.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.robert.spacexgo.core.utils.Resource
import dev.robert.spacexgo.core.utils.UiEvents
import dev.robert.spacexgo.features.capsules.domain.model.Capsule
import dev.robert.spacexgo.features.capsules.domain.usecase.SearchCapsuleUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CapsuleSearchViewModel @Inject constructor(
    private val searchCapsuleUseCase: SearchCapsuleUseCase
) : ViewModel(){

    private val _query = mutableStateOf("")
    val query = _query as State<String>

    private val _eventsFlow = MutableSharedFlow<UiEvents>()
    val eventsFlow = _eventsFlow

    private val _capsuleState = mutableStateOf(CapsuleSearchState())
    val capsuleState : State<CapsuleSearchState> = _capsuleState


    fun onQueryChanged(query: String){
        _query.value = query
    }


    fun onSearchClicked(query: String){
        if(query.isEmpty()){
            _eventsFlow.tryEmit(UiEvents.ErrorEvent(message = "Please enter a valid query"))
        }
        _capsuleState.value = capsuleState.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
            searchCapsuleUseCase(query).collectLatest { result ->
                when(result){
                    is Resource.Error -> {
                        _eventsFlow.emit(
                            UiEvents.ErrorEvent(
                                message = result.message ?: "Unknown Error Occurred"
                            )
                        )
                        _capsuleState.value = capsuleState.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                    is Resource.Success -> {
                        _capsuleState.value = capsuleState.value.copy(
                            isLoading = false,
                            capsules = result.data ?: emptyList()
                        )
                    }
                    else -> {
                       capsuleState
                    }
                }
            }
        }
    }
}

data class CapsuleSearchState(
    val isLoading: Boolean = false,
    val capsules: List<Capsule> = emptyList(),
    val error: String? = null
)

