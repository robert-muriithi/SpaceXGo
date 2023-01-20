package dev.robert.spacexgo.features.company.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import dev.robert.spacexgo.core.utils.UiEvents
import dev.robert.spacexgo.features.ships.presentation.ShipScreenContent
import kotlinx.coroutines.flow.collectLatest

@Composable
@Destination
fun CompanyScreen(
    viewModel: CompanyViewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true, block = {
        viewModel.uiEvent.collectLatest {event ->
            when(event){
                is UiEvents.ErrorEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.message)
                }
                is UiEvents.NavigationEvent -> {

                }
            }
        }
    })
    val state = viewModel.companyInfoState.value
    CompanyInfoScreenContent(scaffoldState = scaffoldState, state = state)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CompanyInfoScreenContent(
    scaffoldState: ScaffoldState,
    state: CompanyInfoState
) {

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Company information")
                },
                elevation = 1.dp
            )
        }
    ) {

    }

}