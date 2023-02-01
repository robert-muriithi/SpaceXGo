package dev.robert.spacexgo.features.launches.presentation.launchdetails

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.robert.spacexgo.core.utils.Resource
import dev.robert.spacexgo.features.rockets.domain.model.Rocket
import dev.robert.spacexgo.features.rockets.domain.usecase.GetSingleRocketUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchDetailsViewModel @Inject constructor(
    private val getSingleRocketUseCase: GetSingleRocketUseCase
): ViewModel() {

    private val _rocketDetails = mutableStateOf(LaunchDetailsState())
    val rocketDetails : State<LaunchDetailsState> = _rocketDetails

    fun getRocketDetails(id: String){
        _rocketDetails.value = rocketDetails.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
            getSingleRocketUseCase(id).collectLatest { result ->
                when(result){
                    is Resource.Error -> {
                        _rocketDetails.value = rocketDetails.value.copy(
                            error = result.message
                        )
                    }
                    is Resource.Success -> {
                        _rocketDetails.value = rocketDetails.value.copy(
                            rocket = result.data
                        )
                    }
                    else -> {
                        rocketDetails
                    }
                }
            }
        }
    }

}

data class LaunchDetailsState(
    val isLoading: Boolean = false,
    val error : String? = null,
    val rocket: Rocket? = null
)