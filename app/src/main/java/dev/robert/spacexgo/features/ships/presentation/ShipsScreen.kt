package dev.robert.spacexgo.features.ships.presentation

import android.annotation.SuppressLint
import android.widget.Toolbar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.ImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.robert.spacexgo.R
import dev.robert.spacexgo.core.utils.UiEvents
import dev.robert.spacexgo.features.destinations.ShipDetailsScreenDestination
import dev.robert.spacexgo.features.ships.domain.model.Ship
import dev.robert.spacexgo.core.presentation.theme.darkGrey
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import me.onebone.toolbar.*

@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination(start = true)
@Composable
fun ShipsScreen(
    navigator: DestinationsNavigator,
    viewModel: ShipsViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val collapsingToolbarState = rememberCollapsingToolbarScaffoldState()
    val pagerState = rememberPagerState()

    LaunchedEffect(key1 = true) {
        viewModel.eventsFlow.collectLatest { event ->
            when (event) {
                is UiEvents.ErrorEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.message)
                }
                is UiEvents.NavigationEvent -> {
                    navigator.navigate(route = event.route)
                }
            }
        }
    }

    val state = viewModel.shipState.value
    ShipScreenContent(
        state = state,
        pagerState = pagerState,
        collapsingToolbarState = collapsingToolbarState,
        navigator = navigator
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ShipScreenContent(
    state: ShipsState,
    collapsingToolbarState: CollapsingToolbarScaffoldState,
    pagerState: PagerState,
    navigator: DestinationsNavigator
) {

    val images: List<String> = listOf(
        "https://farm5.staticflickr.com/4615/40143096241_11128929df_b.jpg",
        "https://farm5.staticflickr.com/4702/40110298232_91b32d0cc0_b.jpg",
        "https://farm5.staticflickr.com/4676/40110297852_5e794b3258_b.jpg",
        "https://farm5.staticflickr.com/4745/40110304192_6e3e9a7a1b_b.jpg"
    )

    CollapsingToolbarScaffold(
        modifier = Modifier,
        state = collapsingToolbarState,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {
            val textSize = (13 + (25 - 7) * collapsingToolbarState.toolbarState.progress).sp
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .pin()
                    .background(color = darkGrey)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                HorizontalPager(
                    state = pagerState,
                    count = images.size,
                    modifier = Modifier
                        .fillMaxSize()
                ) { page ->
                    val painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(
                                data = images[page]
                            )
                            .apply(block = fun ImageRequest.Builder.() {
                                crossfade(true)

                            }).build()
                    )

                    //val painterState = painter.state
                    /*if(painterState is AsyncImagePainter.State.Loading){
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = White
                        )*/
                    /*}else {*/
                        Image(
                            painter = painter,
                            contentDescription = null,
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth()
                                .pin(),
                            contentScale = ContentScale.Crop,
                            alpha = if (textSize.value < 18f) 0f else 1f
                        )
                    /*}*/

                }
            }
            Text(
                text = "Ships and Vehicles",
                style = TextStyle(
                    color = Color.White,
                    fontSize = textSize,
                    fontFamily = FontFamily.Monospace,
                ),
                modifier = Modifier
                    .padding(10.dp)
                    .road(
                        whenCollapsed = Alignment.TopCenter,
                        whenExpanded = Alignment.BottomCenter
                    ),

                )
            LaunchedEffect(pagerState.currentPage) {
                delay(10000) // wait for 5 seconds.
                // increasing the position and check the limit
                var newPosition = pagerState.currentPage + 1
                if (newPosition > images.lastIndex) newPosition = 0
                // scrolling to the new position.
                pagerState.animateScrollToPage(newPosition)
            }

        },
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Loading
            ShipsLoadingStateComponent(state)

            // Error
            ShipErrorStateComponent(state)

            // Data is Empty
            ShipsEmptyStateComponent(state)

            // There is Data
            ShipsSuccessStateComponent(state, navigator = navigator)
        }
    }
}

@Composable
private fun ShipsSuccessStateComponent(state: ShipsState, navigator: DestinationsNavigator) {
    if (!state.isLoading && state.error == null && state.ships.isNotEmpty()) {
        LazyColumn(modifier = Modifier.testTag("ShipsSuccessStateComponent")) {
            items(state.ships) { item ->
                ShipItem(ship = item, navigator = navigator)
            }
        }
    }
}

@Composable
private fun BoxScope.ShipsEmptyStateComponent(state: ShipsState) {
    if (!state.isLoading && state.error == null && state.ships.isEmpty()) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .testTag("ShipsEmptyStateComponent"),
            text = "No Ships Found"
        )
    }
}

@Composable
private fun BoxScope.ShipErrorStateComponent(state: ShipsState) {
    if (!state.isLoading && state.error != null) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .testTag("ShipErrorStateComponent"),
            text = state.error
        )
    }
}

@Composable
private fun BoxScope.ShipsLoadingStateComponent(state: ShipsState) {
    if (state.isLoading) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .testTag("ShipsLoadingStateComponent")
        )
    }
}

@Composable
fun ShipItem(ship: Ship, navigator: DestinationsNavigator) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 5.dp)
            .height(70.dp)
            .clickable {
                navigator.navigate(ShipDetailsScreenDestination(shipData = ship))
            }
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(70.dp)
            ) {

                val painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(
                            data = ship.image
                        )
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                        }).build()
                )
                val painterState = painter.state
                if(painterState is AsyncImagePainter.State.Loading){
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                if(painterState is AsyncImagePainter.State.Success){
                    Image(
                        painter = painter,

                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = null
                    )

                }

            }
            Column(
                modifier = Modifier.padding(horizontal = 5.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = ship.name,
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold
                    ),
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = ship.model ?: "Unknown Model",
                    style = TextStyle(fontSize = 11.sp, fontFamily = FontFamily.Serif)
                )
                Text(
                    text = "Built in ${ship.yearBuilt}",
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
                                if (ship.active) Color.Green else Color.Red
                            )
                    )
                    Text(
                        text = if (ship.active) "Active" else "Inactive",
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

}