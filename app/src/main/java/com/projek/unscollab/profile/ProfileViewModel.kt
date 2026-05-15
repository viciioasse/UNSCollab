package com.projek.unscollab.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.util.UUID

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

class ProfileViewModel : ViewModel() {
    var userName by mutableStateOf("Jordan Hayes")
    var userId by mutableStateOf("U8129034")
    var userProdi by mutableStateOf("Data Science")
    var userAbout by mutableStateOf(
        "Halo! Saya adalah mahasiswa Data Science yang antusias " +
                "terhadap pengembangan backend dan analitik data."
    )

    val portfolioItems = mutableStateListOf(
        PortfolioItem(
            title = "E-Commerce Backend",
            role = "Backend Developer",
            description = "Membangun sistem backend dengan microservices.",
            date = "Jan 2025 - Sekarang"
        ),
        PortfolioItem(
            title = "Data Analytics Tool",
            role = "Data Scientist",
            description = "Mengembangkan dashboard analitik data interaktif.",
            date = "Okt 2024 - Des 2024"
        )
    )

    val experienceItems = mutableStateListOf(
        ExperienceItem(
            company = "PT. Tech Indonesia",
            position = "Backend Intern",
            duration = "Agu 2024 - Jan 2025"
        ),
        ExperienceItem(
            company = "Open Source Community",
            position = "Contributor",
            duration = "Feb 2024 - Des 2024"
        )
    )

    val skills = mutableStateListOf(
        "Python", "Kotlin", "SQL", "Data Analysis", "Git"
    )

    fun updateProfile(name: String, id: String, prodi: String, about: String) {
        userName = name
        userId = id
        userProdi = prodi
        userAbout = about
    }

    fun addOrUpdatePortfolio(item: PortfolioItem, isEdit: Boolean) {
        if (isEdit) {
            val index = portfolioItems.indexOfFirst { it.id == item.id }
            if (index != -1) portfolioItems[index] = item
        } else {
            portfolioItems.add(item)
        }
    }

    fun deletePortfolio(item: PortfolioItem) = portfolioItems.remove(item)

    fun addOrUpdateExperience(item: ExperienceItem, isEdit: Boolean) {
        if (isEdit) {
            val index = experienceItems.indexOfFirst { it.id == item.id }
            if (index != -1) experienceItems[index] = item
        } else {
            experienceItems.add(item)
        }
    }

    fun deleteExperience(item: ExperienceItem) = experienceItems.remove(item)

    fun addOrUpdateSkill(skill: String, index: Int) {
        if (index != -1) {
            skills[index] = skill
        } else {
            skills.add(skill)
        }
    }

    fun deleteSkill(index: Int) {
        if (index in skills.indices) skills.removeAt(index)
    }
}