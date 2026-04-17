package com.projek.unscollab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.projek.unscollab.home.HomeScreen
import com.projek.unscollab.navigation.NavGraph
import com.projek.unscollab.ui.theme.UNSCollabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UNSCollabTheme {
                NavGraph()
            }
        }
    }
}
