package com.projek.unscollab.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projek.unscollab.R
import com.projek.unscollab.navigation.LocalBackStack
import com.projek.unscollab.navigation.Route
import com.projek.unscollab.ui.theme.UNSCollabTheme
import com.projek.unscollab.components.JobFilterBottomSheet
import com.projek.unscollab.components.SuccessAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState

data class JobItem(
    val id: String,
    val title: String,
    val company: String,
    val location: String,
    val duration: String,
    val salary: String,
    val postedTime: String,
    val type: String
)

data class TeamItem(
    val id: String,
    val title: String,
    val category: String,
    val description: String,
    val members: String,
    val postedTime: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, savedItems: Set<String> = emptySet()) {
    val backStack = LocalBackStack.current

    var selectedFilter by remember { mutableStateOf("all") }
    var localSavedItems by remember { mutableStateOf(savedItems) }
    var searchQuery by remember { mutableStateOf("") }

    var showFilterSheet by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var appliedFilterCategory by remember { mutableStateOf("Semua") }
    var appliedFilterSalary by remember { mutableStateOf("Semua") }

    val sheetState = rememberModalBottomSheetState()

    val jobItems = listOf(
        JobItem("JOB-001", "Android Developer",  "PT. Tech Indonesia",   "Jakarta",  "3-6 bulan", "Rp 5.000.000",            "2 jam yang lalu",    "internship"),
        JobItem("JOB-002", "Backend Developer",  "CV. Digital Solutions","Bandung",  "6 bulan",   "Disembunyikan",           "1 jam yang lalu",    "internship"),
        JobItem("JOB-003", "Frontend Developer", "PT. Startup Maju",     "Surabaya", "3 bulan",   "Rp 3.500.000 - 5.000.000","30 menit yang lalu", "internship")
    )

    val teamItems = listOf(
        TeamItem("TEAM-001", "UNS Collab Squad", "Mobile Development", "Kami mencari developer mobile untuk pengembangan UNSCollab", "3/5 member",  "3 jam yang lalu"),
        TeamItem("TEAM-002", "WIBAWA Reguler",   "Program Pembinaan",  "Kami mencari mahasiswa/i yang siap mengikuti program WIBAWA", "4/6 Members", "1 jam yang lalu")
    )

    val tabFilteredJobs = when (selectedFilter) {
        "internships" -> jobItems.filter { it.type == "internship" }
        "teammates"   -> emptyList()
        else          -> jobItems
    }
    val tabFilteredTeams = when (selectedFilter) {
        "teammates" -> teamItems
        "all"       -> teamItems
        else        -> emptyList()
    }

    val query = searchQuery.trim().lowercase()
    val filteredJobs = if (query.isEmpty()) tabFilteredJobs else
        tabFilteredJobs.filter {
            it.title.lowercase().contains(query) ||
                    it.company.lowercase().contains(query) ||
                    it.location.lowercase().contains(query)
        }
    val filteredTeams = if (query.isEmpty()) tabFilteredTeams else
        tabFilteredTeams.filter {
            it.title.lowercase().contains(query) ||
                    it.category.lowercase().contains(query) ||
                    it.description.lowercase().contains(query)
        }

    val hasResults = filteredJobs.isNotEmpty() || filteredTeams.isNotEmpty()

    Surface(modifier = modifier.fillMaxSize(), color = Color(0xFFF5F7FA)) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                HeaderSection(
                    searchQuery = searchQuery,
                    onSearchChange = { searchQuery = it },
                    onFilterClick = { showFilterSheet = true }
                )
            }
            item {
                FilterSection(
                    selectedFilter = selectedFilter,
                    onFilterChange = { selectedFilter = it }
                )
            }

            if (!hasResults) {
                item { EmptyState(query = searchQuery) }
            }

            items(filteredJobs.size) { index ->
                val job = filteredJobs[index]
                JobCard(
                    item = job,
                    isSaved = localSavedItems.contains(job.id),
                    onSaveClick = {
                        localSavedItems = if (localSavedItems.contains(job.id))
                            localSavedItems - job.id else localSavedItems + job.id
                    },
                    onClick = { backStack.add(Route.JobDetail(job.id)) }
                )
            }

            items(filteredTeams.size) { index ->
                val team = filteredTeams[index]
                TeamCard(
                    item = team,
                    isSaved = localSavedItems.contains(team.id),
                    onSaveClick = {
                        localSavedItems = if (localSavedItems.contains(team.id))
                            localSavedItems - team.id else localSavedItems + team.id
                    },
                    onClick = { backStack.add(Route.TeamDetail(team.id)) }
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }

    if (showFilterSheet) {
        JobFilterBottomSheet(
            onDismiss = { showFilterSheet = false },
            onApplyFilter = { category, salary ->
                appliedFilterCategory = category
                appliedFilterSalary = salary
                showSuccessDialog = true
            },
            sheetState = sheetState,
            isVisible = true
        )
    }

    if (showSuccessDialog) {
        SuccessAlertDialog(
            title = "Filter Diterapkan",
            message = "Kategori: $appliedFilterCategory\nKisaran Gaji: $appliedFilterSalary",
            onDismiss = { showSuccessDialog = false },
            buttonText = "OK"
        )
    }
}

@Composable
private fun HeaderSection(
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onFilterClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1FABE1))
            .padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 16.dp)
    ) {
        Column {
            Text(
                text = "Hello, Mahasiswa UNS 👋",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Temukan lowongan & tim terbaik untukmu",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 2.dp, bottom = 14.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    SearchBar(query = searchQuery, onQueryChange = onSearchChange)
                }
                Surface(
                    modifier = Modifier
                        .size(44.dp)
                        .clickable { onFilterClick() },
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White.copy(alpha = 0.2f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Filter",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp),
        shape = RoundedCornerShape(22.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                tint = Color(0xFF1FABE1),
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Box(modifier = Modifier.weight(1f)) {
                if (query.isEmpty()) {
                    Text(
                        text = "Cari lowongan atau tim...",
                        color = Color(0xFFBBBBBB),
                        fontSize = 13.sp
                    )
                }
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 13.sp, color = Color(0xFF1A1A2E)),
                    cursorBrush = SolidColor(Color(0xFF1FABE1)),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            if (query.isNotEmpty()) {
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(Color(0xFFDDDDDD), RoundedCornerShape(10.dp))
                        .clickable { onQueryChange("") },
                    contentAlignment = Alignment.Center
                ) {
                    Text("✕", fontSize = 10.sp, color = Color.Gray)
                }
            }
        }
    }
}

@Composable
private fun EmptyState(query: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp, bottom = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("🔍", fontSize = 48.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = if (query.isNotEmpty()) "Tidak ada hasil untuk \"$query\""
            else "Tidak ada item tersedia",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF555555)
        )
        Text(
            text = "Coba kata kunci lain atau ubah filter",
            fontSize = 13.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun FilterSection(
    selectedFilter: String = "all",
    onFilterChange: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilterChip(label = "Semua",      selected = selectedFilter == "all",        onClick = { onFilterChange("all") })
        FilterChip(label = "Internship", selected = selectedFilter == "internships", onClick = { onFilterChange("internships") })
        FilterChip(label = "Team",       selected = selectedFilter == "teammates",   onClick = { onFilterChange("teammates") })
    }
}

@Composable
fun FilterChip(
    label: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(32.dp).clip(RoundedCornerShape(20.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Color(0xFF1FABE1) else Color.White,
            contentColor   = if (selected) Color.White else Color(0xFF1FABE1)
        ),
        border = if (selected) null else BorderStroke(1.dp, Color(0xFF1FABE1)),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun JobCard(
    item: JobItem,
    isSaved: Boolean = false,
    modifier: Modifier = Modifier,
    onSaveClick: () -> Unit = {},
    onClick: () -> Unit = {}
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.logo_uns),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp).clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(item.title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF1A1A2E))
                    Text(item.company, color = Color.Gray, fontSize = 13.sp)
                }
                IconButton(onClick = onSaveClick, modifier = Modifier.size(36.dp)) {
                    Icon(
                        imageVector = if (isSaved) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = null,
                        tint = if (isSaved) Color(0xFFFFC107) else Color(0xFFCCCCCC),
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(id = R.drawable.ic_location), contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(item.location, fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.width(14.dp))
                Icon(painter = painterResource(id = R.drawable.ic_clock), contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(item.duration, fontSize = 12.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = item.salary,
                color = if (item.salary == "Disembunyikan") Color.Gray else Color(0xFF2E7D32),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(item.postedTime, fontSize = 11.sp, color = Color.Gray)
        }
    }
}

@Composable
fun TeamCard(
    item: TeamItem,
    isSaved: Boolean = false,
    modifier: Modifier = Modifier,
    onSaveClick: () -> Unit = {},
    onClick: () -> Unit = {}
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.logo_uns),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp).clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(item.title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF1A1A2E))
                    Text(item.category, color = Color.Gray, fontSize = 13.sp)
                }
                IconButton(onClick = onSaveClick, modifier = Modifier.size(36.dp)) {
                    Icon(
                        imageVector = if (isSaved) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = null,
                        tint = if (isSaved) Color(0xFFFFC107) else Color(0xFFCCCCCC),
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(item.description, fontSize = 13.sp, color = Color(0xFF555555), lineHeight = 18.sp)

            Spacer(modifier = Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(id = R.drawable.ic_people), contentDescription = null, tint = Color(0xFF1FABE1), modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(item.members, color = Color(0xFF1FABE1), fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(item.postedTime, fontSize = 11.sp, color = Color.Gray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    UNSCollabTheme {
        HomeScreen()
    }
}