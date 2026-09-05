package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Child
import com.example.model.DonationCampaign
import com.example.model.NavTab
import com.example.ui.theme.ErrorContainer
import com.example.ui.theme.OnErrorContainer
import com.example.ui.theme.OnPrimary
import com.example.ui.theme.OnPrimaryContainer
import com.example.ui.theme.OnSecondaryContainer
import com.example.ui.theme.OnTertiaryFixedVariant
import com.example.ui.theme.OutlineVariant
import com.example.ui.theme.Primary
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.SecondaryContainer
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.TertiaryFixedDim

@Composable
fun DashboardScreen(
    children: List<Child>,
    campaigns: List<DonationCampaign>,
    onNavigateTab: (NavTab) -> Unit,
    onAddChildClick: () -> Unit,
    onDonateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val totalChildren = children.size
    val inSchoolCount = children.count { it.schoolGrade.contains("Grade", ignoreCase = true) || it.schoolGrade.contains("School", ignoreCase = true) }
    val needsCheckupCount = children.count { it.needsCheckup }
    val totalFundsRaised = campaigns.sumOf { it.raisedAmount }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Welcome Banner
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("dashboard_welcome_card")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ChildCare,
                                contentDescription = null,
                                tint = OnPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Bal Bhawan Shelter Home",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = OnPrimary
                            )
                            Text(
                                text = "Safe Haven & Holistic Development",
                                style = MaterialTheme.typography.bodySmall,
                                color = OnPrimary.copy(alpha = 0.85f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "48 children under resident shelter care. Daily classes, nutrition, and extracurricular activities running on schedule.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnPrimary.copy(alpha = 0.95f),
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { onNavigateTab(NavTab.CHILDREN) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Primary
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("View Children", fontWeight = FontWeight.SemiBold)
                        }

                        OutlinedButton(
                            onClick = onDonateClick,
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = OnPrimary),
                            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.7f)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Support Fund", fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }

        // Stats Grid
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "Key Metrics",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCard(
                        title = "Resident Children",
                        value = "$totalChildren",
                        subtitle = "Shelter capacity: 60",
                        icon = Icons.Default.ChildCare,
                        iconBg = SecondaryContainer,
                        iconTint = OnSecondaryContainer,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateTab(NavTab.CHILDREN) }
                    )

                    StatCard(
                        title = "Enrolled in School",
                        value = "$inSchoolCount",
                        subtitle = "Formal education",
                        icon = Icons.Default.School,
                        iconBg = TertiaryFixedDim,
                        iconTint = OnTertiaryFixedVariant,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateTab(NavTab.CHILDREN) }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCard(
                        title = "Medical Attention",
                        value = "$needsCheckupCount",
                        subtitle = "Checkup scheduled",
                        icon = Icons.Default.LocalHospital,
                        iconBg = ErrorContainer,
                        iconTint = OnErrorContainer,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateTab(NavTab.CHILDREN) }
                    )

                    StatCard(
                        title = "Funds Raised",
                        value = "₹${(totalFundsRaised / 1000).toInt()}k",
                        subtitle = "Active drives",
                        icon = Icons.Default.VolunteerActivism,
                        iconBg = PrimaryContainer,
                        iconTint = OnPrimaryContainer,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateTab(NavTab.DONATIONS) }
                    )
                }
            }
        }

        // Today's Routine Schedule
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                border = BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Schedule,
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Today's Schedule & Routine",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    ScheduleRow("07:00 AM", "Morning Yoga & Nutritious Breakfast", "Caregiver Sunita Devi")
                    ScheduleRow("08:30 AM", "School Bus Departure & Early Learning", "All batches")
                    ScheduleRow("01:30 PM", "Lunch & Afternoon Rest Hour", "Kitchen Team")
                    ScheduleRow("04:30 PM", "Sports, Art & Vocational Practice", "Instructor Rajesh Gupta")
                    ScheduleRow("06:30 PM", "Evening Study & Homework Tutoring", "Teacher Meenakshi Rao")
                    ScheduleRow("08:00 PM", "Dinner & Storytelling Session", "Care Team")
                }
            }
        }

        // Quick Action Shortcuts
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Quick Administrative Actions",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = onAddChildClick,
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Primary),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.PersonAdd, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Add Child", fontSize = 13.sp)
                        }

                        OutlinedButton(
                            onClick = { onNavigateTab(NavTab.STAFF) },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Staff Duty", fontSize = 13.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    subtitle: String,
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        border = BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.4f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(iconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ScheduleRow(time: String, activity: String, lead: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = time,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = Primary,
            modifier = Modifier.width(72.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = activity,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = lead,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
