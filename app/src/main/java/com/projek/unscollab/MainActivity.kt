package com.projek.unscollab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.projek.unscollab.ui.NotificationScreen
import com.projek.unscollab.ui.theme.UNSCollabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UNSCollabTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {

    var currentScreen by remember { mutableStateOf("notification") }

    Scaffold(
        bottomBar = {
            BottomBar(
                currentScreen = currentScreen,
                onNavigate = { currentScreen = it }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            when (currentScreen) {
                "home" -> HomeScreen()
                "activity" -> ActivityScreen()
                "notification" -> NotificationScreen()
                "profile" -> ProfileScreen()
            }
        }
    }
}


@Composable
fun BottomBar(
    currentScreen: String,
    onNavigate: (String) -> Unit
) {
    NavigationBar(
        containerColor = Color(0xFF1FABE1) // Sidebar background color updated
    ) {
        NavigationBarItem(
            selected = currentScreen == "home",
            onClick = { onNavigate("home") },
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("Home") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                selectedTextColor = Color.Black,
                unselectedIconColor = Color.DarkGray,
                unselectedTextColor = Color.DarkGray,
                indicatorColor = Color.White.copy(alpha = 0.3f)
            )
        )

        NavigationBarItem(
            selected = currentScreen == "activity",
            onClick = { onNavigate("activity") },
            icon = { Icon(Icons.Default.List, contentDescription = null)},
            label = { Text("Activity") },
             colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                selectedTextColor = Color.Black,
                unselectedIconColor = Color.DarkGray,
                unselectedTextColor = Color.DarkGray,
                indicatorColor = Color.White.copy(alpha = 0.3f)
            )
        )

        NavigationBarItem(
            selected = currentScreen == "notification",
            onClick = { onNavigate("notification") },
            icon = { Icon(Icons.Default.Notifications, contentDescription = null)},
            label = { Text("Notification") },
             colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                selectedTextColor = Color.Black,
                unselectedIconColor = Color.DarkGray,
                unselectedTextColor = Color.DarkGray,
                indicatorColor = Color.White.copy(alpha = 0.3f)
            )
        )

        NavigationBarItem(
            selected = currentScreen == "profile",
            onClick = { onNavigate("profile") },
            icon = { Icon(Icons.Default.AccountCircle, contentDescription = null) },
            label = { Text("Profile") },
             colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                selectedTextColor = Color.Black,
                unselectedIconColor = Color.DarkGray,
                unselectedTextColor = Color.DarkGray,
                indicatorColor = Color.White.copy(alpha = 0.3f)
            )
        )
    }
}

@Composable
fun HomeScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Home Screen")
    }
}

@Composable
fun ActivityScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Activity Screen")
    }
}

@Composable
fun ProfileScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Profile Screen")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
    UNSCollabTheme {
        MainScreen()
    }
}