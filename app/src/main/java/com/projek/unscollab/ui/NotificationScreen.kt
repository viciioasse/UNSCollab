package com.projek.unscollab.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NotificationScreen() {
    var selectedFilter by remember { mutableStateOf("All") }
    val filters = listOf("All", "Intern", "Team")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp)
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Notifications",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        //Filter
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            filters.forEach { filter ->
                FilterChip(
                    selected = selectedFilter == filter,
                    onClick = { selectedFilter = filter },
                    label = { Text(filter) },
                    shape = RoundedCornerShape(20.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFFEEEEEE),
                        selectedLabelColor = Color.Black,
                        containerColor = Color.Transparent,
                        labelColor = Color.Gray
                    ),
                    border = if (selectedFilter == filter) null else BorderStroke(1.dp, Color.LightGray)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                Text(
                    text = "New",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }
            
            //Notif
            item {
                NotificationCard(
                    name = "Lisa Thompson",
                    action = "sent you a team collaboration request.",
                    detail = "4 mutual team members",
                    time = "2 mins ago",
                    hasButtons = true,
                    isUnread = true
                )
            }

            item {
                Text(
                    text = "Earlier",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            item {
                NotificationCard(
                    name = "Chou Guo Wie",
                    action = "posted in Team 'Java PAB':",
                    detail = "\"Added data model update for notification list...\"",
                    time = "4 hours ago",
                    isUnread = true
                )
            }

            item {
                NotificationCard(
                    name = "Bahlil imoed and 3 others",
                    action = "edited Team 'UNS Project' page.",
                    time = "a day ago",
                    isUnread = true
                )
            }
            
            item {
                NotificationCard(
                    name = "Team 'AI Research'",
                    action = "changed its name to 'Advanced AI UNS'.",
                    time = "3 weeks ago",
                    isUnread = true
                )
            }
            
             item {
                NotificationCard(
                    name = "Must A Nice",
                    action = "sent you a team message.",
                    time = "1 month ago",
                    isUnread = false
                )
            }
        }
    }
}

@Composable
fun NotificationCard(
    name: String,
    action: String,
    detail: String? = null,
    time: String,
    hasButtons: Boolean = false,
    isUnread: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Avatar Placeholder
        Box(modifier = Modifier.size(56.dp)) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = CircleShape,
                color = Color(0xFFE0E0E0)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.padding(12.dp),
                    tint = Color.Gray
                )
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black)) {
                        append(name)
                    }
                    withStyle(style = SpanStyle(color = Color.Black)) {
                        append(" $action")
                    }
                },
                fontSize = 15.sp,
                lineHeight = 20.sp
            )
            if (detail != null) {
                Text(
                    text = detail,
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    lineHeight = 18.sp
                )
            }
            Text(
                text = time,
                fontSize = 13.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 2.dp)
            )

            if (hasButtons) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5D9CEC)),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("Accept", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0)),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("Ignore", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Unread dot
        if (isUnread) {
            Box(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2196F3))
            )
        } else {
            Spacer(modifier = Modifier.size(10.dp))
        }
    }
}