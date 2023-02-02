package dev.robert.spacexgo.features.launches.presentation.search

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.robert.spacexgo.core.presentation.common.SearchBar
import dev.robert.spacexgo.core.presentation.theme.darkGrey
import dev.robert.spacexgo.features.capsules.domain.model.Capsule
import dev.robert.spacexgo.features.capsules.presentation.CapsuleItem
import dev.robert.spacexgo.features.capsules.presentation.search.CapsuleSearchViewModel
import dev.robert.spacexgo.features.capsules.presentation.search.CapsulesSearchAppBar
import dev.robert.spacexgo.features.capsules.presentation.search.CapsulesSearchScreenContent
import dev.robert.spacexgo.features.launches.domain.model.Launches
import dev.robert.spacexgo.features.launches.presentation.launches.LaunchItem

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun LaunchesSearchScreen(
    navigator: DestinationsNavigator,
    viewModel: LaunchesSearchViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()
    val searchString = viewModel.query.value
    val state = viewModel.launchState.value

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            LaunchesSearchAppBar(
                onNavigateUp = { navigator.navigateUp() },
                searchString = searchString,
                viewModel = viewModel
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            if (searchString.isEmpty()) {
                Text(
                    text = "Search for a launch",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            if (!state.isLoading && state.launches.isEmpty() && state.error == null && searchString.isNotEmpty()) {
                Text(text = "No capsules found", modifier = Modifier.align(Alignment.Center))
            }
            if (!state.isLoading && state.launches.isNotEmpty() && state.error == null && searchString.isNotEmpty()) {
                LaunchesSearchScreenContent(
                    launches = state.launches,
                    navigator = navigator
                )
            }
        }
    }

}


@Composable
fun LaunchesSearchScreenContent(launches: List<Launches>, navigator: DestinationsNavigator) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier
            .testTag("SearchLaunchesSuccessStateComponent")

    ){
        items(launches.size) { launch ->
            LaunchItem(
                launch = launches[launch],
                navigator = navigator
            )
        }
    }
}


@Composable
fun LaunchesSearchAppBar(
    onNavigateUp: () -> Unit,
    searchString: String,
    viewModel: LaunchesSearchViewModel
) {
    TopAppBar(
        title = {
            SearchBar(
                searchStringState = searchString,
                onTextChange = { viewModel.onQueryChanged(it) },
                onSearchClicked = { viewModel.onSearchClicked(it.trim()) }
            )
        },

        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )

}