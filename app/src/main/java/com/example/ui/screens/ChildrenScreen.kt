package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Child
import com.example.ui.components.ChildCard
import com.example.ui.theme.OnPrimary
import com.example.ui.theme.OnTertiaryFixed
import com.example.ui.theme.OutlineVariant
import com.example.ui.theme.Primary
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.TertiaryFixed

@Composable
fun ChildrenScreen(
    children: List<Child>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedFilter: String,
    filterOptions: List<String>,
    onFilterSelected: (String) -> Unit,
    onChildClick: (Child) -> Unit,
    onEditChild: (Child) -> Unit,
    onToggleCheckup: (String) -> Unit,
    onDeleteChild: (String) -> Unit,
    onAddChildClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 88.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Search Input & Filters Header
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Search Bar
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = onSearchQueryChange,
                        placeholder = {
                            Text(
                                text = "Search children by name...",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = MaterialTheme.colorScheme.outline
                            )
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { onSearchQueryChange("") }) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Clear search",
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = SurfaceContainerLowest,
                            unfocusedContainerColor = SurfaceContainerLowest,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = OutlineVariant.copy(alpha = 0.6f)
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("children_search_input")
                    )

                    // Horizontal Filter Chips
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        filterOptions.forEach { filter ->
                            val isSelected = filter == selectedFilter

                            if (isSelected) {
                                // Selected Chip (bg-tertiary-fixed text-on-tertiary-fixed)
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(50))
                                        .background(TertiaryFixed)
                                        .clickable(
                                            interactionSource = null,
                                            indication = ripple()
                                        ) { onFilterSelected(filter) }
                                        .padding(horizontal = 16.dp, vertical = 9.dp)
                                        .testTag("filter_chip_$filter")
                                ) {
                                    Text(
                                        text = filter,
                                        style = MaterialTheme.typography.labelLarge.copy(
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 14.sp
                                        ),
                                        color = OnTertiaryFixed
                                    )
                                }
                            } else {
                                // Unselected Chip (bg-surface-container-lowest border border-outline-variant text-on-surface-variant)
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(50))
                                        .background(SurfaceContainerLowest)
                                        .border(1.dp, OutlineVariant.copy(alpha = 0.5f), RoundedCornerShape(50))
                                        .clickable(
                                            interactionSource = null,
                                            indication = ripple()
                                        ) { onFilterSelected(filter) }
                                        .padding(horizontal = 16.dp, vertical = 9.dp)
                                        .testTag("filter_chip_$filter")
                                ) {
                                    Text(
                                        text = filter,
                                        style = MaterialTheme.typography.labelLarge.copy(
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 14.sp
                                        ),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Children Cards List
            if (children.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 48.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceContainerHigh),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No children records found",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Try adjusting your search query or filter selection.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                items(children, key = { it.id }) { child ->
                    ChildCard(
                        child = child,
                        onClick = { onChildClick(child) },
                        onEdit = { onEditChild(child) },
                        onToggleCheckup = { onToggleCheckup(child.id) },
                        onDelete = { onDeleteChild(child.id) }
                    )
                }
            }
        }

        // Floating Action Button (+)
        FloatingActionButton(
            onClick = onAddChildClick,
            shape = RoundedCornerShape(16.dp),
            containerColor = Primary,
            contentColor = OnPrimary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp)
                .size(56.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp))
                .testTag("add_child_fab")
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add New Child",
                modifier = Modifier.size(28.dp)
            )
        }
    }
}
