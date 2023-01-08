package dev.robert.spacexgo.core.utils

import kotlinx.coroutines.flow.Flow



inline fun <T> safeCall(action: () -> Resource<T>): Resource<T> {
    return try {
        action()
    } catch (e: Exception) {
        Resource.Error(e.message ?: "Unknown Error Occurred")
    }
}