package dev.robert.spacexgo.features.ships.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.fonts.FontStyle
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.robert.spacexgo.R
import dev.robert.spacexgo.core.presentation.theme.darkBlue
import dev.robert.spacexgo.core.presentation.theme.lightBlue
import dev.robert.spacexgo.features.ships.domain.model.Ship


@Composable
@Destination
fun ShipDetailsScreen(
    navigator: DestinationsNavigator,
    shipData: Ship
) {
    val scaffoldState = rememberScaffoldState()
    ShipDetailScreenContent(shipData = shipData, scaffoldState = scaffoldState, navigator = navigator)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ShipDetailScreenContent(shipData: Ship, scaffoldState: ScaffoldState, navigator: DestinationsNavigator) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                ShipDetailTopBar(shipData = shipData)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .background(color = Color.White)
                            .clip(
                                RoundedCornerShape(10.dp)
                            )
                            .size(30.dp)
                            .clickable {
                                navigator.navigateUp()
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    ) {
        LazyColumn {
            item {
                ShipDetailContent(shipData = shipData)
            }
            item {
                ShipRolesContent(shipData = shipData)
            }
        }
    }

}

@Composable
fun ShipRolesContent(shipData: Ship) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        border = BorderStroke(1.5.dp, color = Color.Gray),
        shape = MaterialTheme.shapes.medium,
        elevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "Roles",
                    modifier = Modifier
                        .padding(5.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Serif
                    )
                )
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Divider(
                    color = Color.Gray,
                    thickness = 0.3.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 50.dp)
                )
            }
            shipData.roles.forEach {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                            .clip(CircleShape)
                            .background(color = darkBlue)
                            .padding(2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .width(17.dp)
                                .height(17.dp)
                                .clip(CircleShape)
                                .background(color = lightBlue)
                                .padding(2.dp)
                        ) {
                            Text(
                                text = it!!.substring(0, 1),
                                modifier = Modifier
                                    .align(Alignment.Center),
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 7.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Serif
                                )
                            )
                        }

                    }
                    Text(
                        text = it ?: "",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Serif,
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun ShipDetailTopBar(shipData: Ship) {
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(
                    data = shipData.image
                )
                .apply(block = fun ImageRequest.Builder.() {
                    crossfade(true)

                }).build()
        ),
        modifier = Modifier
            .fillMaxSize()
            .clip(shape = RectangleShape),
        contentScale = ContentScale.Crop,
        contentDescription = null
    )
}

@Composable
fun ShipDetailContent(shipData: Ship) {
    val url = shipData.link ?: "www.spacex.com"
    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(url)) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable {
                context.startActivity(intent)
            },
        border = BorderStroke(1.5.dp, color = Color.Gray),
        shape = MaterialTheme.shapes.medium,
        elevation = 1.dp
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "Ship info",
                    modifier = Modifier
                        .padding(5.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Serif
                    )
                )
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Divider(
                    color = Color.Gray,
                    thickness = 0.3.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 50.dp)
                )
            }
            InfoRows(label = "Name", value = shipData.name)
            InfoRows(label = "Model", value = shipData.model ?: "Unknown")
            InfoRows(label = "Year built", value = shipData.yearBuilt.toString())
            InfoRows(label = "Legacy", value = shipData.legacyId ?: "Unknown")
            InfoRows(label = "Home port", value = shipData.homePort ?: "Unknown")
            InfoRows(label = "Mass", value = "${shipData.massKg} kgs")
            InfoRows(
                label = "Speed (Kn)",
                value = if (shipData.speedKn != null) shipData.speedKn.toString() + " Kn" else "Unknown"
            )
        }
    }
}


@Composable
fun InfoRows(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold
            )
        )
        Text(text = value, style = TextStyle(fontSize = 12.sp, fontFamily = FontFamily.Serif))
    }
}



