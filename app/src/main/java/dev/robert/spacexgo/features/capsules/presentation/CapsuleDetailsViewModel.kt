package dev.robert.spacexgo.features.capsules.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.robert.spacexgo.core.utils.Resource
import dev.robert.spacexgo.features.launches.domain.model.Launches
import dev.robert.spacexgo.features.launches.domain.usecase.GetSingleLaunchUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CapsuleDetailsViewModel @Inject constructor(
    private val getSingleLaunchUseCase: GetSingleLaunchUseCase
)  : ViewModel(){
    private val _capsuleState = mutableStateOf(CapsuleDetailsViewState())
    val capsuleState = _capsuleState as State<CapsuleDetailsViewState>

    fun getLaunches(id: String) {
        _capsuleState.value = capsuleState.value.copy(isLoading = true)
        viewModelScope.launch {
            getSingleLaunchUseCase(id = id).collectLatest {  result ->
               when(result){
                   is Resource.Error -> {
                          _capsuleState.value = capsuleState.value.copy(
                            isLoading = false,
                            error = result.message
                          )
                   }
                   is Resource.Success -> {
                          _capsuleState.value = capsuleState.value.copy(
                            isLoading = false,
                            launch = emptyList() // Yet to think about this
                          )
                     }

                   else -> capsuleState
               }
            }
        }
    }

}

data class CapsuleDetailsViewState(
    val launch : List<Launches> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

