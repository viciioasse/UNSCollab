package com.projek.unscollab.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projek.unscollab.ui.theme.UNSCollabTheme
import com.projek.unscollab.components.DeleteConfirmationDialog
import com.projek.unscollab.components.SuccessDialog

val PrimaryBlue   = Color(0xFF1FABE1)
val LightBlue     = Color(0xFFEEF2FF)
val StatusPending = Color(0xFFF59E0B)
val StatusAccepted= Color(0xFF10B981)
val StatusRejected= Color(0xFFEF4444)
val CardBg        = Color(0xFFFFFFFF)
val PageBg        = Color(0xFFF5F7FA)

enum class StatusAplikasi { MENUNGGU, DITERIMA, DITOLAK }

data class InternshipItem(
    val title: String,
    val company: String,
    val location: String,
    val appliedDate: String,
    val status: StatusAplikasi
)

data class TeamItem(
    val projectName: String,
    val position: String,
    val category: String,
    val requestedDate: String,
    val status: StatusAplikasi,
    val successMessage: String? = null
)

val sampleInternships = listOf(
    InternshipItem("Frontend Developer Intern", "Tech Startup Indonesia", "Jakarta (Remote)", "12 April 2026", StatusAplikasi.MENUNGGU),
    InternshipItem("Data Science Intern",        "AirCroft Company",        "Surabaya",         "10 April 2026", StatusAplikasi.DITERIMA),
    InternshipItem("UI/UX Designer Intern",      "Creative Agency",        "Bandung",          "8 April 2026",  StatusAplikasi.DITOLAK),
    InternshipItem("Backend Developer Intern",   "Shopee",   "Solo",           "5 April 2026",  StatusAplikasi.MENUNGGU),
)

val sampleTeams = listOf(
    TeamItem("FATISDA Development", "Mobile Developer", "Mobile App", "14 April 2026",
        StatusAplikasi.DITERIMA, "Selamat! Kamu sekarang bagian dari tim FATISDA DEV."),
    TeamItem("PKM KI",       "Programmer",     "Research",   "11 April 2026",
        StatusAplikasi.MENUNGGU),
    TeamItem("Radio Campus 00.00",    "Penyiar",   "Broadcasting",      "9 April 2026",
        StatusAplikasi.DITOLAK),
)

@Composable
fun StatusChip(status: StatusAplikasi) {
    val (color, label, icon) = when (status) {
        StatusAplikasi.MENUNGGU -> Triple(StatusPending,  "Menunggu", "⏳")
        StatusAplikasi.DITERIMA -> Triple(StatusAccepted, "Diterima", "✅")
        StatusAplikasi.DITOLAK  -> Triple(StatusRejected, "Ditolak",  "❌")
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(color.copy(alpha = 0.12f))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(icon, fontSize = 12.sp)
        Spacer(Modifier.width(4.dp))
        Text(label, color = color, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun SummaryBar(menunggu: Int, diterima: Int, ditolak: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(PrimaryBlue)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        SummaryItem(menunggu, "Menunggu")
        VerticalDivider()
        SummaryItem(diterima, "Diterima")
        VerticalDivider()
        SummaryItem(ditolak, "Ditolak")
    }
}

@Composable
private fun VerticalDivider() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(48.dp)
            .background(Color.White.copy(alpha = 0.3f))
    )
}

@Composable
private fun SummaryItem(count: Int, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = count.toString(),
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Text(text = label, color = Color.White.copy(alpha = 0.85f), fontSize = 12.sp)
    }
}

@Composable
fun InternshipCard(
    item: InternshipItem,
    onDelete: () -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(LightBlue),
                contentAlignment = Alignment.Center
            ) {
                Text("💼", fontSize = 22.sp)
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        item.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.width(8.dp))
                    StatusChip(item.status)
                }

                Spacer(Modifier.height(4.dp))
                Text(item.company, color = Color.Gray, fontSize = 13.sp)

                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null,
                        tint = Color.Gray, modifier = Modifier.size(14.dp))
                    Text(" ${item.location}", color = Color.Gray, fontSize = 12.sp)
                }

                Spacer(Modifier.height(8.dp))
                HorizontalDivider(color = Color(0xFFF0F0F0))
                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.DateRange, contentDescription = null,
                            tint = Color.Gray, modifier = Modifier.size(14.dp))
                        Text(" Applied: ${item.appliedDate}", color = Color.Gray, fontSize = 12.sp)
                    }
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null,
                        tint = Color.LightGray, modifier = Modifier.size(14.dp).clickable { onDelete() })
                }
            }
        }
    }
}

@Composable
fun TeamCard(item: TeamItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(LightBlue),
                contentAlignment = Alignment.Center
            ) {
                Text("🖥️", fontSize = 48.sp)
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(item.projectName, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black,
                        modifier = Modifier.weight(1f))
                    Spacer(Modifier.width(8.dp))
                    StatusChip(item.status)
                }

                Spacer(Modifier.height(4.dp))
                Text("Posisi: ${item.position}", color = Color.Gray, fontSize = 13.sp)

                Spacer(Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(LightBlue)
                        .padding(horizontal = 10.dp, vertical = 3.dp)
                ) {
                    Text(item.category, color = PrimaryBlue, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                }

                Spacer(Modifier.height(8.dp))
                HorizontalDivider(color = Color(0xFFF0F0F0))
                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.DateRange, contentDescription = null,
                            tint = Color.Gray, modifier = Modifier.size(14.dp))
                        Text(" Requested: ${item.requestedDate}", color = Color.Gray, fontSize = 12.sp)
                    }
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null,
                        tint = Color.LightGray, modifier = Modifier.size(14.dp))
                }

                item.successMessage?.let { msg ->
                    Spacer(Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFDCFCE7))
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text("🎉 $msg", color = StatusAccepted, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }
        }
    }
}

@Composable
fun ActivityTabRow(selectedTab: Int, onTabSelected: (Int) -> Unit, tabs: List<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFEEF2FF))
            .padding(4.dp)
    ) {
        tabs.forEachIndexed { idx, label ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (selectedTab == idx) PrimaryBlue else Color.Transparent)
                    .clickable { onTabSelected(idx) }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    label,
                    color = if (selectedTab == idx) Color.White else Color.Gray,
                    fontWeight = if (selectedTab == idx) FontWeight.Bold else FontWeight.Normal,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun ActivityScreen() {
    var selectedTab by remember { mutableIntStateOf(0) }
    var deleteItemId by remember { mutableStateOf<String?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var deletedItemName by remember { mutableStateOf("") }

    val internshipMenunggu = sampleInternships.count { it.status == StatusAplikasi.MENUNGGU }
    val internshipDiterima = sampleInternships.count { it.status == StatusAplikasi.DITERIMA }
    val internshipDitolak = sampleInternships.count { it.status == StatusAplikasi.DITOLAK }

    val teamMenunggu = sampleTeams.count { it.status == StatusAplikasi.MENUNGGU }
    val teamDiterima = sampleTeams.count { it.status == StatusAplikasi.DITERIMA }
    val teamDitolak = sampleTeams.count { it.status == StatusAplikasi.DITOLAK }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PageBg)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(16.dp))

        Text(
            "Activity",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = Color.Black
        )

        Spacer(Modifier.height(16.dp))

        val tabLabels = listOf(
            "Internship (${sampleInternships.size})",
            "Team (${sampleTeams.size})"
        )
        ActivityTabRow(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it },
            tabs = tabLabels
        )

        Spacer(Modifier.height(16.dp))

        if (selectedTab == 0) {
            SummaryBar(internshipMenunggu, internshipDiterima, internshipDitolak)
        } else {
            SummaryBar(teamMenunggu, teamDiterima, teamDitolak)
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            if (selectedTab == 0) {
                items(sampleInternships) { item ->
                    InternshipCard(
                        item = item,
                        onDelete = {
                            deleteItemId = item.title
                            showDeleteDialog = true
                        }
                    )
                }
            } else {
                items(sampleTeams) { item ->
                    TeamCard(item)
                }
            }
            item { Spacer(Modifier.height(16.dp)) }
        }
    }

    if (showDeleteDialog && deleteItemId != null) {
        DeleteConfirmationDialog(
            itemName = deleteItemId!!,
            onConfirm = {
                deletedItemName = deleteItemId!!
                showDeleteDialog = false
                deleteItemId = null
                showSuccessDialog = true
            },
            onDismiss = {
                showDeleteDialog = false
                deleteItemId = null
            },
            isVisible = true
        )
    }

    if (showSuccessDialog) {
        SuccessDialog(
            title = "Berhasil Dihapus",
            message = "Lamaran \"$deletedItemName\" telah berhasil dihapus.",
            onDismiss = { showSuccessDialog = false },
            buttonText = "OK"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewActivityScreen() {
    UNSCollabTheme {
        ActivityScreen()
    }
}
