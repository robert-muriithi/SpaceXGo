package dev.robert.spacexgo.features.splash

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.robert.spacexgo.R
import dev.robert.spacexgo.core.presentation.theme.darkGrey
import dev.robert.spacexgo.features.destinations.ShipsScreenDestination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination(start = true)
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator
) {
    val scaffoldState = rememberScaffoldState()
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.rocket_loader))

    LaunchedEffect(key1 = true) {
        withContext(Dispatchers.Main) {

            delay(6000)
            navigator.popBackStack()
            navigator.navigate(ShipsScreenDestination)
        }
    }

    Scaffold(scaffoldState = scaffoldState) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(darkGrey)){
            LottieAnimation(
                composition = composition,
                iterations = Int.MAX_VALUE,
                modifier = Modifier.align(alignment = Alignment.Center)
            )

        }
    }


}