package dev.robert.spacexgo.core.utils

sealed class UiEvents {
    data class SnackEvent(val message: String): UiEvents()
    data class NavigationEvent(val route: String): UiEvents()
}
