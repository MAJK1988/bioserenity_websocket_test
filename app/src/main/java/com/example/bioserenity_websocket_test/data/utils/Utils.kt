package com.example.bioserenity_websocket_test.data.utils

import java.net.URI
import java.net.URISyntaxException

class Utils {

    companion object {
    fun isValidUrl(url: String): Boolean {
        return try {
            val uri = URI(url)
            uri.isAbsolute && (uri.scheme == "ws")
        } catch (e: URISyntaxException) {
            false
        }
    }
    }
}