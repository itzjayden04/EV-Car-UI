
package com.example.evcarui

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    // Button states
    private var engineOn = false
    private var trunkOpen = false
    private var alarmOn = false
    private var vehicleLocked = false

    // Button colours
    private val purple = Color.rgb(48, 38, 185)
    private val white = Color.WHITE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Navigation Drawer
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)

        // Hamburger Menu Button
        val btnMenu = findViewById<ImageButton>(R.id.btnMenu)

        btnMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Navigation Menu
        val navigationView =
            findViewById<NavigationView>(R.id.navigationView)

        navigationView.setNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId) {

                R.id.nav_dashboard -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                }

                R.id.nav_activity -> {
                    val intent = Intent(
                        this,
                        ActivityLogsActivity::class.java
                    )

                    startActivity(intent)
                    drawerLayout.closeDrawer(GravityCompat.START)
                }

                R.id.nav_settings -> {
                    Toast.makeText(
                        this,
                        "Settings coming soon!",
                        Toast.LENGTH_SHORT
                    ).show()

                    drawerLayout.closeDrawer(GravityCompat.START)
                }

                R.id.nav_about -> {
                    Toast.makeText(
                        this,
                        "EV Car UI - Smart Vehicle Dashboard",
                        Toast.LENGTH_SHORT
                    ).show()

                    drawerLayout.closeDrawer(GravityCompat.START)
                }
            }

            true
        }

        // Handle system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->

            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        // Find the four buttons
        val btnEngine = findViewById<MaterialButton>(R.id.btnEngine)
        val btnTrunk = findViewById<MaterialButton>(R.id.btnTrunk)
        val btnAlarm = findViewById<MaterialButton>(R.id.btnAlarm)
        val btnLock = findViewById<MaterialButton>(R.id.btnLock)

        // Engine Button
        btnEngine.setOnClickListener {
            engineOn = !engineOn
            updateButtonAppearance(btnEngine, engineOn)

            val message =
                if (engineOn) "Engine activated" else "Engine deactivated"

            ActivityLogRepository.addLog(
                context = this,
                icon = "⚡",
                activityName = message,
                activityStatus = if (engineOn) "Activated" else "Deactivated"
            )

            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        // Trunk Button
        btnTrunk.setOnClickListener {
            trunkOpen = !trunkOpen
            updateButtonAppearance(btnTrunk, trunkOpen)

            val message =
                if (trunkOpen) "Trunk opened" else "Trunk closed"

            ActivityLogRepository.addLog(
                context = this,
                icon = "🚗",
                activityName = message,
                activityStatus = if (trunkOpen) "Opened" else "Closed"
            )

            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        // Alarm Button
        btnAlarm.setOnClickListener {
            alarmOn = !alarmOn
            updateButtonAppearance(btnAlarm, alarmOn)

            val message =
                if (alarmOn) "Alarm activated" else "Alarm deactivated"

            ActivityLogRepository.addLog(
                context = this,
                icon = "🔔",
                activityName = message,
                activityStatus = if (alarmOn) "Activated" else "Deactivated"
            )

            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        // Lock Button
        btnLock.setOnClickListener {
            vehicleLocked = !vehicleLocked
            updateButtonAppearance(btnLock, vehicleLocked)

            val message =
                if (vehicleLocked) "Vehicle locked" else "Vehicle unlocked"

            ActivityLogRepository.addLog(
                context = this,
                icon = if (vehicleLocked) "🔒" else "🔓",
                activityName = message,
                activityStatus = if (vehicleLocked) "Locked" else "Unlocked"
            )

            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

    } // End of onCreate()

    // Update button appearance according to its state
    private fun updateButtonAppearance(
        button: MaterialButton,
        isActive: Boolean
    ) {
        val backgroundColor = if (isActive) purple else white
        val contentColor = if (isActive) white else purple

        button.backgroundTintList =
            ColorStateList.valueOf(backgroundColor)

        button.iconTint =
            ColorStateList.valueOf(contentColor)

        button.setTextColor(contentColor)
    }

} // End of MainActivity
