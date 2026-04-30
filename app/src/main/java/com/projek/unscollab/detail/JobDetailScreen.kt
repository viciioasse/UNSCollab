package com.projek.unscollab.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
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
private val DarkBlue = Color(0xFF1487B3)
private val LightBlue = Color(0xFFE8F7FD)
private val GreenAccent = Color(0xFF2E7D32)
private val BackgroundGray = Color(0xFFF5F7FA)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDetailScreen(
    jobId: String,
    savedItems: androidx.compose.runtime.MutableState<Set<String>> = mutableStateOf(emptySet())
) {
    val backStack = LocalBackStack.current
    var isSaved by remember { mutableStateOf(savedItems.value.contains(jobId)) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showShareDialog by remember { mutableStateOf(false) }
    var contentVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { contentVisible = true }

    LaunchedEffect(isSaved) {
        savedItems.value = if (isSaved) savedItems.value + jobId
        else savedItems.value - jobId
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Lowongan", color = Color.White, fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = { backStack.removeLastOrNull() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Kembali", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = { showShareDialog = true }) {
                        Icon(Icons.Filled.Share, contentDescription = "Bagikan", tint = Color.White)
                    }
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
                    onClick = { showSuccessDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(
                        "Lamar Sekarang",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
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
                    .background(BackgroundGray)
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(PrimaryBlue, DarkBlue)
                            )
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .size(180.dp)
                            .align(Alignment.TopEnd)
                            .offset(x = 60.dp, y = (-40).dp)
                            .background(Color.White.copy(alpha = 0.07f), CircleShape)
                    )
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .align(Alignment.BottomStart)
                            .offset(x = (-30).dp, y = 30.dp)
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
                            text = "Android Developer",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1A1A2E)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "PT. Tech Indonesia",
                            fontSize = 15.sp,
                            color = PrimaryBlue,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            StatusBadge(text = "Magang", backgroundColor = LightBlue, textColor = PrimaryBlue)
                            StatusBadge(text = "Aktif", backgroundColor = Color(0xFFE8F5E9), textColor = GreenAccent)
                            StatusBadge(text = "On-Site", backgroundColor = Color(0xFFFFF8E1), textColor = Color(0xFFF57F17))
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    JobInfoChip(icon = "📍", label = "Lokasi", value = "Jakarta")
                    JobInfoChip(icon = "⏱️", label = "Durasi", value = "3–6 Bulan")
                    JobInfoChip(icon = "💰", label = "Gaji", value = "Rp 3-5 Juta")
                }

                Spacer(modifier = Modifier.height(20.dp))

                SectionCard(title = "Deskripsi Pekerjaan") {
                    Text(
                        text = "Kami sedang mencari Android Developer yang berpengalaman untuk mengembangkan aplikasi mobile terbaru kami. Anda akan bekerja bersama tim yang berdedikasi menggunakan teknologi terkini dalam ekosistem Android.",
                        fontSize = 14.sp,
                        color = Color(0xFF555555),
                        lineHeight = 22.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                SectionCard(title = "Persyaratan") {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        RequirementRow("Mahasiswa/i semester 6/7 Informatika, Teknik Industri, Sains Data, atau jurusan terkait ")
                        RequirementRow("Minimal 1 tahun pengalaman sebagai Android Developer")
                        RequirementRow("Menguasai Kotlin dan Jetpack Compose")
                        RequirementRow("Pemahaman tentang REST API dan database lokal")
                        RequirementRow("Komunikasi yang baik dan mampu bekerja dalam tim")
                        RequirementRow("Bersedia bekerja dari kantor (Jakarta)")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                SectionCard(title = "Keuntungan") {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        BenefitRow("💵", "Gaji kompetitif sesuai pengalaman")
                        BenefitRow("🏥", "Jaminan kesehatan BPJS Kesehatan & Ketenagakerjaan")
                        BenefitRow("📚", "Training rutin & pengembangan skill")
                        BenefitRow("🎯", "Peluang menjadi karyawan tetap")
                        BenefitRow("☕", "Fasilitas kantor lengkap & snack gratis")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = { showShareDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(48.dp),
                    border = BorderStroke(1.5.dp, PrimaryBlue),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(
                        Icons.Filled.Share,
                        contentDescription = null,
                        tint = PrimaryBlue,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Bagikan Lowongan", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = PrimaryBlue)
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    if (showSuccessDialog) {
        SuccessAlertDialog(
            title = "✅ Lamaran Terkirim!",
            message = "Lamaran Anda untuk posisi Android Developer di PT. Tech Indonesia telah berhasil dikirim. Kami akan menghubungi Anda dalam 2–3 hari kerja.",
            onDismiss = { showSuccessDialog = false },
            buttonText = "Oke, Mengerti"
        )
    }

    if (showShareDialog) {
        ConfirmationAlertDialog(
            title = "Bagikan Lowongan",
            message = "Apakah Anda ingin membagikan lowongan Android Developer ini ke media sosial?",
            onConfirm = { showShareDialog = false },
            onDismiss = { showShareDialog = false },
            confirmText = "Bagikan",
            dismissText = "Batal"
        )
    }
}

@Composable
private fun StatusBadge(text: String, backgroundColor: Color, textColor: Color) {
    Box(
        modifier = Modifier
            .background(backgroundColor, RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp, vertical = 5.dp)
    ) {
        Text(text = text, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = textColor)
    }
}

@Composable
private fun JobInfoChip(icon: String, label: String, value: String) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(icon, fontSize = 22.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(label, fontSize = 10.sp, color = Color.Gray)
            Text(value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A2E))
        }
    }
}

@Composable
private fun SectionCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A2E)
            )
            Divider(
                modifier = Modifier.padding(vertical = 10.dp),
                color = Color(0xFFEEEEEE)
            )
            content()
        }
    }
}

@Composable
private fun RequirementRow(text: String) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(Color(0xFFE8F5E9), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text("✓", fontSize = 11.sp, color = GreenAccent, fontWeight = FontWeight.Bold)
        }
        Text(text, fontSize = 13.sp, color = Color(0xFF555555), lineHeight = 18.sp)
    }
}

@Composable
private fun BenefitRow(icon: String, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(LightBlue, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(icon, fontSize = 18.sp)
        }
        Text(text, fontSize = 13.sp, color = Color(0xFF555555), lineHeight = 18.sp)
    }
}