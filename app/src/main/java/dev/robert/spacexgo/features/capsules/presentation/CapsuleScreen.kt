package dev.robert.spacexgo.features.capsules.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.robert.spacexgo.R
import dev.robert.spacexgo.core.presentation.theme.darkBlue
import dev.robert.spacexgo.core.presentation.theme.lightBlue
import dev.robert.spacexgo.core.utils.UiEvents
import dev.robert.spacexgo.features.capsules.domain.model.Capsule
import dev.robert.spacexgo.features.destinations.CapsuleDetailsScreenDestination
import dev.robert.spacexgo.features.destinations.CapsulesSearchScreenDestination
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@Destination
fun CapsulesScreen(
    viewModel: CapsulesViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.eventsFlow.collectLatest { event ->
            when (event) {
                is UiEvents.ErrorEvent -> {
                   snackbarHostState.showSnackbar(message = event.message)
                }
                else -> {
                }
            }
        }
    }
    val state = viewModel.capsuleState.value
    val sortOptions = listOf("All", "Active", "Retired", "Unknown", "Destroyed")
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Capsules")
                },
                actions = {
                    IconButton(onClick = {
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_sort),
                            contentDescription = "Sort",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigator.navigate(CapsulesSearchScreenDestination())
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
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
            items(state.data) { capsule ->
                CapsuleItem(
                    capsule = capsule,
                    index = state.data.indexOf(capsule),
                    navigator = navigator
                )
            }

        }
    }
}

@Composable
fun CapsuleItem(capsule: Capsule, index: Int, navigator: DestinationsNavigator) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 5.dp, horizontal = 10.dp)
            .clickable {
                navigator.navigate(CapsuleDetailsScreenDestination(capsule = capsule))
            },
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 3.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {
                Box(
                    modifier = Modifier
                        .width(37.dp)
                        .height(37.dp)
                        .clip(CircleShape)
                        .background(color = darkBlue)
                        .padding(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(34.dp)
                            .height(34.dp)
                            .clip(CircleShape)
                            .background(color = lightBlue)
                            .padding(4.dp)
                    ) {
                        Text(
                            text = (index + 1).toString(),
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(2.dp),
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Serif
                            )
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = capsule.type ?: "Unknown type",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                fontFamily = FontFamily.Serif
                            )
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
                                        when (capsule.status) {
                                            "active" -> Color.Green
                                            "destroyed" -> Color.Red
                                            "retired" -> Color.Yellow
                                            else -> Color.Gray
                                        }
                                    )
                            )
                            Text(
                                text = capsule.status?.elementAt(0)?.titlecase() ?: "Unknown",
                                modifier = Modifier.padding(horizontal = 3.dp),
                                style = TextStyle(fontSize = 11.sp, fontFamily = FontFamily.Serif)
                            )
                        }
                    }
                    Text(
                        text = capsule.serial ?: "No serial",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Serif
                        )
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 5.dp),
            ) {
                Text(
                    text = capsule.lastUpdate ?: "Last update and location is unknown",
                    textAlign = TextAlign.Justify,
                    style = TextStyle(fontSize = 12.sp, fontFamily = FontFamily.Serif)
                )
            }
        }
    }
}
