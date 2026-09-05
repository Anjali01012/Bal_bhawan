package com.example.model

data class Child(
    val id: String,
    val name: String,
    val age: Int,
    val gender: String = "Boy",
    val imageUrl: String? = null,
    val tags: List<String> = emptyList(),
    val needsCheckup: Boolean = false,
    val admissionYear: Int = 2022,
    val schoolGrade: String = "3rd Grade",
    val caregiver: String = "Sunita Sharma",
    val bloodGroup: String = "B+",
    val hobbies: String = "Drawing, Cricket",
    val healthNotes: String = "Routine vaccinations up to date.",
    val emergencyContact: String = "+91 98765 43210"
)

data class DonationCampaign(
    val id: String,
    val title: String,
    val description: String,
    val targetAmount: Double,
    val raisedAmount: Double,
    val donorsCount: Int,
    val category: String,
    val iconName: String
)

data class DonationRecord(
    val id: String,
    val donorName: String,
    val amount: Double,
    val campaignTitle: String,
    val date: String,
    val isAnonymous: Boolean = false
)

data class StaffMember(
    val id: String,
    val name: String,
    val role: String,
    val shift: String,
    val phone: String,
    val email: String,
    val department: String,
    val initials: String
)

data class ShelterNotification(
    val id: String,
    val title: String,
    val message: String,
    val timeAgo: String,
    val type: NotificationType,
    val isRead: Boolean = false
)

enum class NotificationType {
    MEDICAL,
    DONATION,
    EVENT,
    GENERAL
}

enum class NavTab {
    DASHBOARD,
    CHILDREN,
    DONATIONS,
    STAFF,
    SETTINGS
}
