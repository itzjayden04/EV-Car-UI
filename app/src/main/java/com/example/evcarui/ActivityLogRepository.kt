
package com.example.evcarui

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ActivityLogRepository {

    // SharedPreferences configuration
    private const val PREF_NAME = "EV_CAR_ACTIVITY_LOGS"
    private const val KEY_ACTIVITY_LOGS = "activity_logs"

    // Save a new activity record
    fun addLog(
        context: Context,
        icon: String,
        activityName: String,
        activityStatus: String
    ) {

        // Retrieve existing records
        val logs = getLogs(context).toMutableList()

        // Generate current timestamp
        val currentTime = SimpleDateFormat(
            "dd MMM yyyy, hh:mm a",
            Locale.getDefault()
        ).format(Date())

        // Create new activity record
        val newLog = ActivityLog(
            icon = icon,
            activityName = activityName,
            activityTime = currentTime,
            activityStatus = activityStatus
        )

        // Add newest activity to the beginning
        logs.add(0, newLog)

        // Save updated records
        saveLogs(context, logs)
    }

    // Retrieve previously saved activity records
    fun getLogs(context: Context): List<ActivityLog> {

        val preferences = context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )

        val jsonString = preferences.getString(
            KEY_ACTIVITY_LOGS,
            null
        ) ?: return emptyList()

        return try {

            val jsonArray = JSONArray(jsonString)
            val logs = mutableListOf<ActivityLog>()

            for (i in 0 until jsonArray.length()) {

                val item = jsonArray.getJSONObject(i)

                val activity = ActivityLog(
                    icon = item.optString("icon"),
                    activityName = item.optString("activityName"),
                    activityTime = item.optString("activityTime"),
                    activityStatus = item.optString("activityStatus")
                )

                logs.add(activity)
            }

            logs

        } catch (e: Exception) {
            emptyList()
        }
    }

    // Save activity records as JSON
    private fun saveLogs(
        context: Context,
        logs: List<ActivityLog>
    ) {

        val jsonArray = JSONArray()

        for (log in logs) {

            val jsonObject = JSONObject()

            jsonObject.put("icon", log.icon)
            jsonObject.put("activityName", log.activityName)
            jsonObject.put("activityTime", log.activityTime)
            jsonObject.put("activityStatus", log.activityStatus)

            jsonArray.put(jsonObject)
        }

        val preferences = context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )

        preferences.edit()
            .putString(KEY_ACTIVITY_LOGS, jsonArray.toString())
            .apply()
    }

    // Clear all saved activity records
    fun clearLogs(context: Context) {

        val preferences = context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )

        preferences.edit()
            .remove(KEY_ACTIVITY_LOGS)
            .apply()
    }
}
