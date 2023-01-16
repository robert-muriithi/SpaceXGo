package dev.robert.spacexgo.features.ships.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.robert.spacexgo.core.utils.Resource
import dev.robert.spacexgo.core.utils.UiEvents
import dev.robert.spacexgo.features.ships.domain.model.Ship
import dev.robert.spacexgo.features.ships.domain.usecase.GetAllShipsUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShipsViewModel @Inject constructor(
    private val useCase: GetAllShipsUseCase
) : ViewModel() {

    private val _eventsFlow = MutableSharedFlow<UiEvents>()
    val eventsFlow = _eventsFlow

    private val _shipState = mutableStateOf(ShipsState())
    val shipState : State<ShipsState> = _shipState

     fun getAllShips() {
        _shipState.value = shipState.value.copy(
            isLoading = true
        )
        viewModelScope.launch{
            useCase().collectLatest {result ->
               when(result){
                   is Resource.Error -> {
                       _eventsFlow.emit(
                           UiEvents.ErrorEvent(
                               message = result.message ?: "Unknown Error Occurred"
                           )
                       )


                       _shipState.value = shipState.value.copy(
                           isLoading = false,
                           error = result.message
                       )
                   }
                   is Resource.Success -> {
                      _shipState.value = shipState.value.copy(
                          isLoading = false,
                          ships = result.data ?: emptyList()
                      )

                       /*_eventsFlow.emit(
                           UiEvents.NavigationEvent(
                               route = FeaturesScreenDestination.route
                           )
                       )*/
                   }
                   else -> {
                       shipState
                   }
               }
            }
        }
    }

    init {
        getAllShips()
    }

}

data class ShipsState(
    val isLoading : Boolean = false,
    val error : String? = null,
    val ships: List<Ship> = emptyList()
)