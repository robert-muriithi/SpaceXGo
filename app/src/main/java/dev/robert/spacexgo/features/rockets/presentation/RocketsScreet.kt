package dev.robert.spacexgo.features.rockets.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.robert.spacexgo.core.utils.UiEvents
import dev.robert.spacexgo.features.rockets.domain.model.Rocket
import kotlinx.coroutines.flow.collectLatest

@Composable
@Destination
fun RocketsScreen(
    navigator: DestinationsNavigator,
    viewModel: RocketsViewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true, block = {
        viewModel.events.collectLatest { UiEvents ->
            when (UiEvents) {
                is UiEvents.ErrorEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = UiEvents.message)
                }
                is UiEvents.NavigationEvent -> {
                    //
                }
            }
        }
    })
    val state = viewModel.rocketsState.value

    RocketScreenContent(scaffoldState, state)

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun RocketScreenContent(
    scaffoldState: ScaffoldState,
    state: RocketsState,
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Rockets")
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )
                    }
                },
                elevation = 1.dp
            )
        }
    ) {

        Box(modifier = Modifier.fillMaxSize()) {

            RocketLoadingStateComponent(state)

            RocketsErrorStateComponent(state)

            RocketsEmptyStateComponet(state)

            ShipSuccessStateComponent(state)
        }
    }
}

@Composable
private fun ShipSuccessStateComponent(state: RocketsState) {
    if (!state.isLoading && state.error == null && state.rockets.isNotEmpty()) {
        LazyColumn() {
            items(state.rockets) { item ->
                RocketItem(item)
            }
        }
    }
}

@Composable
private fun BoxScope.RocketsErrorStateComponent(state: RocketsState) {
    if (!state.isLoading && state.error != null) {
        Text(
            text = state.error,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun BoxScope.RocketsEmptyStateComponet(state: RocketsState) {
    if (!state.isLoading && state.error != null && state.rockets.isEmpty()) {
        Text(
            text = "No rockets found",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun BoxScope.RocketLoadingStateComponent(state: RocketsState) {
    if (state.isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun RocketItem(
    rocket: Rocket,
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        RocketCardContent(rocket)
    }
}

@Composable
private fun RocketCardContent(rocket: Rocket) {
    Row {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(
                        rocket.flickrImages[0]
                    )
                    .apply(block = fun ImageRequest.Builder.() {
                        crossfade(true)
                    }).build()

            ), modifier = Modifier
                .size(70.dp),
            contentDescription = null
        )
        Text(text = rocket.name)
    }
}