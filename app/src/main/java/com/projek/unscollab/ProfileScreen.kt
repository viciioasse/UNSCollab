package com.projek.unscollab

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val CyanPrimary = Color(0xFF24A8D8)
val ContentBackground = Color(0xFFFFFFFF)
val ScreenBackground = Color(0xFFF2F4F7)

@Composable
fun ProfileScreen() {
    val scrollState = rememberScrollState()

    val namaUser = "Jordan Hayes"
    val idUser = "U8129034"
    val prodiUser = "Data Science"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBackground)
            .verticalScroll(scrollState)
    ) {
        // --- HEADER SOLID COLOR ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(CyanPrimary)
        ) {
            Text(
                text = "USER PROFILE",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 40.dp)
            )
        }

        // --- PROFILE SECTION ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .offset(y = (-60).dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier
                    .size(120.dp)
                    .border(4.dp, Color.White, CircleShape),
                shape = CircleShape,
                color = Color.White,
                shadowElevation = 4.dp
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_profile),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = namaUser,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
            )
            Text(
                text = "$idUser • $prodiUser",
                fontSize = 15.sp,
                color = Color.Gray
            )

            Button(
                onClick = { },
                modifier = Modifier
                    .padding(top = 20.dp)
                    .height(48.dp)
                    .fillMaxWidth(0.6f),
                colors = ButtonDefaults.buttonColors(containerColor = CyanPrimary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Edit Profile", fontWeight = FontWeight.Bold)
            }
        }

        // --- DASHBOARD DATA (Berdasarkan Atribut Laporan) ---
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .offset(y = (-30).dp)
        ) {
            Text(
                text = "Summary",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Kartu Portfolio
            InfoCard(
                title = "Portfolio",
                items = listOf("E-Commerce Backend", "Portfolio Site", "Data Analytics Tool"),
                icon = Icons.Default.ThumbUp
            )

            // Kartu Pengalaman
            InfoCard(
                title = "Experience",
                items = listOf("Backend Intern", "Open Source Contributor"),
                icon = Icons.Default.Info
            )

            // Kartu Prestasi
            InfoCard(
                title = "Awards",
                items = listOf("Best Researcher 2025", "Hackathon Finalist"),
                icon = Icons.Default.Star
            )

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun InfoCard(title: String, items: List<String>, icon: ImageVector) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ContentBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(CyanPrimary.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = CyanPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = CyanPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                items.forEach { item ->
                    Text(
                        text = "• $item",
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(vertical = 1.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_5")
@Composable
fun ProfilePreview() {
    ProfileScreen()
}