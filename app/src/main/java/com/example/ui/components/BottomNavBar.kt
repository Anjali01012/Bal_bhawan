package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.NavTab
import com.example.ui.theme.OnSecondaryContainer
import com.example.ui.theme.SecondaryContainer

data class NavItem(
    val tab: NavTab,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun BalBhawanBottomNavBar(
    selectedTab: NavTab,
    onTabSelected: (NavTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        NavItem(NavTab.DASHBOARD, "Dashboard", Icons.Filled.Dashboard, Icons.Outlined.Dashboard),
        NavItem(NavTab.CHILDREN, "Children", Icons.Filled.Face, Icons.Outlined.Face),
        NavItem(NavTab.DONATIONS, "Donations", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder),
        NavItem(NavTab.STAFF, "Staff", Icons.Filled.Badge, Icons.Outlined.Badge),
        NavItem(NavTab.SETTINGS, "Settings", Icons.Filled.Settings, Icons.Outlined.Settings)
    )

    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        shadowElevation = 8.dp,
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = item.tab == selectedTab

                if (isSelected) {
                    // Active Tab with Pill styling matching design system and HTML
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(SecondaryContainer)
                            .clickable(
                                interactionSource = null,
                                indication = ripple(bounded = true)
                            ) { onTabSelected(item.tab) }
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .testTag("nav_tab_${item.tab.name.lowercase()}")
                    ) {
                        Icon(
                            imageVector = item.selectedIcon,
                            contentDescription = item.label,
                            tint = OnSecondaryContainer,
                            modifier = Modifier.size(22.dp)
                        )
                        Text(
                            text = item.label,
                            color = OnSecondaryContainer,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            lineHeight = 14.sp
                        )
                    }
                } else {
                    // Inactive Tab
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable(
                                interactionSource = null,
                                indication = ripple(bounded = true)
                            ) { onTabSelected(item.tab) }
                            .padding(horizontal = 8.dp, vertical = 6.dp)
                            .testTag("nav_tab_${item.tab.name.lowercase()}")
                    ) {
                        Icon(
                            imageVector = item.unselectedIcon,
                            contentDescription = item.label,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(22.dp)
                        )
                        Text(
                            text = item.label,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 14.sp
                        )
                    }
                }
            }
        }
    }
}
