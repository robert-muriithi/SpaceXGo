package dev.robert.spacexgo.features.rockets.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.robert.spacexgo.R
import dev.robert.spacexgo.features.rockets.domain.model.Rocket
import kotlinx.coroutines.delay
import java.util.*

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Destination
@Composable
fun RocketDetailsScreen(
    rocket: Rocket,
    navigator: DestinationsNavigator
) {
    val snackbarHostState by remember { mutableStateOf(SnackbarHostState()) }
    val pagerState = rememberPagerState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = rocket.name)
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
                },
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
                item {
                    Box(
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                    )
                    {
                        HorizontalPager(
                            count = rocket.flickrImages.size,
                            state = pagerState,
                            modifier = Modifier.fillMaxSize()
                        ) { page ->
                            val painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(LocalContext.current)
                                    .data(
                                        data = rocket.flickrImages[page]
                                    )
                                    .placeholder(R.drawable.ic_rocket)
                                    .apply(block = fun ImageRequest.Builder.() {
                                        crossfade(true)

                                    }).build()

                            )
                            Image(
                                painter = painter,
                                contentDescription = "rocket",
                                modifier = Modifier
                                    .height(200.dp)
                                    .fillMaxWidth(),
                                contentScale = ContentScale.Crop
                            )
                            LaunchedEffect(pagerState.currentPage) {
                                delay(5000) // wait for 5 seconds.
                                // increasing the position and check the limit
                                var newPosition = pagerState.currentPage + 1
                                if (newPosition > rocket.flickrImages.lastIndex) newPosition = 0
                                // scrolling to the new position.
                                pagerState.animateScrollToPage(newPosition)
                            }
                        }
                    }
                }
                item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Column {
                            DescriptionItem(
                                label = "Type",
                                value = rocket.type.uppercase(Locale.getDefault())
                            )
                            DescriptionItem(
                                label = "Country",
                                value = rocket.country
                            )
                            DescriptionItem(
                                label = "Status",
                                value = if (rocket.active) "Active" else "Inactive"
                            )
                            DescriptionItem(
                                label = "Success rate",
                                value = "${rocket.successRatePct} %"
                            )
                            Text(
                                text = rocket.description,
                                textAlign = TextAlign.Justify,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DescriptionItem(label: String, value: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 10.dp)
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
        )
        Text(
            text = value,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Serif
            )
        )
    }
}
