package com.projek.unscollab.navigation

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.snapshots.SnapshotStateList

val LocalBackStack = compositionLocalOf<SnapshotStateList<Route>> {
    error("No BackStack provided")
}
