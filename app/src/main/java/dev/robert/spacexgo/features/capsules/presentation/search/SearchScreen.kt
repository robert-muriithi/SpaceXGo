package dev.robert.spacexgo.features.capsules.presentation.search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.robert.spacexgo.core.presentation.common.SearchBar
import dev.robert.spacexgo.features.capsules.domain.model.Capsule
import dev.robert.spacexgo.features.capsules.presentation.CapsuleItem


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Destination
@Composable
fun CapsulesSearchScreen(
    navigator: DestinationsNavigator,
    viewModel: CapsuleSearchViewModel = hiltViewModel()
) {
    val snackbarHostState by remember { mutableStateOf(SnackbarHostState()) }
    val searchString by viewModel.query
    val state by viewModel.capsuleState

    Scaffold(
        topBar = {
            CapsulesSearchAppBar(
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

            if(searchString.isEmpty()){
                Text(
                    text = "Search for a capsule",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            if (!state.isLoading && state.capsules.isEmpty() && state.error == null && searchString.isNotEmpty()) {
                Text(text = "No capsules found", modifier = Modifier.align(Alignment.Center))
            }
            if (!state.isLoading && state.capsules.isNotEmpty() && state.error == null && searchString.isNotEmpty()) {
                CapsulesSearchScreenContent(
                    capsuleList = state.capsules,
                    navigator = navigator
                )
            }
        }
    }
}

@Composable
fun CapsulesSearchScreenContent(capsuleList: List<Capsule>, navigator: DestinationsNavigator) {
    LazyColumn {
        items(capsuleList.size) { capsule ->
            CapsuleItem(
                capsule = capsuleList[capsule],
                index = capsule,
                navigator = navigator,
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CapsulesSearchAppBar(
    onNavigateUp: () -> Unit,
    searchString: String,
    viewModel: CapsuleSearchViewModel
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


