package dev.robert.spacexgo.features.rockets.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import dev.robert.spacexgo.R
import dev.robert.spacexgo.core.utils.UiEvents
import dev.robert.spacexgo.features.rockets.domain.model.Rocket
import dev.robert.spacexgo.core.presentation.theme.darkGrey
import dev.robert.spacexgo.features.destinations.RocketDetailsScreenDestination
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

    RocketScreenContent(scaffoldState = scaffoldState, state = state, navigator = navigator)

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun RocketScreenContent(
    scaffoldState: ScaffoldState,
    state: RocketsState,
    navigator: DestinationsNavigator
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Rockets", fontFamily = FontFamily.Serif)
                },
                elevation = 1.dp
            )
        }
    ) {

        Box(modifier = Modifier.fillMaxSize()) {

            RocketLoadingStateComponent(state)

            RocketsErrorStateComponent(state)

            RocketsEmptyStateComponent(state)

            ShipSuccessStateComponent(state, navigator = navigator)
        }
    }
}

@Composable
private fun ShipSuccessStateComponent(state: RocketsState, navigator: DestinationsNavigator) {
    if (!state.isLoading && state.error == null && state.rockets.isNotEmpty()) {
        LazyColumn() {
            items(state.rockets) { item ->
                RocketItem(item, navigator = navigator)
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
private fun BoxScope.RocketsEmptyStateComponent(state: RocketsState) {
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
    navigator: DestinationsNavigator
) {
    Card(
        elevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(8.dp)
            .clickable {
                navigator.navigate(RocketDetailsScreenDestination(rocket = rocket))
            }
    ) {
        RocketCardContent(rocket)
    }
}

@Composable
private fun RocketCardContent(
    rocket: Rocket
) {
    Row(
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(70.dp)
        ) {

            CoilImage(
                imageModel = {
                    rocket.flickrImages[0]
                },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                ),
                modifier = Modifier.fillMaxSize(),
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                },
            )

        }
        Column(
            modifier = Modifier.padding(horizontal = 5.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = rocket.name,
                style = TextStyle(
                    fontSize = 13.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold
                ),
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = rocket.type,
                style = TextStyle(fontSize = 11.sp, fontFamily = FontFamily.Serif)
            )
            Text(
                text = "Stages: ${rocket.stages}",
                style = TextStyle(fontSize = 11.sp, fontFamily = FontFamily.Serif)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 3.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(8.dp)
                        .background(
                            if (rocket.active) Color.Green else Color.Red
                        )
                )
                Text(
                    text = if (rocket.active) "Active" else "Inactive",
                    modifier = Modifier.padding(horizontal = 3.dp),
                    style = TextStyle(fontSize = 11.sp, fontFamily = FontFamily.Serif)
                )
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Image(
                    modifier = Modifier
                        .size(16.dp)
                        .align(Alignment.CenterEnd),
                    painter = painterResource(id = R.drawable.arrow_forward),
                    contentDescription = null
                )
            }
        }

    }
}