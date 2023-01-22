package dev.robert.spacexgo.features.company.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import dev.robert.spacexgo.R
import dev.robert.spacexgo.core.utils.UiEvents
import dev.robert.spacexgo.features.company.domain.model.History
import dev.robert.spacexgo.features.ships.presentation.*
import dev.robert.spacexgo.ui.theme.darkBlue
import dev.robert.spacexgo.ui.theme.darkGrey
import dev.robert.spacexgo.ui.theme.lightBlue
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import me.onebone.toolbar.*

@OptIn(ExperimentalPagerApi::class)
@Composable
@Destination
fun CompanyScreen(
    viewModel: CompanyViewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    val collapsingToolbarState = rememberCollapsingToolbarScaffoldState()
    val pagerState = rememberPagerState()

    LaunchedEffect(key1 = true, block = {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvents.ErrorEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.message)
                }
                is UiEvents.NavigationEvent -> {

                }
            }
        }
    })
    val companyInfoState = viewModel.companyInfoState.value
    val historyState = viewModel.companyHistoryState.value
    CompanyInfoScreenContent(
        scaffoldState = collapsingToolbarState,
        companyInfoState = companyInfoState,
        pagerState = pagerState,
        historyState = historyState
    )
}

@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CompanyInfoScreenContent(
    scaffoldState: CollapsingToolbarScaffoldState,
    companyInfoState: CompanyInfoState,
    historyState: CompanyHistoryState,
    pagerState: PagerState,
) {
    val images: List<String> = listOf(
        "https://live.staticflickr.com/65535/49927519643_b43c6d4c44_o.jpg",
        "https://live.staticflickr.com/65535/49927519588_8a39a3994f_o.jpg",
        "https://live.staticflickr.com/65535/49928343022_6fb33cbd9c_o.jpg",
        "https://live.staticflickr.com/65535/49934168858_cacb00d790_o.jpg",
        "https://live.staticflickr.com/65535/49934682271_fd6a31becc_o.jpg",
        "https://live.staticflickr.com/65535/49956109906_f88d815772_o.jpg",
        "https://live.staticflickr.com/65535/49956109706_cffa847208_o.jpg",
        "https://live.staticflickr.com/65535/49956109671_859b323ede_o.jpg",
        "https://live.staticflickr.com/65535/49955609618_4cca01d581_o.jpg",
        "https://live.staticflickr.com/65535/49956396622_975c116b71_o.jpg",
        "https://live.staticflickr.com/65535/49955609378_9b77e5c771_o.jpg",
        "https://live.staticflickr.com/65535/49956396262_ef41c1d9b0_o.jpg"
    )

    CollapsingToolbarScaffold(
        modifier = Modifier,
        state = scaffoldState,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {
            val textSize = (13 + (25 - 7) * scaffoldState.toolbarState.progress).sp

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .pin()
                    .background(color = darkGrey)
            )
            HorizontalPager(
                state = pagerState,
                count = images.size,
                modifier = Modifier
                    .fillMaxSize()
            ) { page ->
                Image(
                    painter = rememberImagePainter(
                        data = images[page],
                        builder = {
                            crossfade(true)
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .pin(),
                    contentScale = ContentScale.Crop, alpha = if (textSize.value < 18f) 0f else 1f
                )

            }
            Text(
                text = "About Space X",
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
                delay(5000) // wait for 5 seconds.
                // increasing the position and check the limit
                var newPosition = pagerState.currentPage + 1
                if (newPosition > images.lastIndex) newPosition = 0
                // scrolling to the new position.
                pagerState.animateScrollToPage(newPosition)
            }

        }
    ) {

        Box(modifier = Modifier.fillMaxSize()) {
            //Success
            CompanyInfoSuccessStateComponent(
                companyInfoState = companyInfoState,
                historyState = historyState
            )

            //Loading
            CompanyInfoLoadingStateComponent(
                companyInfoState = companyInfoState,
                historyState = historyState
            )

            //Error
            CompanyInfoErrorStateComponent(
                companyInfoState = companyInfoState,
                historyState = historyState
            )

            //Empty
            CompanyInfoEmptyStateComponent(
                companyInfoState = companyInfoState,
                historyState = historyState
            )

        }

    }
}

@Composable
fun HistoryItem(history: History, count: Int) {
    val url = history.links.article
    val context = LocalContext.current
    val intent = remember {Intent(Intent.ACTION_VIEW, Uri.parse(url))}
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .clickable {
                 context.startActivity(intent)
            },
        elevation = 1.dp,
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 3.dp),
                verticalAlignment = Alignment.CenterVertically


            ) {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                        .clip(CircleShape)
                        .background(color = darkBlue)
                        .padding(5.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(37.dp)
                            .height(37.dp)
                            .clip(CircleShape)
                            .background(color = lightBlue)
                            .padding(5.dp)
                    ) {
                        Text(
                            text = (count + 1).toString(),
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
                    Row (horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){
                        Text(
                            text = history.title,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                fontFamily = FontFamily.Serif
                            )
                        )
                        Image(
                            painter = painterResource(id = R.drawable.arrow_forward),
                            modifier = Modifier.size(18.dp),
                            contentDescription = null
                        )
                    }
                    Text(
                        text = history.eventDateUtc,
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
                    text = history.details,
                    textAlign = TextAlign.Justify,
                    style = TextStyle(fontSize = 12.sp, fontFamily = FontFamily.Serif)
                )
            }
        }

    }
}

@Composable
private fun CompanyInfoSuccessStateComponent(
    companyInfoState: CompanyInfoState,
    historyState: CompanyHistoryState,
) {
    if (!companyInfoState.isLoading &&
        !historyState.isLoading &&
        companyInfoState.error == null &&
        historyState.error == null &&
        companyInfoState.data != null &&
        historyState.data.isNotEmpty()
    ) {

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                        .clip(shape = RectangleShape)
                        .background(color = darkGrey)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Top,
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "Space Exploration Technologies Corp",
                                modifier = Modifier.padding(5.dp),
                                style = TextStyle(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    fontFamily = FontFamily.Serif
                                )
                            )
                            Text(
                                text = companyInfoState.data.summary,
                                modifier = Modifier
                                    .padding(horizontal = 10.dp, vertical = 3.dp),
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 13.sp,
                                ),
                                textAlign = TextAlign.Justify,
                                fontFamily = FontFamily.Serif
                            )
                        }
                    }

                }
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(155.dp)
                ) {
                    Column {
                        DetailsRow(
                            prefix = "CEO",
                            detail = companyInfoState.data.ceo
                        )
                        DetailsRow(
                            prefix = "CTO",
                            detail = companyInfoState.data.cto
                        )
                        DetailsRow(
                            prefix = "CTO Propulsion",
                            detail = companyInfoState.data.ctoPropulsion
                        )
                        DetailsRow(
                            prefix = "COO",
                            detail = companyInfoState.data.coo
                        )
                        DetailsRow(
                            prefix = "Valuation",
                            detail = "$${companyInfoState.data.valuation}"
                        )
                        DetailsRow(
                            prefix = "Location",
                            detail = companyInfoState.data.employees.toString()
                        )
                        DetailsRow(
                            prefix = "Headquarters",
                            detail = companyInfoState.data.headquarters.city
                        )
                    }
                }
            }
            item {
                Text(
                    text = "Historical events",
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                )
            }
            items(historyState.data) { history ->
                HistoryItem(history = history, count = historyState.data.indexOf(history))
            }

        }
    }
}

@Composable
private fun BoxScope.CompanyInfoEmptyStateComponent(
    companyInfoState: CompanyInfoState,
    historyState: CompanyHistoryState,
) {
    if (!companyInfoState.isLoading &&
        !historyState.isLoading &&
        companyInfoState.error == null &&
        historyState.error == null &&
        companyInfoState.data == null &&
        historyState.data.isEmpty()
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .testTag("CompanyInfoEmptyStateComponent"),
            text = "Company info not found"
        )
    }
}

@Composable
private fun BoxScope.CompanyInfoErrorStateComponent(
    companyInfoState: CompanyInfoState,
    historyState: CompanyHistoryState,
) {
    if (!companyInfoState.isLoading &&
        !historyState.isLoading &&
        companyInfoState.error != null &&
        historyState.error != null
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .testTag("CompanyInfoErrorStateComponent"),
            text = companyInfoState.error
        )
    }
}

@Composable
private fun BoxScope.CompanyInfoLoadingStateComponent(
    companyInfoState: CompanyInfoState,
    historyState: CompanyHistoryState,
) {
    if (companyInfoState.isLoading && historyState.isLoading) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .testTag("CompanyInfoLoadingStateComponent")
        )
    }
}

@Composable
fun DetailsRow(
    prefix: String,
    detail: String,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 10.dp)
    ) {
        Text(
            text = prefix,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                fontFamily = FontFamily.Serif
            )
        )
        Text(
            text = detail,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                fontFamily = FontFamily.Serif
            )
        )
    }

}
