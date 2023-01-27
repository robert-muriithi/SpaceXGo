package dev.robert.spacexgo.features.launches.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.robert.spacexgo.core.utils.Resource
import dev.robert.spacexgo.features.launches.domain.model.Launches
import dev.robert.spacexgo.features.launches.domain.usecase.GetAllLaunchesUseCase
import dev.robert.spacexgo.features.launches.domain.usecase.GetPastLaunchesUseCase
import dev.robert.spacexgo.features.launches.domain.usecase.GetSingleLaunchUseCase
import dev.robert.spacexgo.features.launches.domain.usecase.GetUpcomingLaunchesUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchesViewModel @Inject constructor(
    private val getAllLaunchesUseCase: GetAllLaunchesUseCase,
    private val getPastLaunchesUseCase: GetPastLaunchesUseCase,
    private val getUpcomingLaunchesUseCase: GetUpcomingLaunchesUseCase
) : ViewModel() {

    private val _launchState = mutableStateOf(LaunchesState())
    val launchState = _launchState as State<LaunchesState>

    fun getAllLaunches(){
        _launchState.value = launchState.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
            getAllLaunchesUseCase().collectLatest { result ->
                when(result){
                    is Resource.Error -> {
                        _launchState.value = launchState.value.copy(
                            isLoading = false,
                            message = result.message
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

    fun getPastLaunches(){
        _launchState.value = launchState.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
            getPastLaunchesUseCase().collectLatest { result ->
                when(result){
                    is Resource.Error -> {
                        _launchState.value = launchState.value.copy(
                            isLoading = false,
                            message = result.message
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

    fun getUpcomingLaunches(){
        _launchState.value = launchState.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
            getUpcomingLaunchesUseCase().collectLatest { result ->
                when(result){
                    is Resource.Error -> {
                        _launchState.value = launchState.value.copy(
                            isLoading = false,
                            message = result.message
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

    init {
        getAllLaunches()
    }
}

data class LaunchesState(
    val isLoading: Boolean = false,
    val launches: List<Launches> = emptyList(),
    val message: String? = null,
)