package dev.robert.spacexgo.features.launches.presentation.launches

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import dev.robert.spacexgo.core.presentation.theme.darkBlue
import dev.robert.spacexgo.core.presentation.theme.darkGrey
import dev.robert.spacexgo.features.destinations.LaunchDetailsScreenDestination
import dev.robert.spacexgo.features.destinations.LaunchesSearchScreenDestination
import dev.robert.spacexgo.features.launches.domain.model.Launches

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Destination
@Composable
fun LaunchesScreen(
    navigator: DestinationsNavigator,
    viewModel: LaunchesViewModel = hiltViewModel(),
) {
    val snackbarHostState by remember { mutableStateOf(SnackbarHostState()) }
    val categories = listOf("All", "Past", "Upcoming")
    val state = viewModel.launchState.value
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Launches", style = TextStyle(fontFamily = FontFamily.Serif))

                },
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigator.navigate(LaunchesSearchScreenDestination)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            }
        }
    ) {


        Column(modifier = Modifier.fillMaxSize()) {
            //TopSection
            CategoriesSection(categories = categories, viewModel = viewModel)

            Box(modifier = Modifier.fillMaxSize()) {

                // Loading
                LaunchesLoadingStateComponent(state = state)

                // Error
                LaunchesErrorStateComponent(state = state)

                // Data is Empty
                LaunchesEmptyStateComponent(state = state)

                // There is Data
                LaunchesSuccessStateComponent(
                    state = state,
                    navigator = navigator,
                )
            }
        }
    }
}

@Composable
private fun CategoriesSection(
    categories: List<String>,
    viewModel: LaunchesViewModel
) {
    var selectedIndex by remember { mutableStateOf(0) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(color = darkGrey)
    ) {
        LazyRow(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(categories) { category ->
                Box(
                    modifier = Modifier
                        .clip(
                            shape = RoundedCornerShape(8.dp)
                        )
                        .background(
                            color = if (categories.indexOf(category) == selectedIndex) darkBlue else Color.Transparent
                        )
                        .width(80.dp)
                ) {
                    Text(
                        text = category,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(8.dp)
                            .clickable {
                                when (category) {
                                    "All" -> {
                                        viewModel.getAllLaunches()
                                        selectedIndex = 0
                                    }
                                    "Past" -> {
                                        viewModel.getPastLaunches()
                                        selectedIndex = 1
                                    }
                                    "Upcoming" -> {
                                        viewModel.getUpcomingLaunches()
                                        selectedIndex = 2
                                    }
                                }
                            },
                        style = TextStyle(color = Color.White, fontFamily = FontFamily.Serif)
                    )
                }
            }
        }
    }
}

@Composable
fun BoxScope.LaunchesLoadingStateComponent(state: LaunchesState) {
    if (state.isLoading) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .testTag("LaunchesLoadingStateComponent")
        )
    }
}

@Composable
fun BoxScope.LaunchesErrorStateComponent(state: LaunchesState) {
    if (!state.isLoading && state.message != null) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .testTag("LaunchesErrorStateComponent"),
            text = state.message
        )
    }
}

@Composable
fun BoxScope.LaunchesEmptyStateComponent(state: LaunchesState) {
    if (!state.isLoading && state.message == null && state.launches.isEmpty()) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .testTag("LaunchesEmptyStateComponent"),
            text = "No Ships Found"
        )
    }
}

@Composable
fun LaunchesSuccessStateComponent(
    state: LaunchesState,
    navigator: DestinationsNavigator,
) {
    if (!state.isLoading && state.message == null && state.launches.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier
                .testTag("LaunchesSuccessStateComponent")
                .background(color = darkGrey)
        ) {
            items(state.launches) { item ->
                LaunchItem(
                    launch = item,
                    navigator = navigator
                )
            }
        }
    }
}

@Composable
fun LaunchItem(
    launch: Launches,
    navigator: DestinationsNavigator
) {
    Card(
        modifier = Modifier
            .width(80.dp)
            .height(170.dp)
            .padding(8.dp)
            .testTag("LaunchItem")
            .clickable {
                navigator.navigate(LaunchDetailsScreenDestination(launch = launch))
            }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            CoilImage(
                imageModel = {
                    launch.linksDto?.patchDto?.large
                        ?: launch.linksDto?.patchDto?.small
                },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Fit,
                    contentDescription = null,
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                },
            )

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.TopStart
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Text(
                        text = "#${launch.flightNumber}",
                        color = darkGrey,
                        fontSize = 12.sp
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = launch.name,
                        maxLines = 1,
                        fontSize = 14.sp,
                        color = darkBlue,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = if (launch.success) "Successful" else "Failed",
                        fontSize = 12.sp,
                        color = darkBlue,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
