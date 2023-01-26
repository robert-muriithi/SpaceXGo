package dev.robert.spacexgo.core.utils

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import dev.robert.spacexgo.SpaceXApp
import kotlinx.coroutines.flow.Flow



inline fun <T> safeCall(action: () -> Resource<T>): Resource<T> {
    return try {
        action()
    } catch (e: Exception) {
        Resource.Error(e.message ?: "Unknown Error Occurred")
    }
}

fun isConnectionAvailable(): Boolean {
  return true
}