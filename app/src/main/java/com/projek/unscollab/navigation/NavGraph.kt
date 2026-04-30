package com.projek.unscollab.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.projek.unscollab.ProfileScreen
import com.projek.unscollab.activity.ActivityScreen
import com.projek.unscollab.activity.InternshipItem
import com.projek.unscollab.activity.StatusAplikasi
import com.projek.unscollab.auth.login.LoginScreen
import com.projek.unscollab.auth.register.RegisterScreen
import com.projek.unscollab.components.UNSCollabBottomBar
import com.projek.unscollab.detail.JobDetailScreen
import com.projek.unscollab.detail.TeamDetailScreen
import com.projek.unscollab.home.HomeScreen
import com.projek.unscollab.landing.LandingScreen
import com.projek.unscollab.notification.NotificationScreen

@Composable
fun NavGraph() {
    val backStack = remember { mutableStateListOf<Route>(Route.Landing) }
    val savedItems = remember { mutableStateOf(setOf<String>()) }

    val internshipList = remember {
        mutableStateListOf(
            InternshipItem("Frontend Developer Intern", "Tech Startup Indonesia", "Jakarta (Remote)", "12 April 2026", StatusAplikasi.MENUNGGU),
            InternshipItem("Data Science Intern",       "AirCroft Company",       "Surabaya",         "10 April 2026", StatusAplikasi.DITERIMA),
            InternshipItem("UI/UX Designer Intern",     "Creative Agency",        "Bandung",          "8 April 2026",  StatusAplikasi.DITOLAK),
            InternshipItem("Backend Developer Intern",  "Shopee",                 "Solo",             "5 April 2026",  StatusAplikasi.MENUNGGU),
        )
    }

    CompositionLocalProvider(LocalBackStack provides backStack) {
        val currentRoute = backStack.lastOrNull() ?: Route.Landing

        val isDetailScreen = currentRoute is Route.JobDetail || currentRoute is Route.TeamDetail
        val isAuthScreen = currentRoute is Route.Landing
                || currentRoute is Route.Login
                || currentRoute is Route.Register

        Scaffold(
            bottomBar = {
                if (!isDetailScreen && !isAuthScreen) {
                    UNSCollabBottomBar()
                }
            }
        ) { paddingValues ->
            when (currentRoute) {
                is Route.Landing    -> LandingScreen()
                is Route.Login      -> LoginScreen()
                is Route.Register   -> RegisterScreen()
                is Route.Home       -> HomeScreen(
                    modifier = Modifier.padding(paddingValues),
                    savedItems = savedItems.value
                )
                is Route.Activity     -> ActivityScreen(
                    internshipList = internshipList,
                    modifier = Modifier.padding(paddingValues)
                )
                is Route.Notification -> NotificationScreen(
                    modifier = Modifier.padding(paddingValues)
                )
                is Route.Profile      -> ProfileScreen(
                    modifier = Modifier.padding(paddingValues)
                )
                is Route.JobDetail  -> JobDetailScreen(
                    jobId = currentRoute.jobId,
                    savedItems = savedItems,
                    onApply = { newItem -> internshipList.add(newItem) }
                )
                is Route.TeamDetail -> TeamDetailScreen(
                    teamId = currentRoute.teamId,
                    savedItems = savedItems
                )
            }
        }
    }
}