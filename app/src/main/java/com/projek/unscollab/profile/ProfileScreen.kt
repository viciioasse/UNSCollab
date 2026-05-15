package com.projek.unscollab.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.projek.unscollab.ui.theme.UNSCollabTheme

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel()
) {
    var selectedTab by remember { mutableStateOf("Portofolio") }

    // Dialog States
    var showEditDialog by remember { mutableStateOf(false) }

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
            item {
                ProfileInfoCard(
                    viewModel.userName,
                    viewModel.userId,
                    viewModel.userProdi
                )
            }
            item { ProfileStatsSection() }
            item { ProfileActions(onEditClick = { showEditDialog = true }) }
            item { ProfileTabSection(selectedTab) { selectedTab = it } }

            when (selectedTab) {
                "Tentang" -> {
                    item { AboutSection(viewModel.userAbout) }
                }
                "Portofolio" -> {
                    item {
                        AddButton("Tambah Portofolio") {
                            portoToEdit = null
                            showPortoDialog = true
                        }
                    }
                    items(viewModel.portfolioItems) { item ->
                        PortfolioCard(
                            item = item,
                            onEdit = {
                                portoToEdit = item
                                showPortoDialog = true
                            },
                            onDelete = {
                                viewModel.deletePortfolio(item)
                            }
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
                    items(viewModel.experienceItems) { item ->
                        ExperienceCard(
                            item = item,
                            onEdit = {
                                expToEdit = item
                                showExpDialog = true
                            },
                            onDelete = {
                                viewModel.deleteExperience(item)
                            }
                        )
                    }
                }
                "Keahlian" -> {
                    item {
                        AddButton("Tambah Keahlian") {
                            skillToEdit = null
                            skillEditIndex = -1
                            showSkillDialog = true
                        }
                    }
                    items(viewModel.skills.size) { index ->
                        SkillCard(
                            skill = viewModel.skills[index],
                            onEdit = {
                                skillToEdit = viewModel.skills[index]
                                skillEditIndex = index
                                showSkillDialog = true
                            },
                            onDelete = {
                                viewModel.deleteSkill(index)
                            }
                        )
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(32.dp)) }
        }

        // --- PANGGILAN DIALOGS ---
        if (showEditDialog) {
            EditProfileDialog(
                currentName = viewModel.userName,
                currentId = viewModel.userId,
                currentProdi = viewModel.userProdi,
                currentAbout = viewModel.userAbout,
                onDismiss = { showEditDialog = false }
            ) { n, i, p, a ->
                viewModel.updateProfile(n, i, p, a)
                showEditDialog = false
            }
        }

        if (showPortoDialog) {
            PortoDialog(
                initialItem = portoToEdit,
                onDismiss = { showPortoDialog = false }
            ) {
                viewModel.addOrUpdatePortfolio(it, portoToEdit != null)
                showPortoDialog = false
            }
        }

        if (showExpDialog) {
            ExpDialog(
                initialItem = expToEdit,
                onDismiss = { showExpDialog = false }
            ) {
                viewModel.addOrUpdateExperience(it, expToEdit != null)
                showExpDialog = false
            }
        }

        if (showSkillDialog) {
            SkillDialog(
                initialSkill = skillToEdit,
                onDismiss = { showSkillDialog = false }
            ) {
                viewModel.addOrUpdateSkill(it, skillEditIndex)
                showSkillDialog = false
            }
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