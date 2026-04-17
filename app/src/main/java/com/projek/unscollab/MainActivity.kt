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
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

    var currentScreen by remember { mutableStateOf("home") }

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
    NavigationBar{
        NavigationBarItem(
            selected = currentScreen == "home",
            onClick = { onNavigate("home") },
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("Home") }
        )

        NavigationBarItem(
            selected = currentScreen == "activity",
            onClick = { onNavigate("activity") },
            icon = { Icon(Icons.Default.List, contentDescription = null)},
            label = { Text("Activity") }
        )

        NavigationBarItem(
            selected = currentScreen == "notification",
            onClick = { onNavigate("notification") },
            icon = { Icon(Icons.Default.Notifications, contentDescription = null)},
            label = { Text("Notification") }
        )

        NavigationBarItem(
            selected = currentScreen == "profile",
            onClick = { onNavigate("profile") },
            icon = { Icon(Icons.Default.AccountCircle, contentDescription = null) },
            label = {Text("Profile")}
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
fun NotificationScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Notif Screen")
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
