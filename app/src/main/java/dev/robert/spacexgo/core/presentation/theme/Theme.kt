package dev.robert.spacexgo.core.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColorScheme(
    primary = darkGrey,
    primaryContainer = darkGrey,
    secondary = darkGrey
)

private val LightColorPalette = lightColorScheme(
    primary = darkGrey,
    primaryContainer = darkGrey,
    secondary = darkGrey

)

@Composable
fun SpaceXGoTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = if (darkTheme) darkGrey else darkGrey
        )
    }

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}