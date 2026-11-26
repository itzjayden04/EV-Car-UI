
package com.example.evcarui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import com.google.android.material.button.MaterialButton

class ActivityLogsActivity : AppCompatActivity() {

    private lateinit var rvActivityLogs: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logs)

        // Find RecyclerView
        rvActivityLogs = findViewById(R.id.rvActivityLogs)

        // Set RecyclerView layout manager
        rvActivityLogs.layoutManager = LinearLayoutManager(this)

        // Find empty-state message
        val tvEmptyLogs = findViewById<TextView>(R.id.tvEmptyLogs)

        // Retrieve records from SharedPreferences
        val activityList = ActivityLogRepository.getLogs(this)

        // Check whether activity records exist
        if (activityList.isEmpty()) {

            rvActivityLogs.visibility = View.GONE
            tvEmptyLogs.visibility = View.VISIBLE

        } else {

            rvActivityLogs.visibility = View.VISIBLE
            tvEmptyLogs.visibility = View.GONE

        }

        // Set up RecyclerView adapter
        val adapter = ActivityLogAdapter(activityList)
        rvActivityLogs.adapter = adapter

        // Find Clear Logs button
        val btnClearLogs = findViewById<MaterialButton>(R.id.btnClearLogs)

        // Clear all saved activity records
        btnClearLogs.setOnClickListener {

            // Delete records from SharedPreferences
            ActivityLogRepository.clearLogs(this)

            // Clear RecyclerView
            rvActivityLogs.adapter = ActivityLogAdapter(emptyList())

            // Hide RecyclerView
            rvActivityLogs.visibility = View.GONE

            // Display empty-state message
            tvEmptyLogs.visibility = View.VISIBLE

            // Show confirmation
            Toast.makeText(
                this,
                "All activity logs cleared",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Back button
        val btnBack = findViewById<TextView>(R.id.btnBack)

        btnBack.setOnClickListener {
            finish()
        }
    }
}
