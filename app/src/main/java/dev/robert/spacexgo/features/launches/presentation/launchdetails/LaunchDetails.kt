package dev.robert.spacexgo.features.launches.presentation.launchdetails

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.robert.spacexgo.R
import dev.robert.spacexgo.core.presentation.common.InfoRows
import dev.robert.spacexgo.core.presentation.theme.darkBlue
import dev.robert.spacexgo.features.launches.domain.model.Launches
import kotlinx.coroutines.delay


@OptIn(ExperimentalPagerApi::class)
@Composable
@Destination
fun LaunchDetailsScreen(
    navigator: DestinationsNavigator,
    launch: Launches,
    viewModel: LaunchDetailsViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val pagerState = rememberPagerState()
    val state = viewModel.rocketDetails.value
    LaunchDetailsScreenContent(
        scaffoldState = scaffoldState,
        launch = launch,
        navigator = navigator,
        pagerState = pagerState,
        state = state
    )
}

@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LaunchDetailsScreenContent(
    scaffoldState: ScaffoldState,
    launch: Launches,
    navigator: DestinationsNavigator,
    pagerState: PagerState,
    state: LaunchDetailsState
) {
    val context = LocalContext.current

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = launch.name, fontFamily = FontFamily.Serif)
                },
                elevation = 1.dp,
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
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(launch.linksDto?.webcast))
                context.startActivity(intent)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_video),
                    contentDescription = "Watch video"
                )
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn() {
                item {
                    Box(
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                    )
                    {
                        HorizontalPager(
                            count = launch.linksDto?.flickr?.original!!.size,
                            state = pagerState,
                            modifier = Modifier.fillMaxSize()
                        ) { page ->
                            val painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(LocalContext.current)
                                    .data(
                                        data = launch.linksDto.flickr.original[page]
                                    )
                                    .placeholder(R.drawable.ic_launches)
                                    .error(R.drawable.ic_question)
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
                                if (newPosition > launch.linksDto.flickr.original.lastIndex) newPosition =
                                    0
                                // scrolling to the new position.
                                pagerState.animateScrollToPage(newPosition)
                            }
                        }
                    }
                }
                item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Card(
                            modifier = Modifier
                                .padding(vertical = 8.dp, horizontal = 10.dp),
                            border = BorderStroke(1.5.dp, color = Color.Gray),
                            shape = MaterialTheme.shapes.medium,
                            elevation = 1.dp
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                            ) {
                                Row() {
                                    Image(
                                        painter = rememberAsyncImagePainter(
                                            ImageRequest.Builder(LocalContext.current)
                                                .data(
                                                    data = launch.linksDto?.patchDto?.large
                                                        ?: launch.linksDto?.patchDto?.small
                                                )
                                                .apply(block = fun ImageRequest.Builder.() {
                                                    crossfade(true)

                                                }).build()
                                        ),
                                        modifier = Modifier
                                            .size(80.dp)
                                            .clip(shape = CircleShape),
                                        contentScale = ContentScale.Crop,
                                        contentDescription = null
                                    )
                                    Column(
                                        verticalArrangement = Arrangement.Top,
                                        horizontalAlignment = Alignment.Start,
                                        modifier = Modifier.padding(horizontal = 5.dp)
                                    ) {
                                        Text(
                                            text = launch.name,
                                            fontFamily = FontFamily.Serif,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                        )
                                        Row(
                                            modifier = Modifier.padding(vertical = 5.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.ic_calender),
                                                contentDescription = "calender icon"
                                            )
                                            Text(text = launch.dateUtc)
                                        }
                                        Row(
                                            modifier = Modifier.padding(vertical = 5.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painter = painterResource(id = if (launch.success) R.drawable.ic_check else R.drawable.ic_cancel),
                                                contentDescription = null
                                            )
                                            Text(
                                                text = if (launch.success) "Successful" else "Failed",
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(5.dp))
                                Divider(
                                    modifier = Modifier.padding(horizontal = 10.dp),
                                    thickness = 0.3.dp,
                                )
                                Text(
                                    text = launch.details ?: "This launch has currently no details",
                                    modifier = Modifier.padding(8.dp),
                                    textAlign = TextAlign.Justify
                                )
                            }
                        }
                    }
                }
                item {
                    Box(modifier = Modifier.fillMaxWidth()) {


                        Card(
                            modifier = Modifier
                                .padding(vertical = 8.dp, horizontal = 10.dp),
                            border = BorderStroke(1.5.dp, color = Color.Gray),
                            shape = MaterialTheme.shapes.medium,
                            elevation = 1.dp
                        ) {
                            Column(modifier = Modifier.fillMaxSize()) {
                                Row(horizontalArrangement = Arrangement.Center) {
                                    Text(text = "ROCKET")
                                }

                            }

                        }
                    }
                }
            }
        }
    }
}
