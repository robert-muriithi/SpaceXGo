package dev.robert.spacexgo.features.launches.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.robert.spacexgo.features.launches.domain.model.Launches

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun LaunchesScreen(
    navigator: DestinationsNavigator,
    viewModel: LaunchesViewModel = hiltViewModel(),
) {
   val scaffoldState = rememberScaffoldState()
    val categories = listOf("All", "Past", "Upcoming")
    val state = viewModel.launchState.value
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Launches" , style = TextStyle(fontFamily = FontFamily.Serif))
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)){
                LazyRow(modifier = Modifier.fillMaxSize()){
                    items(categories) { category ->
                        Text(
                            text = category,
                            modifier = Modifier
                                .padding(10.dp)
                                .clickable {
                                    when(category){
                                        "All" -> viewModel.getAllLaunches()
                                        "Past" -> viewModel.getPastLaunches()
                                        "Upcoming" -> viewModel.getUpcomingLaunches()
                                    }
                                }
                        )
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {

                }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            // Loading
            LaunchesLoadingStateComponent(state)

            // Error
            LaunchesErrorStateComponent(state)

            // Data is Empty
            LaunchesEmptyStateComponent(state)

            // There is Data
            LaunchesSuccessStateComponent(state, navigator = navigator)
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
fun LaunchesSuccessStateComponent(state: LaunchesState, navigator: DestinationsNavigator) {
    if (!state.isLoading && state.message == null && state.launches.isNotEmpty()) {
        LazyColumn(modifier = Modifier.testTag("LaunchesSuccessStateComponent")) {
            items(state.launches) { item ->
                LaunchItem(launch = item, navigator = navigator)
            }
        }
    }
}

@Composable
fun LaunchItem(launch: Launches, navigator: DestinationsNavigator) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .testTag("LaunchItem")
            .clickable {

            }
    ) {
        Text(text = launch.name)
    }
}
