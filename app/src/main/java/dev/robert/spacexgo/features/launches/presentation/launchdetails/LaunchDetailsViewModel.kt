package dev.robert.spacexgo.features.launches.presentation.launchdetails

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.robert.spacexgo.core.utils.Resource
import dev.robert.spacexgo.features.rockets.domain.model.Rocket
import dev.robert.spacexgo.features.rockets.domain.usecase.GetSingleRocketUseCase
import dev.robert.spacexgo.features.ships.domain.model.Ship
import dev.robert.spacexgo.features.ships.domain.usecase.GetShipUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchDetailsViewModel @Inject constructor(
    private val getSingleRocketUseCase: GetSingleRocketUseCase,
    private val getSingleShipUseCase: GetShipUseCase
): ViewModel() {

    private val _rocketDetails = mutableStateOf(LaunchDetailsState())
    val rocketDetails : State<LaunchDetailsState> = _rocketDetails

    private val _shipDetails = mutableStateOf(ShipDetailsState())
    val shipDetails : State<ShipDetailsState> = _shipDetails

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

    fun getShipDetails(id: String){
        _shipDetails.value = shipDetails.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
            getSingleShipUseCase(id).collectLatest { result ->
                when(result){
                    is Resource.Error -> {
                        _shipDetails.value = shipDetails.value.copy(
                            error = result.message
                        )
                    }
                    is Resource.Success -> {
                        _shipDetails.value = shipDetails.value.copy(
                            ship = result.data
                        )
                    }
                    else -> {
                        shipDetails
                    }
                }
            }
        }
    }

}


/**
 * States for the [LaunchDetailsViewModel]
 */

data class LaunchDetailsState(
    val isLoading: Boolean = false,
    val error : String? = null,
    val rocket: Rocket? = null
)

data class ShipDetailsState(
    val isLoading: Boolean = false,
    val error : String? = null,
    val ship: Ship? = null
)