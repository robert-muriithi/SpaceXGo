package dev.robert.spacexgo.features.capsules.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.robert.spacexgo.features.capsules.domain.model.Capsule


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun CapsuleDetailsScreen(
    navigator: DestinationsNavigator,
    capsule: Capsule,
    viewModel: CapsuleDetailsViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val launches = viewModel.capsuleState.value.launch
    LaunchedEffect(key1 = true, block = {
        for (launch in launches) {
            viewModel.getLaunches(id = launch.id)
        }
    } )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Launches")
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
    ) {
        CapsuleDetailsScreenContent(scaffoldState = scaffoldState, capsule = capsule)
    }
}

@Composable
fun CapsuleDetailsScreenContent(scaffoldState: ScaffoldState, capsule: Capsule) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(capsule.launches.size) {
            CapsuleDetailsItem(capsule = capsule, index = it)
        }
    }
}

@Composable
fun CapsuleDetailsItem(capsule: Capsule, index: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 8.dp
    ) {

    }
}
