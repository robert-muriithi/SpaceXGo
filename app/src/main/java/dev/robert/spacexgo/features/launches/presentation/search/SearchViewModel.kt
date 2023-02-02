package dev.robert.spacexgo.features.launches.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.robert.spacexgo.core.utils.Resource
import dev.robert.spacexgo.core.utils.UiEvents
import dev.robert.spacexgo.features.capsules.domain.model.Capsule
import dev.robert.spacexgo.features.capsules.domain.usecase.SearchCapsuleUseCase
import dev.robert.spacexgo.features.capsules.presentation.search.CapsuleSearchState
import dev.robert.spacexgo.features.launches.domain.model.Launches
import dev.robert.spacexgo.features.launches.domain.usecase.SearchLaunchesUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchesSearchViewModel @Inject constructor(
    private val searchLaunchesUseCase: SearchLaunchesUseCase
) : ViewModel(){

    private val _query = mutableStateOf("")
    val query = _query as State<String>

    private val _eventsFlow = MutableSharedFlow<UiEvents>()
    val eventsFlow = _eventsFlow

    private val _launchState = mutableStateOf(LaunchesSearchState())
    val launchState : State<LaunchesSearchState> = _launchState


    fun onQueryChanged(query: String){
        _query.value = query
    }


    fun onSearchClicked(query: String){
        if(query.isEmpty()){
            _eventsFlow.tryEmit(UiEvents.ErrorEvent(message = "Please enter a valid query"))
        }
        _launchState.value = launchState.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
            searchLaunchesUseCase(query).collectLatest { result ->
                when(result){
                    is Resource.Error -> {
                        _eventsFlow.emit(
                            UiEvents.ErrorEvent(
                                message = result.message ?: "Unknown Error Occurred"
                            )
                        )
                        _launchState.value = launchState.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                    is Resource.Success -> {
                        _launchState.value = launchState.value.copy(
                            isLoading = false,
                            launches = result.data ?: emptyList()
                        )
                    }
                    else -> {
                        launchState
                    }
                }
            }
        }
    }
}

data class LaunchesSearchState(
    val isLoading: Boolean = false,
    val launches: List<Launches> = emptyList(),
    val error: String? = null
)

