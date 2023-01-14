package dev.robert.spacexgo.features.ships.presentation

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.robert.spacexgo.core.utils.UiEvents
import dev.robert.spacexgo.features.ships.domain.model.Ship
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination(start = true)
@Composable
fun ShipsScreen(
    navigator: DestinationsNavigator,
    viewModel: ShipsViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true){
        viewModel.eventsFlow.collectLatest { event ->
            when(event){
                is UiEvents.SnackEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.message)
                }
                is UiEvents.NavigationEvent -> {
                    navigator.navigate(route = event.route)
                }
            }
        }
    }

    val state = viewModel.shipState.value
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Ships")
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
            // Loading
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // Error
            if (!state.isLoading && state.error != null) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = state.error
                )
            }

            // Data is Empty
            if (!state.isLoading && state.error == null && state.ships.isEmpty()) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "No Ships Found"
                )
            }

            // There is Data
            LazyColumn {
                items(state.ships) { item ->
                    ShipItem(ship = item)
                }
            }
        }
    }
}

@Composable
fun ShipItem(ship: Ship) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(
                            data = ship.image
                        )
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                        }).build()
                ),
                modifier = Modifier
                    .size(70.dp),
                contentDescription = null
            )
            Text(text = ship.name)
        }

    }
}