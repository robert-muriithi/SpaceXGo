package dev.robert.spacexgo.features.capsules.presentation

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
import dev.robert.spacexgo.features.capsules.domain.model.Capsule
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@Destination()
fun CapsulesScreen(
    viewModel: CapsulesViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventsFlow.collectLatest { event ->
            when (event) {
                is UiEvents.ErrorEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.message)
                }
                else -> {
                }
            }
        }
    }
    val state = viewModel.capsuleState.value
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Capsules")
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
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            if (state.error != null && !state.isLoading) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = state.error.toString()
                )
            }
            if (!state.isLoading && state.error == null && state.data.isEmpty())
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "No capsules found"
                )
        }

        LazyColumn {
            item {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(
                                data = "https://1734811051.rsc.cdn77.org/data/images/full/372994/dragon.jpg"
                            )
                            .apply(block = fun ImageRequest.Builder.() {
                                crossfade(true)
                            }).build()
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentDescription = null
                )
            }
            items(state.data) { capsule ->
                CapsuleItem(capsule = capsule)
            }

        }
    }
}

@Composable
fun CapsuleItem(capsule: Capsule) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column {
            Text(text = capsule.serial ?: "No serial")
            Text(text = capsule.status ?: "No status")
            Text(text = capsule.type ?: "")
            Text(text = capsule.lastUpdate ?: "")
        }
    }
}
