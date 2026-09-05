package com.example.data

import com.example.model.Child
import com.example.model.DonationCampaign
import com.example.model.DonationRecord
import com.example.model.NotificationType
import com.example.model.ShelterNotification
import com.example.model.StaffMember
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class BalBhawanRepository {

    private val initialChildren = listOf(
        Child(
            id = "c1",
            name = "Aarav Sharma",
            age = 8,
            gender = "Boy",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuD_X8ilPzU2xJsevZvOsRjJDQXHUoB6y3KjGD5OI6v8MtAR1DjuR0A4whI28mQ0kJYXXM5Zr6_JAhRFFzLmIU0DCrxzwFI2N9ZHuPaEDBkCTvI-3er-c6Fs9K_cdsIe3XKb8c5m5xQS1mHZA6TJURQ_nMO_FPoEcf2qb06dkeFpaOLoGekXGuhmHcBsFiooftOciasUTB61DKH88SPBedwIOz9O4CdllBGHhzia5fGjX-Y45oqe6jU",
            tags = listOf("Education", "Joined 2022"),
            needsCheckup = false,
            admissionYear = 2022,
            schoolGrade = "3rd Grade - Govt Model School",
            caregiver = "Sunita Devi",
            bloodGroup = "O+",
            hobbies = "Painting, Storytelling, Science quizzes",
            healthNotes = "Good general health. Dental checkup cleared last month.",
            emergencyContact = "+91 98101 23456"
        ),
        Child(
            id = "c2",
            name = "Diya Patel",
            age = 5,
            gender = "Girl",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuASHuLIjkhWg67wBQxecv11p3DWXnjKL58ln4yLC7YaFtwBSOGAMQMhwutajsv7Vx837QP2n7U1UmKmzK--xO5PhGfCwieDxZ_tqc_N7huf1c61fyT5hlCtZTlITCRlPFq2Km0VLNvGtwkp7P2Kj5UAMgN4BEK7_FIkthJHfDxaQFdnumgXt_W1FyMG2h4enHE1jY9MAqf_7dLHedGV-OpuvY840kuzZVL-rVi_VPOupLAtyFcpMmM",
            tags = listOf("Needs Checkup"),
            needsCheckup = true,
            admissionYear = 2023,
            schoolGrade = "Kindergarten - Bal Vikas",
            caregiver = "Meenakshi Rao",
            bloodGroup = "A+",
            hobbies = "Coloring, Clay modeling, Rhymes",
            healthNotes = "Mild seasonal cold. Scheduled for pediatric checkup this Friday.",
            emergencyContact = "+91 98102 34567"
        ),
        Child(
            id = "c3",
            name = "Rohan Singh",
            age = 10,
            gender = "Boy",
            imageUrl = null,
            tags = listOf("Sports"),
            needsCheckup = false,
            admissionYear = 2021,
            schoolGrade = "5th Grade - National Public School",
            caregiver = "Sunita Devi",
            bloodGroup = "B+",
            hobbies = "Cricket, Football, Running track",
            healthNotes = "Excellent physical fitness. Won inter-school 100m sprint.",
            emergencyContact = "+91 98103 45678"
        ),
        Child(
            id = "c4",
            name = "Vikram Kumar",
            age = 14,
            gender = "Boy",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDniBeo9c-tCXuLKm3YTIqvE_bb9J9F2fTPEHzp6VFolRChFf91k9Btqx-VvummkeTu2TLcZYX-KdQRx_6EIeIvQWRIYqrhm78oUHptJnq5aQYgQGkhLZMKMPWVmyHUiKtnoOCIEz3yGnVO-8vUXbeskqt2LYWB2ThX_gDq_DpiSbHXDMtu3Szkh_CArkxNHr3T1kZl91prc5sE6x4KamHrXN2FiZf91m9zggJi3NGtCS2Z-bvFEqo",
            tags = listOf("Vocational"),
            needsCheckup = false,
            admissionYear = 2020,
            schoolGrade = "9th Grade - Vocational Skill Center",
            caregiver = "Rajesh Gupta",
            bloodGroup = "AB+",
            hobbies = "Computer Hardware, Carpentry, Robotics",
            healthNotes = "Vision test recommended for reading glasses.",
            emergencyContact = "+91 98104 56789"
        ),
        Child(
            id = "c5",
            name = "Ananya Gupta",
            age = 7,
            gender = "Girl",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuASHuLIjkhWg67wBQxecv11p3DWXnjKL58ln4yLC7YaFtwBSOGAMQMhwutajsv7Vx837QP2n7U1UmKmzK--xO5PhGfCwieDxZ_tqc_N7huf1c61fyT5hlCtZTlITCRlPFq2Km0VLNvGtwkp7P2Kj5UAMgN4BEK7_FIkthJHfDxaQFdnumgXt_W1FyMG2h4enHE1jY9MAqf_7dLHedGV-OpuvY840kuzZVL-rVi_VPOupLAtyFcpMmM",
            tags = listOf("Education", "Joined 2023"),
            needsCheckup = false,
            admissionYear = 2023,
            schoolGrade = "2nd Grade - Saraswati Vidya",
            caregiver = "Meenakshi Rao",
            bloodGroup = "O-",
            hobbies = "Classical Dance, Poetry, Math Puzzles",
            healthNotes = "Healthy growth curve.",
            emergencyContact = "+91 98105 67890"
        ),
        Child(
            id = "c6",
            name = "Kabir Das",
            age = 4,
            gender = "Boy",
            imageUrl = null,
            tags = listOf("Needs Checkup"),
            needsCheckup = true,
            admissionYear = 2024,
            schoolGrade = "Playgroup - Early Care",
            caregiver = "Sunita Devi",
            bloodGroup = "B-",
            hobbies = "Building Blocks, Toy cars",
            healthNotes = "Booster vaccination due next Monday.",
            emergencyContact = "+91 98106 78901"
        ),
        Child(
            id = "c7",
            name = "Meera Sen",
            age = 12,
            gender = "Girl",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuASHuLIjkhWg67wBQxecv11p3DWXnjKL58ln4yLC7YaFtwBSOGAMQMhwutajsv7Vx837QP2n7U1UmKmzK--xO5PhGfCwieDxZ_tqc_N7huf1c61fyT5hlCtZTlITCRlPFq2Km0VLNvGtwkp7P2Kj5UAMgN4BEK7_FIkthJHfDxaQFdnumgXt_W1FyMG2h4enHE1jY9MAqf_7dLHedGV-OpuvY840kuzZVL-rVi_VPOupLAtyFcpMmM",
            tags = listOf("Education", "Sports"),
            needsCheckup = false,
            admissionYear = 2021,
            schoolGrade = "7th Grade - St. Xavier Community",
            caregiver = "Rajesh Gupta",
            bloodGroup = "A-",
            hobbies = "Badminton, Science fair projects",
            healthNotes = "All vitals normal.",
            emergencyContact = "+91 98107 89012"
        ),
        Child(
            id = "c8",
            name = "Tushar Verma",
            age = 15,
            gender = "Boy",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDniBeo9c-tCXuLKm3YTIqvE_bb9J9F2fTPEHzp6VFolRChFf91k9Btqx-VvummkeTu2TLcZYX-KdQRx_6EIeIvQWRIYqrhm78oUHptJnq5aQYgQGkhLZMKMPWVmyHUiKtnoOCIEz3yGnVO-8vUXbeskqt2LYWB2ThX_gDq_DpiSbHXDMtu3Szkh_CArkxNHr3T1kZl91prc5sE6x4KamHrXN2FiZf91m9zggJi3NGtCS2Z-bvFEqo",
            tags = listOf("Vocational", "Joined 2022"),
            needsCheckup = false,
            admissionYear = 2022,
            schoolGrade = "10th Grade - Technical Academy",
            caregiver = "Rajesh Gupta",
            bloodGroup = "O+",
            hobbies = "Electrician training, Guitar, Chess",
            healthNotes = "General fitness certified.",
            emergencyContact = "+91 98108 90123"
        )
    )

    private val initialCampaigns = listOf(
        DonationCampaign(
            id = "camp1",
            title = "Winter Warmth & Clothes Drive",
            description = "Providing warm jackets, thermal wear, and cozy blankets for all 48 resident children.",
            targetAmount = 75000.0,
            raisedAmount = 58200.0,
            donorsCount = 42,
            category = "Essentials",
            iconName = "dry_cleaning"
        ),
        DonationCampaign(
            id = "camp2",
            title = "Annual School Books & STEM Kits",
            description = "Textbooks, notebooks, stationary, and interactive science kits for the new academic semester.",
            targetAmount = 120000.0,
            raisedAmount = 94500.0,
            donorsCount = 68,
            category = "Education",
            iconName = "menu_book"
        ),
        DonationCampaign(
            id = "camp3",
            title = "Daily Nutrition & Fresh Milk Fund",
            description = "Ensuring high-protein daily meals, fruits, and nutritious milk for holistic growth.",
            targetAmount = 60000.0,
            raisedAmount = 45000.0,
            donorsCount = 35,
            category = "Nutrition",
            iconName = "restaurant"
        ),
        DonationCampaign(
            id = "camp4",
            title = "Computer Lab & Digital Learning",
            description = "Adding 6 refurbished computers and high-speed learning software for vocational batches.",
            targetAmount = 150000.0,
            raisedAmount = 62000.0,
            donorsCount = 29,
            category = "Vocational",
            iconName = "computer"
        )
    )

    private val initialDonations = listOf(
        DonationRecord("d1", "Aakash Singhal", 5000.0, "Annual School Books & STEM Kits", "Today, 11:30 AM"),
        DonationRecord("d2", "Priya & Sandeep Malhotra", 10000.0, "Winter Warmth & Clothes Drive", "Yesterday"),
        DonationRecord("d3", "Anand Foundation", 25000.0, "Daily Nutrition & Fresh Milk Fund", "3 days ago"),
        DonationRecord("d4", "Kiran Rao", 2500.0, "Winter Warmth & Clothes Drive", "4 days ago"),
        DonationRecord("d5", "Sneha Joshi", 7500.0, "Computer Lab & Digital Learning", "Last week")
    )

    private val initialStaff = listOf(
        StaffMember("s1", "Sunita Devi", "Head Caregiver", "Morning (07:00 - 15:00)", "+91 98765 11111", "sunita.d@balbhawan.ngo", "Caregiving", "SD"),
        StaffMember("s2", "Dr. Alok Nath", "Resident Medical Officer", "Visiting (10:00 - 14:00)", "+91 98765 22222", "alok.n@balbhawan.ngo", "Healthcare", "AN"),
        StaffMember("s3", "Meenakshi Rao", "Primary Educator & Tutor", "Full Day (09:00 - 17:00)", "+91 98765 33333", "meenakshi.r@balbhawan.ngo", "Education", "MR"),
        StaffMember("s4", "Rajesh Gupta", "Vocational Instructor", "Evening (14:00 - 20:00)", "+91 98765 44444", "rajesh.g@balbhawan.ngo", "Skills & Sports", "RG"),
        StaffMember("s5", "Kavita Sharma", "Nutrition & Kitchen Lead", "Morning (06:00 - 14:00)", "+91 98765 55555", "kavita.s@balbhawan.ngo", "Nutrition", "KS")
    )

    private val initialNotifications = listOf(
        ShelterNotification(
            id = "n1",
            title = "Pediatric Health Checkup Due",
            message = "Diya Patel & Kabir Das are scheduled for medical evaluation this Friday.",
            timeAgo = "10 mins ago",
            type = NotificationType.MEDICAL
        ),
        ShelterNotification(
            id = "n2",
            title = "New Donation Received",
            message = "Aakash Singhal donated ₹5,000 to School Books Fund.",
            timeAgo = "1 hour ago",
            type = NotificationType.DONATION
        ),
        ShelterNotification(
            id = "n3",
            title = "Science Exhibition Tomorrow",
            message = "Vocational & high school children will present projects at 11:00 AM.",
            timeAgo = "3 hours ago",
            type = NotificationType.EVENT
        )
    )

    private val _children = MutableStateFlow(initialChildren)
    val children: StateFlow<List<Child>> = _children.asStateFlow()

    private val _campaigns = MutableStateFlow(initialCampaigns)
    val campaigns: StateFlow<List<DonationCampaign>> = _campaigns.asStateFlow()

    private val _donations = MutableStateFlow(initialDonations)
    val donations: StateFlow<List<DonationRecord>> = _donations.asStateFlow()

    private val _staff = MutableStateFlow(initialStaff)
    val staff: StateFlow<List<StaffMember>> = _staff.asStateFlow()

    private val _notifications = MutableStateFlow(initialNotifications)
    val notifications: StateFlow<List<ShelterNotification>> = _notifications.asStateFlow()

    fun addChild(child: Child) {
        _children.value = listOf(child) + _children.value
    }

    fun updateChild(updated: Child) {
        _children.value = _children.value.map { if (it.id == updated.id) updated else it }
    }

    fun deleteChild(id: String) {
        _children.value = _children.value.filter { it.id != id }
    }

    fun toggleNeedsCheckup(id: String) {
        _children.value = _children.value.map { child ->
            if (child.id == id) {
                val newCheckup = !child.needsCheckup
                val newTags = if (newCheckup) {
                    if ("Needs Checkup" !in child.tags) child.tags + "Needs Checkup" else child.tags
                } else {
                    child.tags.filter { it != "Needs Checkup" }
                }
                child.copy(needsCheckup = newCheckup, tags = newTags)
            } else child
        }
    }

    fun recordDonation(donorName: String, amount: Double, campaignId: String) {
        val targetCamp = _campaigns.value.find { it.id == campaignId }
        val campaignTitle = targetCamp?.title ?: "General Shelter Welfare"

        val newRecord = DonationRecord(
            id = UUID.randomUUID().toString(),
            donorName = donorName.ifBlank { "Generous Supporter" },
            amount = amount,
            campaignTitle = campaignTitle,
            date = "Just now"
        )
        _donations.value = listOf(newRecord) + _donations.value

        _campaigns.value = _campaigns.value.map { camp ->
            if (camp.id == campaignId) {
                camp.copy(
                    raisedAmount = camp.raisedAmount + amount,
                    donorsCount = camp.donorsCount + 1
                )
            } else camp
        }

        _notifications.value = listOf(
            ShelterNotification(
                id = UUID.randomUUID().toString(),
                title = "New Donation Received",
                message = "${newRecord.donorName} contributed ₹${amount.toInt()} to $campaignTitle.",
                timeAgo = "Just now",
                type = NotificationType.DONATION
            )
        ) + _notifications.value
    }

    fun addStaff(staffMember: StaffMember) {
        _staff.value = listOf(staffMember) + _staff.value
    }

    fun clearNotifications() {
        _notifications.value = emptyList()
    }
}
