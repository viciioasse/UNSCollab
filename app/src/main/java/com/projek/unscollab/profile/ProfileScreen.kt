package com.projek.unscollab.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.projek.unscollab.R
import com.projek.unscollab.ui.theme.UNSCollabTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

// --- DATA CLASSES ---
data class PortfolioItem(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val role: String,
    val description: String,
    val date: String
)

data class ExperienceItem(
    val id: String = UUID.randomUUID().toString(),
    val company: String,
    val position: String,
    val duration: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    // --- STATE PROFIL ---
    var userName by remember { mutableStateOf("Jordan Hayes") }
    var userId by remember { mutableStateOf("U8129034") }
    var userProdi by remember { mutableStateOf("Data Science") }
    var userAbout by remember { mutableStateOf("Halo! Saya adalah mahasiswa Data Science yang antusias terhadap pengembangan backend dan analitik data.") }

    var selectedTab by remember { mutableStateOf("Portofolio") }
    var showEditDialog by remember { mutableStateOf(false) }

    // --- STATE CRUD ---
    val portfolioItems = remember { mutableStateListOf(
        PortfolioItem(title = "E-Commerce Backend", role = "Backend Developer", description = "Membangun sistem backend dengan microservices.", date = "Jan 2025 - Sekarang"),
        PortfolioItem(title = "Data Analytics Tool", role = "Data Scientist", description = "Mengembangkan dashboard analitik data interaktif.", date = "Okt 2024 - Des 2024")
    ) }

    val experienceItems = remember { mutableStateListOf(
        ExperienceItem(company = "PT. Tech Indonesia", position = "Backend Intern", duration = "Agu 2024 - Jan 2025"),
        ExperienceItem(company = "Open Source Community", position = "Contributor", duration = "Feb 2024 - Des 2024")
    ) }

    val skills = remember { mutableStateListOf("Python", "Kotlin", "SQL", "Data Analysis", "Git") }

    // State untuk Dialog CRUD
    var showPortoDialog by remember { mutableStateOf(false) }
    var portoToEdit by remember { mutableStateOf<PortfolioItem?>(null) }

    var showExpDialog by remember { mutableStateOf(false) }
    var expToEdit by remember { mutableStateOf<ExperienceItem?>(null) }

    var showSkillDialog by remember { mutableStateOf(false) }
    var skillToEdit by remember { mutableStateOf<String?>(null) }
    var skillEditIndex by remember { mutableStateOf(-1) }

    Surface(modifier = modifier.fillMaxSize(), color = Color(0xFFF5F7FA)) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item { ProfileHeader() }
            item { ProfileInfoCard(name = userName, id = userId, prodi = userProdi) }
            item { ProfileStatsSection() }
            item { ProfileActions(onEditClick = { showEditDialog = true }) }
            item { ProfileTabSection(selectedTab = selectedTab, onTabChange = { selectedTab = it }) }

            when (selectedTab) {
                "Tentang" -> {
                    item { AboutSection(aboutText = userAbout) }
                }
                "Portofolio" -> {
                    item {
                        AddButton("Tambah Portofolio") {
                            portoToEdit = null
                            showPortoDialog = true
                        }
                    }
                    items(portfolioItems) { item ->
                        PortfolioCard(
                            item = item,
                            onEdit = { portoToEdit = item; showPortoDialog = true },
                            onDelete = { portfolioItems.remove(item) }
                        )
                    }
                }
                "Pengalaman" -> {
                    item {
                        AddButton("Tambah Pengalaman") {
                            expToEdit = null
                            showExpDialog = true
                        }
                    }
                    items(experienceItems) { item ->
                        ExperienceCard(
                            item = item,
                            onEdit = { expToEdit = item; showExpDialog = true },
                            onDelete = { experienceItems.remove(item) }
                        )
                    }
                }
                "Keahlian" -> {
                    item {
                        AddButton("Tambah Keahlian") {
                            skillToEdit = null
                            showSkillDialog = true
                        }
                    }
                    items(skills.size) { index ->
                        val skill = skills[index]
                        SkillCard(
                            skill = skill,
                            onEdit = { skillToEdit = skill; skillEditIndex = index; showSkillDialog = true },
                            onDelete = { skills.removeAt(index) }
                        )
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(32.dp)) }
        }

        if (showEditDialog) {
            EditProfileDialog(
                currentName = userName, currentId = userId, currentProdi = userProdi, currentAbout = userAbout,
                onDismiss = { showEditDialog = false },
                onSave = { name, id, prodi, about ->
                    userName = name; userId = id; userProdi = prodi; userAbout = about
                    showEditDialog = false
                }
            )
        }

        if (showPortoDialog) {
            PortoDialog(
                initialItem = portoToEdit,
                onDismiss = { showPortoDialog = false },
                onSave = { newItem ->
                    if (portoToEdit == null) portfolioItems.add(newItem)
                    else {
                        val index = portfolioItems.indexOfFirst { it.id == portoToEdit!!.id }
                        if (index != -1) portfolioItems[index] = newItem
                    }
                    showPortoDialog = false
                }
            )
        }

        if (showExpDialog) {
            ExpDialog(
                initialItem = expToEdit,
                onDismiss = { showExpDialog = false },
                onSave = { newItem ->
                    if (expToEdit == null) experienceItems.add(newItem)
                    else {
                        val index = experienceItems.indexOfFirst { it.id == expToEdit!!.id }
                        if (index != -1) experienceItems[index] = newItem
                    }
                    showExpDialog = false
                }
            )
        }

        if (showSkillDialog) {
            SkillDialog(
                initialSkill = skillToEdit,
                onDismiss = { showSkillDialog = false },
                onSave = { newSkill ->
                    if (skillToEdit == null) skills.add(newSkill)
                    else skills[skillEditIndex] = newSkill
                    showSkillDialog = false
                }
            )
        }
    }
}

// --- KOMPONEN DATE PICKER CUSTOM ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    label: String,
    selectedDateText: String,
    onDateSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    var showDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    // Formatter untuk menampilkan "Bulan Tahun" (Contoh: Jan 2025)
    val formatter = SimpleDateFormat("MMM yyyy", Locale("id", "ID"))

    Box(modifier = modifier.clickable(enabled = enabled) { if (enabled) showDialog = true }) {
        OutlinedTextField(
            value = selectedDateText,
            onValueChange = { },
            label = { Text(label) },
            enabled = false, // Disable typing, make it clickable only
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = if (enabled) Color(0xFF1A1A2E) else Color.Gray,
                disabledBorderColor = if (enabled) Color.Gray else Color.LightGray,
                disabledLabelColor = Color.Gray,
                disabledTrailingIconColor = Color.Gray
            ),
            trailingIcon = {
                Icon(Icons.Outlined.DateRange, contentDescription = "Pilih Tanggal")
            }
        )
    }

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        onDateSelected(formatter.format(Date(millis)))
                    }
                    showDialog = false
                }) {
                    Text("Pilih")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Batal") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

// --- DIALOG CRUD PORTOFOLIO DENGAN KALENDER ---
@Composable
fun PortoDialog(initialItem: PortfolioItem?, onDismiss: () -> Unit, onSave: (PortfolioItem) -> Unit) {
    var title by remember { mutableStateOf(initialItem?.title ?: "") }
    var role by remember { mutableStateOf(initialItem?.role ?: "") }
    var desc by remember { mutableStateOf(initialItem?.description ?: "") }

    // Parsing tanggal (Mulai - Selesai) jika sedang edit
    val dates = initialItem?.date?.split(" - ")
    var startDate by remember { mutableStateOf(dates?.getOrNull(0) ?: "") }
    var endDate by remember { mutableStateOf(dates?.getOrNull(1) ?: "") }
    var isCurrent by remember { mutableStateOf(endDate.equals("Sekarang", ignoreCase = true) || endDate.isEmpty()) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(shape = RoundedCornerShape(16.dp), color = Color.White, modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(if (initialItem == null) "Tambah Portofolio" else "Edit Portofolio", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Judul Proyek") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = role, onValueChange = { role = it }, label = { Text("Peran (Role)") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                Spacer(modifier = Modifier.height(12.dp))

                // Row untuk Tanggal Mulai dan Selesai
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    DatePickerField(
                        label = "Mulai",
                        selectedDateText = startDate,
                        onDateSelected = { startDate = it },
                        modifier = Modifier.weight(1f)
                    )
                    DatePickerField(
                        label = "Selesai",
                        selectedDateText = if (isCurrent) "Sekarang" else endDate,
                        onDateSelected = { endDate = it },
                        enabled = !isCurrent,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Checkbox "Sampai Sekarang"
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                    Checkbox(
                        checked = isCurrent,
                        onCheckedChange = { isCurrent = it; if (it) endDate = "Sekarang" },
                        colors = CheckboxDefaults.colors(checkedColor = Color(0xFF1FABE1))
                    )
                    Text("Saat ini masih berlangsung", fontSize = 13.sp, color = Color(0xFF555555))
                }

                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("Deskripsi Singkat") }, modifier = Modifier.fillMaxWidth(), minLines = 3, maxLines = 5)
                Spacer(modifier = Modifier.height(20.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) { Text("Batal", color = Color.Gray) }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            val finalDate = if (isCurrent) "$startDate - Sekarang" else "$startDate - $endDate"
                            onSave(PortfolioItem(id = initialItem?.id ?: UUID.randomUUID().toString(), title, role, desc, finalDate))
                        },
                        colors = ButtonDefaults.buttonColors(Color(0xFF1FABE1))
                    ) { Text("Simpan") }
                }
            }
        }
    }
}

// --- DIALOG CRUD PENGALAMAN DENGAN KALENDER ---
@Composable
fun ExpDialog(initialItem: ExperienceItem?, onDismiss: () -> Unit, onSave: (ExperienceItem) -> Unit) {
    var company by remember { mutableStateOf(initialItem?.company ?: "") }
    var position by remember { mutableStateOf(initialItem?.position ?: "") }

    val dates = initialItem?.duration?.split(" - ")
    var startDate by remember { mutableStateOf(dates?.getOrNull(0) ?: "") }
    var endDate by remember { mutableStateOf(dates?.getOrNull(1) ?: "") }
    var isCurrent by remember { mutableStateOf(endDate.equals("Sekarang", ignoreCase = true) || endDate.isEmpty()) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(shape = RoundedCornerShape(16.dp), color = Color.White, modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(if (initialItem == null) "Tambah Pengalaman" else "Edit Pengalaman", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = company, onValueChange = { company = it }, label = { Text("Nama Perusahaan / Organisasi") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = position, onValueChange = { position = it }, label = { Text("Posisi / Jabatan") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                Spacer(modifier = Modifier.height(12.dp))

                // Row untuk Tanggal Mulai dan Selesai
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    DatePickerField(
                        label = "Mulai",
                        selectedDateText = startDate,
                        onDateSelected = { startDate = it },
                        modifier = Modifier.weight(1f)
                    )
                    DatePickerField(
                        label = "Selesai",
                        selectedDateText = if (isCurrent) "Sekarang" else endDate,
                        onDateSelected = { endDate = it },
                        enabled = !isCurrent,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Checkbox "Sampai Sekarang"
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                    Checkbox(
                        checked = isCurrent,
                        onCheckedChange = { isCurrent = it; if (it) endDate = "Sekarang" },
                        colors = CheckboxDefaults.colors(checkedColor = Color(0xFF1FABE1))
                    )
                    Text("Saat ini masih bekerja di sini", fontSize = 13.sp, color = Color(0xFF555555))
                }

                Spacer(modifier = Modifier.height(20.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) { Text("Batal", color = Color.Gray) }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            val finalDuration = if (isCurrent) "$startDate - Sekarang" else "$startDate - $endDate"
                            onSave(ExperienceItem(id = initialItem?.id ?: UUID.randomUUID().toString(), company, position, finalDuration))
                        },
                        colors = ButtonDefaults.buttonColors(Color(0xFF1FABE1))
                    ) { Text("Simpan") }
                }
            }
        }
    }
}

// =========================================================================
// KOMPONEN LAINNYA (Sama persis seperti sebelumnya)
// =========================================================================

@Composable
fun AddButton(text: String, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF1FABE1)),
        border = BorderStroke(1.dp, Color(0xFF1FABE1))
    ) {
        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun PortfolioCard(item: PortfolioItem, onEdit: () -> Unit, onDelete: () -> Unit) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                Box(
                    modifier = Modifier.size(40.dp).background(Color(0xFFE3F2FD), RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) { Icon(Icons.Default.Build, null, tint = Color(0xFF1FABE1)) }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(item.title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF1A1A2E))
                    Text(item.role, color = Color(0xFF1FABE1), fontSize = 13.sp, fontWeight = FontWeight.Medium)
                }
                Row {
                    IconButton(onClick = onEdit, modifier = Modifier.size(24.dp)) { Icon(Icons.Default.Edit, null, tint = Color.Gray, modifier = Modifier.size(18.dp)) }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = onDelete, modifier = Modifier.size(24.dp)) { Icon(Icons.Default.Delete, null, tint = Color.Red.copy(alpha = 0.7f), modifier = Modifier.size(18.dp)) }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(item.description, fontSize = 13.sp, color = Color(0xFF555555))
            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.DateRange, null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(item.date, fontSize = 11.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun ExperienceCard(item: ExperienceItem, onEdit: () -> Unit, onDelete: () -> Unit) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(item.position, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF1A1A2E))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(item.company, color = Color(0xFF555555), fontSize = 13.sp)
                }
                Row {
                    IconButton(onClick = onEdit, modifier = Modifier.size(24.dp)) { Icon(Icons.Default.Edit, null, tint = Color.Gray, modifier = Modifier.size(18.dp)) }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = onDelete, modifier = Modifier.size(24.dp)) { Icon(Icons.Default.Delete, null, tint = Color.Red.copy(alpha = 0.7f), modifier = Modifier.size(18.dp)) }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.DateRange, null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(item.duration, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun SkillCard(skill: String, onEdit: () -> Unit, onDelete: () -> Unit) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.CheckCircle, null, tint = Color(0xFF1FABE1), modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(skill, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1A1A2E), modifier = Modifier.weight(1f))
            IconButton(onClick = onEdit, modifier = Modifier.size(24.dp)) { Icon(Icons.Default.Edit, null, tint = Color.Gray, modifier = Modifier.size(18.dp)) }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = onDelete, modifier = Modifier.size(24.dp)) { Icon(Icons.Default.Delete, null, tint = Color.Red.copy(alpha = 0.7f), modifier = Modifier.size(18.dp)) }
        }
    }
}

@Composable
fun SkillDialog(initialSkill: String?, onDismiss: () -> Unit, onSave: (String) -> Unit) {
    var skill by remember { mutableStateOf(initialSkill ?: "") }
    Dialog(onDismissRequest = onDismiss) {
        Surface(shape = RoundedCornerShape(16.dp), color = Color.White, modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(if (initialSkill == null) "Tambah Keahlian" else "Edit Keahlian", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = skill, onValueChange = { skill = it }, label = { Text("Nama Keahlian") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(20.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) { Text("Batal", color = Color.Gray) }
                    Button(onClick = { onSave(skill) }, colors = ButtonDefaults.buttonColors(Color(0xFF1FABE1))) { Text("Simpan") }
                }
            }
        }
    }
}

@Composable
fun EditProfileDialog(
    currentName: String, currentId: String, currentProdi: String, currentAbout: String,
    onDismiss: () -> Unit, onSave: (String, String, String, String) -> Unit
) {
    var tempName by remember { mutableStateOf(currentName) }
    var tempId by remember { mutableStateOf(currentId) }
    var tempProdi by remember { mutableStateOf(currentProdi) }
    var tempAbout by remember { mutableStateOf(currentAbout) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(shape = RoundedCornerShape(16.dp), color = Color.White, modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(20.dp).fillMaxWidth()) {
                Text("Edit Profil", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF1A1A2E))
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = tempName, onValueChange = { tempName = it }, label = { Text("Nama Lengkap") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = tempId, onValueChange = { tempId = it }, label = { Text("NIM / ID") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = tempProdi, onValueChange = { tempProdi = it }, label = { Text("Program Studi") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = tempAbout, onValueChange = { tempAbout = it }, label = { Text("Tentang (Summary)") }, modifier = Modifier.fillMaxWidth(), minLines = 3, maxLines = 5)
                Spacer(modifier = Modifier.height(20.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) { Text("Batal", color = Color.Gray, fontWeight = FontWeight.SemiBold) }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { onSave(tempName, tempId, tempProdi, tempAbout) }, colors = ButtonDefaults.buttonColors(Color(0xFF1FABE1)), shape = RoundedCornerShape(8.dp)) { Text("Simpan", fontWeight = FontWeight.Bold) }
                }
            }
        }
    }
}

@Composable
private fun ProfileHeader() {
    Box(modifier = Modifier.fillMaxWidth().height(140.dp).background(Color(0xFF1FABE1)).padding(top = 16.dp, start = 8.dp, end = 8.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
            IconButton(onClick = { /* Back Action */ }) { Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White) }
            Text("USER PROFILE", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 12.dp))
            IconButton(onClick = { /* Settings Action */ }) { Icon(Icons.Filled.Settings, contentDescription = "Settings", tint = Color.White) }
        }
    }
}

@Composable
private fun ProfileInfoCard(name: String, id: String, prodi: String) {
    Column(modifier = Modifier.fillMaxWidth().offset(y = (-50).dp).padding(horizontal = 24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(modifier = Modifier.size(100.dp), shape = CircleShape, color = Color.White, border = BorderStroke(4.dp, Color.White), shadowElevation = 4.dp) {
            Image(painter = painterResource(id = R.drawable.icon_profile), contentDescription = "Profile Picture", modifier = Modifier.fillMaxSize().clip(CircleShape), contentScale = ContentScale.Crop)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = name, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF1A1A2E))
        Text(text = "$id • $prodi", fontSize = 14.sp, color = Color(0xFF555555), modifier = Modifier.padding(top = 4.dp))
    }
}

@Composable
private fun ProfileStatsSection() {
    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp).offset(y = (-30).dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        StatItem(value = "12", label = "Proyek")
        HorizontalDivider(modifier = Modifier.height(30.dp).width(1.dp), color = Color.LightGray)
        StatItem(value = "3", label = "Tim Aktif")
        HorizontalDivider(modifier = Modifier.height(30.dp).width(1.dp), color = Color.LightGray)
        StatItem(value = "145", label = "Koneksi")
    }
}

@Composable
private fun StatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF1A1A2E))
        Text(text = label, fontSize = 12.sp, color = Color.Gray)
    }
}

@Composable
private fun ProfileActions(onEditClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).offset(y = (-10).dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Button(onClick = onEditClick, modifier = Modifier.weight(1f).height(44.dp), colors = ButtonDefaults.buttonColors(Color(0xFF1FABE1)), shape = RoundedCornerShape(12.dp)) {
            Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(8.dp))
            Text("Edit Profile", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        }
        OutlinedButton(onClick = { }, modifier = Modifier.weight(1f).height(44.dp), shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.outlinedButtonColors(Color(0xFF1FABE1)), border = BorderStroke(1.dp, Color(0xFF1FABE1))) {
            Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(8.dp))
            Text("Share", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun ProfileTabSection(selectedTab: String, onTabChange: (String) -> Unit) {
    val tabs = listOf("Tentang", "Portofolio", "Pengalaman", "Keahlian")
    LazyRow(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(tabs.size) { index ->
            val tab = tabs[index]
            val isSelected = selectedTab == tab
            Button(
                onClick = { onTabChange(tab) },
                modifier = Modifier.height(36.dp).clip(RoundedCornerShape(20.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = if (isSelected) Color(0xFF1FABE1) else Color.White, contentColor = if (isSelected) Color.White else Color(0xFF1FABE1)),
                border = if (isSelected) null else BorderStroke(1.dp, Color(0xFF1FABE1)),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) { Text(text = tab, fontSize = 13.sp, fontWeight = FontWeight.Medium) }
        }
    }
}

@Composable
private fun AboutSection(aboutText: String) {
    ElevatedCard(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), shape = RoundedCornerShape(14.dp), colors = CardDefaults.elevatedCardColors(Color.White), elevation = CardDefaults.elevatedCardElevation(2.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = aboutText, fontSize = 13.sp, color = Color(0xFF555555), lineHeight = 20.sp, textAlign = TextAlign.Justify)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    UNSCollabTheme {
        ProfileScreen()
    }
}