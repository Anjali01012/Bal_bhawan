package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.BalBhawanRepository
import com.example.model.Child
import com.example.model.DonationCampaign
import com.example.model.DonationRecord
import com.example.model.NavTab
import com.example.model.ShelterNotification
import com.example.model.StaffMember
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.util.UUID

class BalBhawanViewModel(
    private val repository: BalBhawanRepository = BalBhawanRepository()
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedFilter = MutableStateFlow("All Ages")
    val selectedFilter: StateFlow<String> = _selectedFilter.asStateFlow()

    val filterOptions = listOf(
        "All Ages",
        "0-5 Years",
        "6-10 Years",
        "Needs Attention",
        "Vocational",
        "Sports",
        "Education"
    )

    private val _activeTab = MutableStateFlow(NavTab.CHILDREN)
    val activeTab: StateFlow<NavTab> = _activeTab.asStateFlow()

    private val _selectedChildForDetail = MutableStateFlow<Child?>(null)
    val selectedChildForDetail: StateFlow<Child?> = _selectedChildForDetail.asStateFlow()

    private val _childToEdit = MutableStateFlow<Child?>(null)
    val childToEdit: StateFlow<Child?> = _childToEdit.asStateFlow()

    private val _showAddChildDialog = MutableStateFlow(false)
    val showAddChildDialog: StateFlow<Boolean> = _showAddChildDialog.asStateFlow()

    private val _showDonationDialog = MutableStateFlow(false)
    val showDonationDialog: StateFlow<Boolean> = _showDonationDialog.asStateFlow()

    private val _selectedCampaignForDonation = MutableStateFlow<DonationCampaign?>(null)
    val selectedCampaignForDonation: StateFlow<DonationCampaign?> = _selectedCampaignForDonation.asStateFlow()

    private val _showNotificationSheet = MutableStateFlow(false)
    val showNotificationSheet: StateFlow<Boolean> = _showNotificationSheet.asStateFlow()

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    val campaigns: StateFlow<List<DonationCampaign>> = repository.campaigns
    val donations: StateFlow<List<DonationRecord>> = repository.donations
    val staff: StateFlow<List<StaffMember>> = repository.staff
    val notifications: StateFlow<List<ShelterNotification>> = repository.notifications

    val filteredChildren: StateFlow<List<Child>> = combine(
        repository.children,
        _searchQuery,
        _selectedFilter
    ) { children, query, filter ->
        children.filter { child ->
            val matchesQuery = query.isBlank() ||
                    child.name.contains(query, ignoreCase = true) ||
                    child.schoolGrade.contains(query, ignoreCase = true) ||
                    child.tags.any { it.contains(query, ignoreCase = true) }

            val matchesFilter = when (filter) {
                "All Ages" -> true
                "0-5 Years" -> child.age in 0..5
                "6-10 Years" -> child.age in 6..10
                "Needs Attention" -> child.needsCheckup || child.tags.any { it.equals("Needs Checkup", ignoreCase = true) }
                "Vocational" -> child.tags.any { it.equals("Vocational", ignoreCase = true) }
                "Sports" -> child.tags.any { it.equals("Sports", ignoreCase = true) }
                "Education" -> child.tags.any { it.equals("Education", ignoreCase = true) }
                else -> true
            }

            matchesQuery && matchesFilter
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onFilterSelected(filter: String) {
        _selectedFilter.value = filter
    }

    fun onTabSelected(tab: NavTab) {
        _activeTab.value = tab
    }

    fun onChildClicked(child: Child) {
        _selectedChildForDetail.value = child
    }

    fun onDismissChildDetail() {
        _selectedChildForDetail.value = null
    }

    fun onOpenAddChild() {
        _childToEdit.value = null
        _showAddChildDialog.value = true
    }

    fun onOpenEditChild(child: Child) {
        _childToEdit.value = child
        _showAddChildDialog.value = true
    }

    fun onDismissAddChildDialog() {
        _showAddChildDialog.value = false
        _childToEdit.value = null
    }

    fun onSaveChild(
        id: String?,
        name: String,
        age: Int,
        gender: String,
        imageUrl: String?,
        tags: List<String>,
        needsCheckup: Boolean,
        schoolGrade: String,
        caregiver: String,
        bloodGroup: String,
        hobbies: String,
        healthNotes: String
    ) {
        if (id != null) {
            val existing = repository.children.value.find { it.id == id }
            if (existing != null) {
                val updated = existing.copy(
                    name = name,
                    age = age,
                    gender = gender,
                    imageUrl = imageUrl?.ifBlank { null },
                    tags = tags,
                    needsCheckup = needsCheckup,
                    schoolGrade = schoolGrade,
                    caregiver = caregiver,
                    bloodGroup = bloodGroup,
                    hobbies = hobbies,
                    healthNotes = healthNotes
                )
                repository.updateChild(updated)
                if (_selectedChildForDetail.value?.id == id) {
                    _selectedChildForDetail.value = updated
                }
            }
        } else {
            val newChild = Child(
                id = "c_${UUID.randomUUID()}",
                name = name,
                age = age,
                gender = gender,
                imageUrl = imageUrl?.ifBlank { null },
                tags = tags,
                needsCheckup = needsCheckup,
                schoolGrade = schoolGrade,
                caregiver = caregiver,
                bloodGroup = bloodGroup,
                hobbies = hobbies,
                healthNotes = healthNotes
            )
            repository.addChild(newChild)
        }
        _showAddChildDialog.value = false
        _childToEdit.value = null
    }

    fun onDeleteChild(childId: String) {
        repository.deleteChild(childId)
        if (_selectedChildForDetail.value?.id == childId) {
            _selectedChildForDetail.value = null
        }
    }

    fun onToggleNeedsCheckup(childId: String) {
        repository.toggleNeedsCheckup(childId)
        val updated = repository.children.value.find { it.id == childId }
        if (_selectedChildForDetail.value?.id == childId && updated != null) {
            _selectedChildForDetail.value = updated
        }
    }

    fun onOpenDonationDialog(campaign: DonationCampaign? = null) {
        _selectedCampaignForDonation.value = campaign ?: campaigns.value.firstOrNull()
        _showDonationDialog.value = true
    }

    fun onDismissDonationDialog() {
        _showDonationDialog.value = false
        _selectedCampaignForDonation.value = null
    }

    fun onSubmitDonation(donorName: String, amount: Double, campaignId: String) {
        repository.recordDonation(donorName, amount, campaignId)
        _showDonationDialog.value = false
        _selectedCampaignForDonation.value = null
    }

    fun onToggleNotifications(show: Boolean) {
        _showNotificationSheet.value = show
    }

    fun onToggleDarkMode(isDark: Boolean) {
        _isDarkMode.value = isDark
    }

    fun onClearNotifications() {
        repository.clearNotifications()
    }
}
