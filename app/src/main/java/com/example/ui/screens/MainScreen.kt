package com.example.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.NavTab
import com.example.ui.components.AddEditChildDialog
import com.example.ui.components.BalBhawanBottomNavBar
import com.example.ui.components.BalBhawanTopAppBar
import com.example.ui.components.ChildDetailSheet
import com.example.ui.components.DonationDialog
import com.example.ui.components.NotificationSheet
import com.example.ui.viewmodel.BalBhawanViewModel

@Composable
fun MainScreen(
    viewModel: BalBhawanViewModel = viewModel()
) {
    val activeTab by viewModel.activeTab.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedFilter by viewModel.selectedFilter.collectAsState()
    val filteredChildren by viewModel.filteredChildren.collectAsState()
    val selectedChildForDetail by viewModel.selectedChildForDetail.collectAsState()
    val showAddChildDialog by viewModel.showAddChildDialog.collectAsState()
    val childToEdit by viewModel.childToEdit.collectAsState()
    val showDonationDialog by viewModel.showDonationDialog.collectAsState()
    val selectedCampaignForDonation by viewModel.selectedCampaignForDonation.collectAsState()
    val showNotificationSheet by viewModel.showNotificationSheet.collectAsState()
    val isDarkMode by viewModel.isDarkMode.collectAsState()

    val campaigns by viewModel.campaigns.collectAsState()
    val donations by viewModel.donations.collectAsState()
    val staff by viewModel.staff.collectAsState()
    val notifications by viewModel.notifications.collectAsState()

    Scaffold(
        topBar = {
            BalBhawanTopAppBar(
                unreadNotificationCount = notifications.size,
                onNotificationClick = { viewModel.onToggleNotifications(true) }
            )
        },
        bottomBar = {
            BalBhawanBottomNavBar(
                selectedTab = activeTab,
                onTabSelected = { viewModel.onTabSelected(it) }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        val contentModifier = Modifier.padding(innerPadding)

        when (activeTab) {
            NavTab.DASHBOARD -> {
                DashboardScreen(
                    children = filteredChildren,
                    campaigns = campaigns,
                    onNavigateTab = { viewModel.onTabSelected(it) },
                    onAddChildClick = { viewModel.onOpenAddChild() },
                    onDonateClick = { viewModel.onOpenDonationDialog() },
                    modifier = contentModifier
                )
            }
            NavTab.CHILDREN -> {
                ChildrenScreen(
                    children = filteredChildren,
                    searchQuery = searchQuery,
                    onSearchQueryChange = { viewModel.onSearchQueryChanged(it) },
                    selectedFilter = selectedFilter,
                    filterOptions = viewModel.filterOptions,
                    onFilterSelected = { viewModel.onFilterSelected(it) },
                    onChildClick = { viewModel.onChildClicked(it) },
                    onEditChild = { viewModel.onOpenEditChild(it) },
                    onToggleCheckup = { viewModel.onToggleNeedsCheckup(it) },
                    onDeleteChild = { viewModel.onDeleteChild(it) },
                    onAddChildClick = { viewModel.onOpenAddChild() },
                    modifier = contentModifier
                )
            }
            NavTab.DONATIONS -> {
                DonationsScreen(
                    campaigns = campaigns,
                    donations = donations,
                    onDonateClick = { viewModel.onOpenDonationDialog(it) },
                    modifier = contentModifier
                )
            }
            NavTab.STAFF -> {
                StaffScreen(
                    staffList = staff,
                    modifier = contentModifier
                )
            }
            NavTab.SETTINGS -> {
                SettingsScreen(
                    isDarkMode = isDarkMode,
                    onToggleDarkMode = { viewModel.onToggleDarkMode(it) },
                    modifier = contentModifier
                )
            }
        }

        // Child Detail BottomSheet
        selectedChildForDetail?.let { child ->
            ChildDetailSheet(
                child = child,
                onDismiss = { viewModel.onDismissChildDetail() },
                onEdit = {
                    viewModel.onDismissChildDetail()
                    viewModel.onOpenEditChild(child)
                },
                onToggleCheckup = { viewModel.onToggleNeedsCheckup(child.id) },
                onSponsor = {
                    viewModel.onDismissChildDetail()
                    viewModel.onOpenDonationDialog()
                }
            )
        }

        // Add / Edit Child Dialog
        if (showAddChildDialog) {
            AddEditChildDialog(
                child = childToEdit,
                onDismiss = { viewModel.onDismissAddChildDialog() },
                onSave = { id, name, age, gender, imageUrl, tags, needsCheckup, schoolGrade, caregiver, bloodGroup, hobbies, healthNotes ->
                    viewModel.onSaveChild(
                        id = id,
                        name = name,
                        age = age,
                        gender = gender,
                        imageUrl = imageUrl,
                        tags = tags,
                        needsCheckup = needsCheckup,
                        schoolGrade = schoolGrade,
                        caregiver = caregiver,
                        bloodGroup = bloodGroup,
                        hobbies = hobbies,
                        healthNotes = healthNotes
                    )
                }
            )
        }

        // Donation Dialog
        if (showDonationDialog) {
            DonationDialog(
                campaign = selectedCampaignForDonation,
                onDismiss = { viewModel.onDismissDonationDialog() },
                onSubmitDonation = { donorName, amount, campaignId ->
                    viewModel.onSubmitDonation(donorName, amount, campaignId)
                }
            )
        }

        // Notification Sheet
        if (showNotificationSheet) {
            NotificationSheet(
                notifications = notifications,
                onDismiss = { viewModel.onToggleNotifications(false) },
                onClearAll = { viewModel.onClearNotifications() }
            )
        }
    }
}
