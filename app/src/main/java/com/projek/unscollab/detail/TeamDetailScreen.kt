package com.projek.unscollab.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projek.unscollab.R
import com.projek.unscollab.components.ConfirmationAlertDialog
import com.projek.unscollab.components.SuccessAlertDialog
import com.projek.unscollab.navigation.LocalBackStack

private val PrimaryBlue = Color(0xFF1FABE1)
private val DarkBlue    = Color(0xFF1487B3)
private val LightBlue   = Color(0xFFE8F7FD)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamDetailScreen(
    teamId: String,
    savedItems: androidx.compose.runtime.MutableState<Set<String>> = mutableStateOf(emptySet())
) {
    val backStack = LocalBackStack.current
    var isSaved by remember { mutableStateOf(savedItems.value.contains(teamId)) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }
    var contentVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { contentVisible = true }

    LaunchedEffect(isSaved) {
        savedItems.value = if (isSaved) savedItems.value + teamId
        else savedItems.value - teamId
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Tim", color = Color.White, fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = { backStack.removeLastOrNull() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Kembali", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = { isSaved = !isSaved }) {
                        Icon(
                            imageVector = if (isSaved) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = "Simpan",
                            tint = if (isSaved) Color(0xFFFFC107) else Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryBlue)
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 12.dp,
                color = Color.White
            ) {
                Button(
                    onClick = { showConfirmDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text("Bergabung Tim", fontSize = 16.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.5.sp)
                }
            }
        }
    ) { paddingValues ->
        AnimatedVisibility(
            visible = contentVisible,
            enter = fadeIn() + slideInVertically(initialOffsetY = { 40 })
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFFF5F7FA))
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .background(Brush.verticalGradient(listOf(PrimaryBlue, DarkBlue)))
                ) {
                    Box(
                        modifier = Modifier
                            .size(180.dp)
                            .align(Alignment.TopEnd)
                            .offset(x = 60.dp, y = (-40).dp)
                            .background(Color.White.copy(alpha = 0.07f), CircleShape)
                    )
                    Card(
                        modifier = Modifier
                            .size(90.dp)
                            .align(Alignment.Center)
                            .shadow(8.dp, RoundedCornerShape(20.dp)),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(0.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_uns),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .offset(y = (-20).dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "UNSCollab Squad",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1A1A2E)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Mobile Development",
                            fontSize = 15.sp,
                            color = PrimaryBlue,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Slot Tersedia", fontSize = 12.sp, color = Color.Gray)
                                Text("2 dari 5 posisi", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A2E))
                            }
                            Box(
                                modifier = Modifier
                                    .background(Color(0xFFE8F5E9), RoundedCornerShape(20.dp))
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text("Terbuka", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        LinearProgressIndicator(
                            progress = { 3 / 5f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = PrimaryBlue,
                            trackColor = Color(0xFFEEEEEE)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("3 dari 5 anggota", fontSize = 11.sp, color = Color.Gray)
                    }
                }

                TeamSectionCard(title = "Tentang Tim") {
                    Text(
                        text = "Kami adalah sekelompok tim mahasiswa yang bertujuan untuk mengembangkan sebuah aplikasi dan juga website untuk mempermudah mahasiswa untuk mencari internship dan juga membentuk tim",
                        fontSize = 14.sp,
                        color = Color(0xFF555555),
                        lineHeight = 22.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                TeamSectionCard(title = "Fokus Teknis") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("Kotlin", "Compose", "Android").forEach { tag ->
                            TechChip(tag)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("REST API", "Firebase").forEach { tag ->
                            TechChip(tag)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                TeamSectionCard(title = "Anggota Tim") {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        TeamMemberRow("Ahmad Rahman", "Ketua Tim", "Frontend Developer")
                        TeamMemberRow("Siti Nurhaliza", "Anggota", "UI/UX Designer")
                        TeamMemberRow("Budi Santoso", "Anggota", "Backend Integration")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                TeamSectionCard(title = "Project Terbaru") {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        TeamProjectRow("Aplikasi E-Commerce (Tinari)", "Penjualan online yang dilengkapi fitur pencatatan laba")
                        TeamProjectRow("Aplikasi Chat (E-ChitChat)", "Real-time messaging dengan enkripsi E2E")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    if (showConfirmDialog) {
        ConfirmationAlertDialog(
            title = "Bergabung dengan Tim?",
            message = "Apakah Anda ingin bergabung dengan Mobile Dev Squad? Tim akan meninjau profil Anda terlebih dahulu.",
            onConfirm = {
                showConfirmDialog = false
                showSuccessDialog = true
            },
            onDismiss = { showConfirmDialog = false },
            confirmText = "Bergabung",
            dismissText = "Batal"
        )
    }

    if (showSuccessDialog) {
        SuccessAlertDialog(
            title = "✅ Permintaan Terkirim",
            message = "Permintaan bergabung Anda telah berhasil dikirim ke Mobile Dev Squad. Tim akan menghubungi Anda segera.",
            onDismiss = { showSuccessDialog = false },
            buttonText = "Oke, Mengerti"
        )
    }
}

@Composable
private fun TeamSectionCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A2E))
            Divider(modifier = Modifier.padding(vertical = 10.dp), color = Color(0xFFEEEEEE))
            content()
        }
    }
}

@Composable
private fun TechChip(text: String) {
    Box(
        modifier = Modifier
            .background(LightBlue, RoundedCornerShape(20.dp))
            .padding(horizontal = 14.dp, vertical = 7.dp)
    ) {
        Text(text, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = PrimaryBlue)
    }
}

@Composable
private fun TeamMemberRow(name: String, role: String, specialty: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(PrimaryBlue, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(name.first().toString(), fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A2E))
            Text(role, fontSize = 12.sp, color = Color.Gray)
            Text(specialty, fontSize = 11.sp, color = PrimaryBlue, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun TeamProjectRow(title: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8F8F8), RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(LightBlue, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("📱", fontSize = 20.sp)
        }
        Column {
            Text(title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A2E))
            Text(description, fontSize = 12.sp, color = Color.Gray, lineHeight = 16.sp)
        }
    }
}