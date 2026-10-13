package com.example.model

enum class ConsultationType(
    val title: String,
    val subtitle: String,
    val defaultFee: Double,
    val durationText: String,
    val tag: String
) {
    IN_PERSON(
        title = "In-Person Visit",
        subtitle = "Face-to-face clinical consultation at St. Jude Medical Plaza, Suite 402.",
        defaultFee = 48.0,
        durationText = "30–45 mins duration",
        tag = "Popular"
    ),
    VIDEO(
        title = "Video Consultation",
        subtitle = "High-definition secure tele-health call from the comfort of your home.",
        defaultFee = 35.0,
        durationText = "25–30 mins duration",
        tag = "Fastest"
    )
}

enum class AppointmentStatus {
    CONFIRMED,
    COMPLETED,
    CANCELLED
}

data class Doctor(
    val id: String,
    val name: String,
    val degrees: String = "MBBS, MD",
    val specialty: String,
    val hospital: String,
    val rating: Float,
    val reviewCount: Int,
    val experienceYears: Int,
    val consultationFee: Double,
    val avatarUrl: String,
    val availableToday: Boolean = true,
    val nextSlotText: String,
    val bio: String = "Specialist with deep clinical expertise in preventative and diagnostic medical care.",
    val supportedTypes: List<ConsultationType> = listOf(ConsultationType.IN_PERSON, ConsultationType.VIDEO),
    val locationCity: String = "San Francisco, CA",
    val postalCode: String = "94102",
    val gender: String = "Male"
)

data class Specialty(
    val id: String,
    val name: String,
    val iconName: String,
    val doctorCount: Int
)

data class Appointment(
    val id: String,
    val doctorId: String,
    val doctorName: String,
    val doctorSpecialty: String,
    val doctorHospital: String,
    val doctorAvatarUrl: String,
    val dateText: String, // e.g. "Thu, Oct 24, 2024"
    val timeSlot: String, // e.g. "10:30 AM"
    val consultationType: ConsultationType,
    val fee: Double,
    val status: AppointmentStatus = AppointmentStatus.CONFIRMED,
    val patientName: String,
    val notes: String = "",
    val daysLeftText: String = "1d left"
)

data class PatientReview(
    val id: String,
    val patientName: String,
    val initials: String,
    val rating: Int,
    val timeAgo: String,
    val comment: String,
    val consultationType: String,
    val isVerified: Boolean = true,
    val helpfulCount: Int = 14
)

data class PatientProfile(
    val fullName: String = "Alex Vance",
    val email: String = "patient@medixin.health",
    val dob: String = "1998-05-14",
    val gender: String = "Male",
    val phone: String = "(555) 234-8910",
    val address: String = "452 Elm Street, Suite 201",
    val avatarUrl: String = "https://lh3.googleusercontent.com/aida-public/AB6AXuAH_qmOtZmQ8IqEC299Thr4skYSduhx0FKnvLrazThRsENAtQ3-v4bIsC5Z8Z35lW6q7MjYf5e_aav_quEiedc4sdVKX13a0N2_xL1kBnDIqjGz_7OFmRtUJ9W8MDasAf6EKrNYrvcHFX_kwvgaBnAWiWu_EQ3E5UJh2QmWAKv4ns_Lh5BVYLlPYuSrIPKIp61H9zFcwOd-8vCuzm8gtW3erYnHysxY0G7kSF7eVFMfW4mZonw6keI6"
)

data class FilterCriteria(
    val selectedSpecialties: Set<String> = setOf("Cardiology"),
    val location: String = "San Francisco, CA (94102)",
    val maxFee: Int = 120,
    val minRating: Float = 4.5f,
    val experience: String = "5 - 10 yrs",
    val gender: String = "Any",
    val consultationTypes: Set<ConsultationType> = setOf(ConsultationType.IN_PERSON, ConsultationType.VIDEO),
    val availableToday: Boolean = true
)
