package dev.robert.spacexgo.features.capsules.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.robert.spacexgo.features.capsules.domain.model.Capsule
import dev.robert.spacexgo.features.launches.domain.model.Launches
import dev.robert.spacexgo.features.launches.presentation.launches.LaunchItem


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun CapsuleDetailsScreen(
    navigator: DestinationsNavigator,
    capsule: Capsule,
    viewModel: CapsuleDetailsViewModel = hiltViewModel()
) {
    val launches = viewModel.capsuleState.value.launch

    LaunchedEffect(key1 = true, block = {
        for (launch in launches) {
            viewModel.getLaunches(id = launch.id)
        }
    })

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Launches", fontFamily = FontFamily.Serif)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navigator.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues)
        ) {
            CapsuleDetailsScreenContent(launches = launches, navigator = navigator)
        }
    }
}

@Composable
fun CapsuleDetailsScreenContent(launches: List<Launches>, navigator: DestinationsNavigator) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier
            .testTag("SearchCapsuleSuccessStateComponent")

    ) {
        items(launches.size) { launch ->
            LaunchItem(
                launch = launches[launch],
                navigator = navigator
            )
        }
    }
}

