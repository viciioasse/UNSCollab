package com.projek.unscollab.navigation

import androidx.compose.foundation.layout.padding
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.projek.unscollab.components.UNSCollabBottomBar
import com.projek.unscollab.home.HomeScreen
import com.projek.unscollab.activity.ActivityScreen
import com.projek.unscollab.notification.NotificationScreen
import com.projek.unscollab.ProfileScreen


@Composable
fun NavGraph() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            UNSCollabBottomBar(navController)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") {
                HomeScreen()
            }

            composable("activity") {
                ActivityScreen()
            }

            composable("notification") {
                NotificationScreen()
            }

            composable("profile") {
                ProfileScreen()
            }
        }
    }
}
