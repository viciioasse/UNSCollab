package com.projek.unscollab.landing

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projek.unscollab.R
import com.projek.unscollab.navigation.LocalBackStack
import com.projek.unscollab.navigation.Route

@Composable
fun LandingScreen() {
    val backStack = LocalBackStack.current
    val poppinsBlack = FontFamily(Font(R.font.poppins_black))
    val poppinsLight = FontFamily(Font(R.font.poppins_light))
    val poppinsBold = FontFamily(Font(R.font.poppins_bold))
    val poppinsMedium = FontFamily(Font(R.font.poppins_medium))

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.6f)))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_unscollab),
                contentDescription = null,
                modifier = Modifier.height(250.dp).padding(bottom = 24.dp)
            )

            Text(
                text = "Hello",
                fontSize = 32.sp,
                fontFamily = poppinsBlack,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Welcome to UNSCollab, where you can find internships and teammates",
                fontSize = 16.sp,
                fontFamily = poppinsLight,
                color = Color(0xFFA0A0A0),
                textAlign = TextAlign.Center,
                modifier = Modifier.width(300.dp).padding(bottom = 32.dp)
            )

            Button(
                onClick = { backStack.add(Route.Login) },
                modifier = Modifier.width(250.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1FABE1))
            ) {
                Text("Login", fontFamily = poppinsBold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { backStack.add(Route.Register) },
                modifier = Modifier.width(250.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFF1FABE1))
            ) {
                Text("Sign Up", fontFamily = poppinsBold)
            }

            Spacer(modifier = Modifier.height(64.dp))

            Text("Sign up using", color = Color(0xFFA0A0A0), fontFamily = poppinsMedium)

            Spacer(modifier = Modifier.height(8.dp))

            androidx.compose.material3.OutlinedButton(
                onClick = { },
                modifier = Modifier.wrapContentWidth(),
                border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFF1FABE1)),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = R.drawable.logo_google), contentDescription = null, modifier = Modifier.size(24.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Continue with Google", fontFamily = poppinsMedium)
                }
            }
        }
    }
}