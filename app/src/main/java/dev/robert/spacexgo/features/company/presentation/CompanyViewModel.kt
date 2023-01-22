package dev.robert.spacexgo.features.company.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.robert.spacexgo.core.utils.Resource
import dev.robert.spacexgo.core.utils.UiEvents
import dev.robert.spacexgo.features.company.domain.model.CompanyInfo
import dev.robert.spacexgo.features.company.domain.model.History
import dev.robert.spacexgo.features.company.domain.usecases.GetCompanyHistoryUseCase
import dev.robert.spacexgo.features.company.domain.usecases.GetCompanyInfoUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyViewModel @Inject constructor(
    private val companyInfoUseCase: GetCompanyInfoUseCase,
    private val companyHistoryUseCase: GetCompanyHistoryUseCase
) : ViewModel() {
    private val _companyInfoState = mutableStateOf(CompanyInfoState())
    val companyInfoState = _companyInfoState as State<CompanyInfoState>

    private val _companyHistoryState = mutableStateOf(CompanyHistoryState())
    val companyHistoryState = _companyHistoryState as State<CompanyHistoryState>

    private val _uiEvent = MutableSharedFlow<UiEvents>()
    val uiEvent = _uiEvent

    fun getCompanyInfo(){
        _companyInfoState.value = companyInfoState.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
           companyInfoUseCase().collectLatest { result ->
               when(result){
                   is Resource.Error -> {
                       _uiEvent.emit(
                           UiEvents.ErrorEvent(
                               message = result.message ?: "Unkown error occurred"
                           )
                       )
                       _companyInfoState.value = companyInfoState.value.copy(
                           error = result.message,
                           isLoading = false
                       )
                   }
                   is Resource.Success -> {
                       _companyInfoState.value = companyInfoState.value.copy(
                           data = result.data,
                           isLoading = false
                       )
                   }
                   else -> {
                       companyInfoState
                   }
               }
           }
            _companyHistoryState.value = companyHistoryState.value.copy(
                isLoading = true
            )
            companyHistoryUseCase().collectLatest { result ->
                when(result){
                    is Resource.Error -> {
                        _uiEvent.emit(
                            UiEvents.ErrorEvent(
                                message = result.message ?: "Unkown error occurred"
                            )
                        )
                        _companyHistoryState.value = companyHistoryState.value.copy(
                            error = result.message,
                            isLoading = false
                        )
                    }
                    is Resource.Success -> {
                        _companyHistoryState.value = companyHistoryState.value.copy(
                            data = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    else -> {
                        companyHistoryState
                    }
                }
            }
        }
    }

    init {
        getCompanyInfo()
    }
}

data class CompanyInfoState(
    val error: String? = null,
    val data : CompanyInfo? = null,
    val isLoading: Boolean = false
)

data class CompanyHistoryState(
    val error: String? = null,
    val data : List<History> = emptyList(),
    val isLoading: Boolean = false
)