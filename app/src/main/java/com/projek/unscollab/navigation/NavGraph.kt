package com.projek.unscollab.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.projek.unscollab.ProfileScreen
import com.projek.unscollab.activity.ActivityScreen
import com.projek.unscollab.auth.login.LoginScreen
import com.projek.unscollab.auth.register.RegisterScreen
import com.projek.unscollab.components.UNSCollabBottomBar
import com.projek.unscollab.home.HomeScreen
import com.projek.unscollab.landing.LandingScreen
import com.projek.unscollab.notification.NotificationScreen

@Composable
fun NavGraph() {
    val backStack = remember { mutableStateListOf<Route>(Route.Landing) }

    CompositionLocalProvider(LocalBackStack provides backStack) {
        val currentRoute = backStack.lastOrNull() ?: Route.Landing

        Scaffold(
            bottomBar = {
                if (currentRoute !is Route.Landing && currentRoute !is Route.Login && currentRoute !is Route.Register) {
                    UNSCollabBottomBar()
                }
            }
        ) { paddingValues ->
            val modifier = Modifier.padding(paddingValues)
            
            when (currentRoute) {
                is Route.Landing -> LandingScreen()
                is Route.Login -> LoginScreen()
                is Route.Register -> RegisterScreen()
                is Route.Home -> HomeScreen(modifier)
                is Route.Activity -> ActivityScreen()
                is Route.Notification -> NotificationScreen()
                is Route.Profile -> ProfileScreen()
                is Route.Detail -> {
                    DetailScreen(id = currentRoute.id)
                }
            }
        }
    }
}

@Composable
fun DetailScreen(id: String) {
    val backStack = LocalBackStack.current
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Detail Lowongan", style = MaterialTheme.typography.headlineMedium)
            Text("ID Lowongan: $id", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { backStack.removeLastOrNull() }) {
                Text("Kembali")
            }
        }
    }
}
