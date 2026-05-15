package com.projek.unscollab.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import java.text.SimpleDateFormat
import java.util.*

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
    val formatter = SimpleDateFormat("MMM yyyy", Locale("id", "ID"))

    Box(
        modifier = modifier.clickable(enabled = enabled) {
            if (enabled) showDialog = true
        }
    ) {
        OutlinedTextField(
            value = selectedDateText,
            onValueChange = { },
            label = { Text(label) },
            enabled = false,
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = if (enabled) Color(0xFF1A1A2E) else Color.Gray,
                disabledBorderColor = if (enabled) Color.Gray else Color.LightGray,
                disabledLabelColor = Color.Gray,
                disabledTrailingIconColor = Color.Gray
            ),
            trailingIcon = { Icon(Icons.Outlined.DateRange, null) }
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
                }) { Text("Pilih") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Batal") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
fun PortoDialog(
    initialItem: PortfolioItem?,
    onDismiss: () -> Unit,
    onSave: (PortfolioItem) -> Unit
) {
    var title by remember { mutableStateOf(initialItem?.title ?: "") }
    var role by remember { mutableStateOf(initialItem?.role ?: "") }
    var desc by remember { mutableStateOf(initialItem?.description ?: "") }

    val dates = initialItem?.date?.split(" - ")
    var startDate by remember { mutableStateOf(dates?.getOrNull(0) ?: "") }
    var endDate by remember { mutableStateOf(dates?.getOrNull(1) ?: "") }
    var isCurrent by remember {
        mutableStateOf(endDate.equals("Sekarang", ignoreCase = true) || endDate.isEmpty())
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = if (initialItem == null) "Tambah Portofolio" else "Edit Portofolio",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(Modifier.height(16.dp))
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Judul Proyek") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = role,
                    onValueChange = { role = it },
                    label = { Text("Peran (Role)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
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

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Checkbox(
                        checked = isCurrent,
                        onCheckedChange = { isCurrent = it; if (it) endDate = "Sekarang" },
                        colors = CheckboxDefaults.colors(checkedColor = Color(0xFF1FABE1))
                    )
                    Text("Saat ini masih berlangsung", fontSize = 13.sp, color = Color(0xFF555555))
                }

                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = desc,
                    onValueChange = { desc = it },
                    label = { Text("Deskripsi Singkat") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    maxLines = 5
                )
                Spacer(Modifier.height(20.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) { Text("Batal", color = Color.Gray) }
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = {
                            val finalDate = if (isCurrent) "$startDate - Sekarang"
                            else "$startDate - $endDate"
                            onSave(
                                PortfolioItem(
                                    id = initialItem?.id ?: UUID.randomUUID().toString(),
                                    title, role, desc, finalDate
                                )
                            )
                        },
                        colors = ButtonDefaults.buttonColors(Color(0xFF1FABE1))
                    ) { Text("Simpan") }
                }
            }
        }
    }
}

@Composable
fun ExpDialog(
    initialItem: ExperienceItem?,
    onDismiss: () -> Unit,
    onSave: (ExperienceItem) -> Unit
) {
    var company by remember { mutableStateOf(initialItem?.company ?: "") }
    var position by remember { mutableStateOf(initialItem?.position ?: "") }

    val dates = initialItem?.duration?.split(" - ")
    var startDate by remember { mutableStateOf(dates?.getOrNull(0) ?: "") }
    var endDate by remember { mutableStateOf(dates?.getOrNull(1) ?: "") }
    var isCurrent by remember {
        mutableStateOf(endDate.equals("Sekarang", ignoreCase = true) || endDate.isEmpty())
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = if (initialItem == null) "Tambah Pengalaman" else "Edit Pengalaman",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(Modifier.height(16.dp))
                OutlinedTextField(
                    value = company,
                    onValueChange = { company = it },
                    label = { Text("Nama Perusahaan / Organisasi") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = position,
                    onValueChange = { position = it },
                    label = { Text("Posisi / Jabatan") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
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

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Checkbox(
                        checked = isCurrent,
                        onCheckedChange = { isCurrent = it; if (it) endDate = "Sekarang" },
                        colors = CheckboxDefaults.colors(checkedColor = Color(0xFF1FABE1))
                    )
                    Text("Saat ini masih bekerja di sini", fontSize = 13.sp, color = Color(0xFF555555))
                }

                Spacer(Modifier.height(20.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) { Text("Batal", color = Color.Gray) }
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = {
                            val finalDuration = if (isCurrent) "$startDate - Sekarang"
                            else "$startDate - $endDate"
                            onSave(
                                ExperienceItem(
                                    id = initialItem?.id ?: UUID.randomUUID().toString(),
                                    company, position, finalDuration
                                )
                            )
                        },
                        colors = ButtonDefaults.buttonColors(Color(0xFF1FABE1))
                    ) { Text("Simpan") }
                }
            }
        }
    }
}

@Composable
fun SkillDialog(
    initialSkill: String?,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var skill by remember { mutableStateOf(initialSkill ?: "") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = if (initialSkill == null) "Tambah Keahlian" else "Edit Keahlian",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(Modifier.height(16.dp))
                OutlinedTextField(
                    value = skill,
                    onValueChange = { skill = it },
                    label = { Text("Nama Keahlian") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(20.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) { Text("Batal", color = Color.Gray) }
                    Button(
                        onClick = { onSave(skill) },
                        colors = ButtonDefaults.buttonColors(Color(0xFF1FABE1))
                    ) { Text("Simpan") }
                }
            }
        }
    }
}

@Composable
fun EditProfileDialog(
    currentName: String,
    currentId: String,
    currentProdi: String,
    currentAbout: String,
    onDismiss: () -> Unit,
    onSave: (String, String, String, String) -> Unit
) {
    var tempName by remember { mutableStateOf(currentName) }
    var tempId by remember { mutableStateOf(currentId) }
    var tempProdi by remember { mutableStateOf(currentProdi) }
    var tempAbout by remember { mutableStateOf(currentAbout) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp).fillMaxWidth()) {
                Text(
                    text = "Edit Profil",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF1A1A2E)
                )
                Spacer(Modifier.height(16.dp))
                OutlinedTextField(
                    value = tempName,
                    onValueChange = { tempName = it },
                    label = { Text("Nama Lengkap") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = tempId,
                    onValueChange = { tempId = it },
                    label = { Text("NIM / ID") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = tempProdi,
                    onValueChange = { tempProdi = it },
                    label = { Text("Program Studi") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = tempAbout,
                    onValueChange = { tempAbout = it },
                    label = { Text("Tentang") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    maxLines = 5
                )
                Spacer(Modifier.height(20.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) {
                        Text("Batal", color = Color.Gray, fontWeight = FontWeight.SemiBold)
                    }
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = { onSave(tempName, tempId, tempProdi, tempAbout) },
                        colors = ButtonDefaults.buttonColors(Color(0xFF1FABE1)),
                        shape = RoundedCornerShape(8.dp)
                    ) { Text("Simpan", fontWeight = FontWeight.Bold) }
                }
            }
        }
    }
}