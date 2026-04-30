package com.projek.unscollab.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * ModalBottomSheet untuk filter lowongan (kategori + kisaran gaji).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobFilterBottomSheet(
    onDismiss: () -> Unit,
    onApplyFilter: (category: String, salaryRange: String) -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    isVisible: Boolean = true
) {
    if (!isVisible) return

    var selectedCategory by remember { mutableStateOf("Semua") }
    var selectedSalaryRange by remember { mutableStateOf("Semua") }

    val categories   = listOf("Semua", "Internship", "Full-time", "Part-time", "Freelance")
    val salaryRanges = listOf("Semua", "< Rp 5 Juta", "Rp 5–10 Juta", "Rp 10–20 Juta", "> Rp 20 Juta")

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 560.dp)
                .padding(horizontal = 20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Filter Lowongan",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A2E)
                )
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Tutup",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            HorizontalDivider(color = Color(0xFFEEEEEE))
            Spacer(modifier = Modifier.height(12.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                SectionLabel("Kategori")
                categories.forEach { item ->
                    FilterOption(
                        label = item,
                        isSelected = selectedCategory == item,
                        onClick = { selectedCategory = item }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                SectionLabel("Kisaran Gaji")
                salaryRanges.forEach { item ->
                    FilterOption(
                        label = item,
                        isSelected = selectedSalaryRange == item,
                        onClick = { selectedSalaryRange = item }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        selectedCategory   = "Semua"
                        selectedSalaryRange = "Semua"
                    },
                    modifier = Modifier.weight(1f).height(46.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Reset", fontWeight = FontWeight.Medium, color = Color(0xFF1FABE1))
                }

                Button(
                    onClick = {
                        onApplyFilter(selectedCategory, selectedSalaryRange)
                        onDismiss()
                    },
                    modifier = Modifier.weight(1f).height(46.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1FABE1)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Terapkan", fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

/**
 * ModalBottomSheet untuk detail lowongan lengkap.
 */
@Suppress("unused")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDetailBottomSheet(
    jobTitle: String,
    company: String,
    description: String,
    requirements: List<String>,
    onApply: () -> Unit,
    onDismiss: () -> Unit,
    isVisible: Boolean = true
) {
    if (!isVisible) return

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Text(jobTitle, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A2E))
            Spacer(modifier = Modifier.height(4.dp))
            Text(company, fontSize = 14.sp, color = Color.Gray)

            HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp), color = Color(0xFFEEEEEE))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 380.dp)
            ) {
                item {
                    Text("Deskripsi Lowongan", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A2E))
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(description, fontSize = 13.sp, color = Color.Gray, lineHeight = 20.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Persyaratan", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A2E))
                    Spacer(modifier = Modifier.height(8.dp))
                }

                items(requirements.size) { index ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            "• ",
                            color = Color(0xFF1FABE1),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(top = 1.dp)
                        )
                        Text(requirements[index], fontSize = 13.sp, color = Color.Gray, lineHeight = 18.sp)
                    }
                }

                item { Spacer(modifier = Modifier.height(8.dp)) }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    onApply()
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth().height(46.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1FABE1)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Lamar Sekarang", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth().height(46.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Tutup", fontWeight = FontWeight.Medium, fontSize = 14.sp, color = Color(0xFF1FABE1))
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

/**
 * ModalBottomSheet untuk aksi (action sheet).
 */
@Suppress("unused")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionBottomSheet(
    title: String,
    actions: List<BottomSheetAction>,
    onDismiss: () -> Unit,
    isVisible: Boolean = true
) {
    if (!isVisible) return

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A2E),
                modifier = Modifier.padding(vertical = 12.dp)
            )

            HorizontalDivider(color = Color(0xFFEEEEEE))

            Column(modifier = Modifier.fillMaxWidth()) {
                actions.forEach { action ->
                    ActionItem(
                        action = action,
                        onClick = {
                            action.onClick()
                            onDismiss()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth().height(44.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Batal", fontWeight = FontWeight.Medium, color = Color(0xFF1FABE1))
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF888888),
        modifier = Modifier.padding(bottom = 6.dp)
    )
}

@Composable
private fun FilterOption(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = if (isSelected) Color(0xFF1FABE1) else Color(0xFF333333),
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
        )
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = null,
                tint = Color(0xFF1FABE1),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun ActionItem(action: BottomSheetAction, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 14.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = action.icon,
            contentDescription = action.label,
            tint = action.color,
            modifier = Modifier.size(22.dp)
        )
        Text(text = action.label, fontSize = 14.sp, color = action.color, fontWeight = FontWeight.Medium)
    }
}

data class BottomSheetAction(
    val label: String,
    val icon: ImageVector,
    val color: Color = Color(0xFF1A1A2E),
    val onClick: () -> Unit
)