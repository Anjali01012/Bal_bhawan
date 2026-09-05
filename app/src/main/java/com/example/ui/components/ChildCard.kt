package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.model.Child
import com.example.ui.theme.ErrorContainer
import com.example.ui.theme.OnErrorContainer
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.OnTertiaryFixedVariant
import com.example.ui.theme.OutlineVariant
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.TertiaryFixedDim

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChildCard(
    child: Child,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onToggleCheckup: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceContainerLowest
        ),
        border = BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.4f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .testTag("child_card_${child.id}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header Row: Avatar + Name/Age + More Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    // Avatar
                    if (!child.imageUrl.isNullOrBlank()) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(child.imageUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Portrait of ${child.name}",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(SurfaceContainerHigh)
                        )
                    } else {
                        // Letter Initial Avatar
                        val initial = child.name.firstOrNull()?.uppercase() ?: "C"
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(SurfaceContainerHigh),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = initial,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = OnSurfaceVariant
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = child.name,
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${child.age} Years Old",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // More Menu Button
                Box {
                    IconButton(
                        onClick = { menuExpanded = true },
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("child_menu_button_${child.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Options for ${child.name}",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("View Full Profile") },
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                            onClick = {
                                menuExpanded = false
                                onClick()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(if (child.needsCheckup) "Mark Checkup Done" else "Mark Needs Checkup") },
                            leadingIcon = { Icon(Icons.Default.MedicalServices, contentDescription = null) },
                            onClick = {
                                menuExpanded = false
                                onToggleCheckup()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Edit Details") },
                            leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null) },
                            onClick = {
                                menuExpanded = false
                                onEdit()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Delete Record", color = MaterialTheme.colorScheme.error) },
                            leadingIcon = { Icon(Icons.Default.Delete, contentDescription = null, tint = MaterialTheme.colorScheme.error) },
                            onClick = {
                                menuExpanded = false
                                onDelete()
                            }
                        )
                    }
                }
            }

            // Tags Row
            if (child.tags.isNotEmpty()) {
                Spacer(modifier = Modifier.padding(top = 10.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    child.tags.forEach { tag ->
                        ChildTagChip(tag = tag)
                    }
                }
            }
        }
    }
}

@Composable
fun ChildTagChip(tag: String) {
    val isNeedsCheckup = tag.equals("Needs Checkup", ignoreCase = true) || tag.equals("Needs Attention", ignoreCase = true)
    val isJoined = tag.startsWith("Joined", ignoreCase = true)

    val (bgColor, textColor) = when {
        isNeedsCheckup -> Pair(ErrorContainer, OnErrorContainer)
        isJoined -> Pair(SurfaceContainer, OnSurfaceVariant)
        else -> Pair(TertiaryFixedDim, OnTertiaryFixedVariant)
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(bgColor)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = tag,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
            ),
            color = textColor
        )
    }
}
