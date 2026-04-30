package com.projek.unscollab.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projek.unscollab.R
import com.projek.unscollab.navigation.LocalBackStack
import com.projek.unscollab.navigation.Route

@Composable
fun UNSCollabBottomBar() {
    val backStack = LocalBackStack.current
    val currentRoute = backStack.lastOrNull()

    NavigationBar(
        containerColor = Color(0xFF1FABE1),
        contentColor = Color.White
    ) {
        val items = listOf(
            NavigationItem(Route.Home, R.string.nav_home, R.drawable.icon_home),
            NavigationItem(Route.Activity, R.string.nav_activity, R.drawable.icon_activity),
            NavigationItem(Route.Notification, R.string.nav_notification, R.drawable.icon_notification),
            NavigationItem(Route.Profile, R.string.nav_profile, R.drawable.icon_profile)
        )

        items.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (currentRoute != item.route) {
                        val authRoutes = setOf(Route.Landing, Route.Login, Route.Register)
                        backStack.retainAll { it in authRoutes }
                        backStack.add(item.route)
                    }
                },
                icon = {
                    Image(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = item.labelRes),
                        color = Color.White,
                        fontSize = 10.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.White.copy(alpha = 0.2f),
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    unselectedIconColor = Color.White,
                    unselectedTextColor = Color.White
                )
            )
        }
    }
}

data class NavigationItem(val route: Route, val labelRes: Int, val iconRes: Int)