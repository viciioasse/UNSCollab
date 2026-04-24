package com.projek.unscollab.navigation

import kotlinx.serialization.Serializable

sealed class Route {
    @Serializable
    data object Landing : Route()

    @Serializable
    data object Login : Route()
    
    @Serializable
    data object Register : Route()
    
    @Serializable
    data object Home : Route()
    
    @Serializable
    data object Activity : Route()
    
    @Serializable
    data object Notification : Route()
    
    @Serializable
    data object Profile : Route()

    @Serializable
    data class Detail(val id: String) : Route()
}
