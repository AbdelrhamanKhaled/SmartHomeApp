package com.yassine.smarthome

import android.app.Activity
import java.io.IOException
import java.nio.charset.StandardCharsets


object Utilities {
    fun loadJSONFromAsset(activity: Activity): String? {
        val json: String = try {
            val inputStream = activity.assets.open("contents_commands.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, StandardCharsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}