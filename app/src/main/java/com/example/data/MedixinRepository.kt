package com.example.data

import com.example.model.Appointment
import com.example.model.AppointmentStatus
import com.example.model.ConsultationType
import com.example.model.Doctor
import com.example.model.FilterCriteria
import com.example.model.PatientProfile
import com.example.model.PatientReview
import com.example.model.Specialty
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class MedixinRepository {

    private val initialDoctors = listOf(
        Doctor(
            id = "doc_1",
            name = "Dr. Marcus Vance",
            degrees = "MD, FACC",
            specialty = "Cardiology",
            hospital = "St. Jude Heart Center",
            rating = 4.9f,
            reviewCount = 128,
            experienceYears = 12,
            consultationFee = 48.0,
            avatarUrl = "https://lh3.googleusercontent.com/aida/AEtjO1WYSMYwNukk8df9LsG1DZ4Vbn4c17P4VKd1Li5AN8Yd15VsmiZW5RDnO57AxCaTCTNMiEcmT1yOBzNrySupFBb0V_uWcWJCA_trnbu_cH4MD34iNMvEHVP9TUVIq-mC2XOMq_h5sIL1gqcpos73Jqr0A7viMcxjq_exqyVXGoE8APkOO1qAk-dau8WnKiUJRP85O1EC9WXCb3n0ARVBNuJVVbe5qNMhGLPLHBYRkPhC6FcFyD-GE3UAyfc",
            availableToday = true,
            nextSlotText = "Today, 02:30 PM",
            bio = "Senior Cardiologist and Surgeon specializing in preventive cardiology, cardiovascular wellness, and advanced hypertension management.",
            locationCity = "San Francisco, CA",
            postalCode = "94102",
            gender = "Male"
        ),
        Doctor(
            id = "doc_2",
            name = "Dr. Sarah Jenkins",
            degrees = "MBBS, MD",
            specialty = "Cardiology",
            hospital = "St. Jude Hospital",
            rating = 4.8f,
            reviewCount = 94,
            experienceYears = 8,
            consultationFee = 45.0,
            avatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAD3ZAjyT-JcAfjQaBGOlouu2aXxxQZq0zwZLdmcEAajEu_twQCJJPxLJT11T3ME6FxDfp3NFWOaqELz64u7yW3uwi1XeGlVvd4Y2S9GdiRqLYkK-_3aS4fR79aszD-lhsyie0kaqpsv9Gvyy0N2S9cJccMM6bHvtoonCD0ncXyAxxxsMLRWi-zldaEpHt3og3e5HA4ajDSYx7zlBmEar_41xKB78xxK9AXq-0kMW37HolclQcA2htC",
            availableToday = true,
            nextSlotText = "Tomorrow, 10:00 AM",
            bio = "Experienced Cardiologist dedicated to non-invasive cardiology, pediatric cardiac care, and personalized treatment plans.",
            locationCity = "San Francisco, CA",
            postalCode = "94103",
            gender = "Female"
        ),
        Doctor(
            id = "doc_3",
            name = "Dr. Tamim Ikraim",
            degrees = "MBBS, DDV",
            specialty = "Dermatology",
            hospital = "Aesthetic Care Clinic",
            rating = 4.9f,
            reviewCount = 124,
            experienceYears = 9,
            consultationFee = 45.0,
            avatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCxd8B4xRrPMSCq0x1a8gQGigd7zoeX5yPnaY56y-xHxPmbwFzWodpkSoJcLGR9h7mydK-yIl046VK1gGt0-zIuPCu9X0CTsJemelmUkaxnlK5J73qyC8h6THKKPPbrDrbmT977XDun3fora48dYkAmR7k6KVgVMFOLY_UGHymCVnivF1iTwONGHF95if-ygzjf7GK2eym1WFWEcK51PYNcVAKLrKUe09dIQnnCxF0fj-6vHaOPxb-1",
            availableToday = true,
            nextSlotText = "Today, 04:00 PM",
            bio = "Certified Dermatologist with expertise in clinical dermatology, skin health restoration, and modern cosmetic procedures.",
            locationCity = "San Francisco, CA",
            postalCode = "94102",
            gender = "Male"
        ),
        Doctor(
            id = "doc_4",
            name = "Dr. Tasnim Mridha",
            degrees = "MBBS, FCPS",
            specialty = "General Practice",
            hospital = "Metro Family Health",
            rating = 5.0f,
            reviewCount = 98,
            experienceYears = 11,
            consultationFee = 40.0,
            avatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAmsiAzNL1eftdjqhO0Ed1x9ztbJvTSQYCyb7AfAYM9YFi2AoJCHLCysL7Dhcv8wbQt3Tr9TU8YewuhMFwWQUgYwVBFwZFh3QfuqsBWPukvHh3SdQWPeduwIgBhOIM3XE7DGe6DSZA7EYRPMUlTdOHjN1BONmcL-2jL2TyxmItNkIwmC61Pf9blRiPr5Wurb_OnABP0MEJ9GtTcd_YgBsBnd9sZU2tMw72lynlCD9vhBhYdvSka5IIz",
            availableToday = true,
            nextSlotText = "Today, 01:30 PM",
            bio = "Compassionate primary care physician with a holistic focus on family wellness, disease prevention, and active lifestyle medicine.",
            locationCity = "San Francisco, CA",
            postalCode = "94108",
            gender = "Female"
        ),
        Doctor(
            id = "doc_5",
            name = "Dr. Aris Thorne",
            degrees = "MD, Interventional Cardiology",
            specialty = "Cardiology",
            hospital = "Heart Care Clinic • Apex",
            rating = 4.9f,
            reviewCount = 210,
            experienceYears = 15,
            consultationFee = 65.0,
            avatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBBzdyCHux24X56VzX9-7ckLmjhRFffR8IPWlbQhSAiy5cbnHiDPbkmhgmfccbk5eOJI29OAi4fKV2TK5NvhDipEH1DR3igDUkm0WtyVKrfAogVCOWa8W5oiWD8Ta7JssGiGi4ckKwaxzZxeY4XMVtB7zG1KyLQGa13H-2cDySEw69doTmZ5YsDM1JPppc8CYwTiSX_6WYA7w-awe0lp7i7Oi2FNVbH9Tid_Vvhc_i8CYQcohosRu20",
            availableToday = false,
            nextSlotText = "Thu, 11:15 AM",
            bio = "Pioneering interventional cardiologist with over 15 years experience managing coronary diseases and structural heart therapy.",
            locationCity = "San Francisco, CA",
            postalCode = "94102",
            gender = "Male"
        ),
        Doctor(
            id = "doc_6",
            name = "Dr. Eleanor Vance",
            degrees = "DDS, Orthodontics",
            specialty = "Dental",
            hospital = "Apex Dental Studio",
            rating = 4.7f,
            reviewCount = 88,
            experienceYears = 10,
            consultationFee = 50.0,
            avatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCWtRLnQtFFbNUlpBOhLRq7pFmXk41gV2OwxKYGSY5O8BjU0bG1fIGOdXc-jr27isPMTFn267PvncVHX42rIibWNLBkbAiZuvhqSnaWFyfm4WlbWSXQloOGTBfQuKeq02k58hry47NC599sFsnlFS9GRMRU3fRsK-h4nEM7JDO8L-ENA1TOQNUC7Grsx_568x8Wxff6v1v708DYz9BLPNAN9jCFb-jmtMMn2hycYzdP-sWGu79jE288",
            availableToday = true,
            nextSlotText = "Tomorrow, 03:00 PM",
            bio = "Expert in aesthetic dentistry, smile alignment, restorative care, and gentle dental treatments.",
            locationCity = "San Francisco, CA",
            postalCode = "94102",
            gender = "Female"
        )
    )

    private val initialSpecialties = listOf(
        Specialty("spec_1", "General", "stethoscope", 48),
        Specialty("spec_2", "Dental", "dentistry", 32),
        Specialty("spec_3", "Cardiology", "cardiology", 24),
        Specialty("spec_4", "Dermatology", "vital_signs", 19),
        Specialty("spec_5", "Neurology", "psychology", 15),
        Specialty("spec_6", "Pediatrics", "child_care", 28),
        Specialty("spec_7", "Orthopedics", "accessible", 20)
    )

    private val initialAppointments = listOf(
        Appointment(
            id = "apt_initial_1",
            doctorId = "doc_2",
            doctorName = "Dr. Sarah Jenkins",
            doctorSpecialty = "Cardiologist",
            doctorHospital = "St. Jude Hospital",
            doctorAvatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAD3ZAjyT-JcAfjQaBGOlouu2aXxxQZq0zwZLdmcEAajEu_twQCJJPxLJT11T3ME6FxDfp3NFWOaqELz64u7yW3uwi1XeGlVvd4Y2S9GdiRqLYkK-_3aS4fR79aszD-lhsyie0kaqpsv9Gvyy0N2S9cJccMM6bHvtoonCD0ncXyAxxxsMLRWi-zldaEpHt3og3e5HA4ajDSYx7zlBmEar_41xKB78xxK9AXq-0kMW37HolclQcA2htC",
            dateText = "Thu, Oct 24",
            timeSlot = "10:30 AM",
            consultationType = ConsultationType.VIDEO,
            fee = 45.0,
            status = AppointmentStatus.CONFIRMED,
            patientName = "Alex Vance",
            daysLeftText = "1d left"
        )
    )

    private val initialReviews = listOf(
        PatientReview(
            id = "rev_1",
            patientName = "Sarah Jenkins",
            initials = "SJ",
            rating = 5,
            timeAgo = "2 days ago",
            comment = "Dr. Vance took time to thoroughly explain my ECG results and cardiovascular health plan. He answered all my questions calmly without rushing. Highly recommend his clinical expertise!",
            consultationType = "In-Person",
            isVerified = true,
            helpfulCount = 14
        ),
        PatientReview(
            id = "rev_2",
            patientName = "Michael Chen",
            initials = "MC",
            rating = 5,
            timeAgo = "1 week ago",
            comment = "Very punctual video consultation. Dr. Marcus provided clear next steps for blood pressure management and immediately coordinated my prescription.",
            consultationType = "Video Call",
            isVerified = true,
            helpfulCount = 9
        ),
        PatientReview(
            id = "rev_3",
            patientName = "Jessica Taylor",
            initials = "JT",
            rating = 5,
            timeAgo = "2 weeks ago",
            comment = "Compassionate approach, very thorough examination and clear medication routine. St. Jude Heart Center facilities are top tier.",
            consultationType = "In-Person",
            isVerified = true,
            helpfulCount = 6
        ),
        PatientReview(
            id = "rev_4",
            patientName = "Robert Lang",
            initials = "RL",
            rating = 4,
            timeAgo = "3 weeks ago",
            comment = "Great consultation overall. Very knowledgeable and reassuring doctor. Wait time was under 8 minutes.",
            consultationType = "Video Call",
            isVerified = true,
            helpfulCount = 4
        )
    )

    private val _doctors = MutableStateFlow(initialDoctors)
    val doctors: StateFlow<List<Doctor>> = _doctors.asStateFlow()

    private val _specialties = MutableStateFlow(initialSpecialties)
    val specialties: StateFlow<List<Specialty>> = _specialties.asStateFlow()

    private val _appointments = MutableStateFlow(initialAppointments)
    val appointments: StateFlow<List<Appointment>> = _appointments.asStateFlow()

    private val _patientProfile = MutableStateFlow(PatientProfile())
    val patientProfile: StateFlow<PatientProfile> = _patientProfile.asStateFlow()

    private val _favorites = MutableStateFlow<Set<String>>(setOf("doc_1"))
    val favorites: StateFlow<Set<String>> = _favorites.asStateFlow()

    private val _recentSearches = MutableStateFlow(
        listOf("Dr. Marcus Vance", "Cardiology Specialist", "Dermatology Clinic")
    )
    val recentSearches: StateFlow<List<String>> = _recentSearches.asStateFlow()

    private val _filterCriteria = MutableStateFlow(FilterCriteria())
    val filterCriteria: StateFlow<FilterCriteria> = _filterCriteria.asStateFlow()

    private val _hydrationProgress = MutableStateFlow(0.65f)
    val hydrationProgress: StateFlow<Float> = _hydrationProgress.asStateFlow()

    private val _locationGranted = MutableStateFlow(true)
    val locationGranted: StateFlow<Boolean> = _locationGranted.asStateFlow()

    fun toggleFavorite(doctorId: String) {
        _favorites.update { current ->
            if (current.contains(doctorId)) current - doctorId else current + doctorId
        }
    }

    fun addRecentSearch(query: String) {
        if (query.isBlank()) return
        _recentSearches.update { current ->
            (listOf(query) + current.filter { it != query }).take(6)
        }
    }

    fun removeRecentSearch(query: String) {
        _recentSearches.update { current -> current.filter { it != query } }
    }

    fun clearRecentSearches() {
        _recentSearches.value = emptyList()
    }

    fun updateFilters(criteria: FilterCriteria) {
        _filterCriteria.value = criteria
    }

    fun resetFilters() {
        _filterCriteria.value = FilterCriteria(
            selectedSpecialties = emptySet(),
            location = "",
            maxFee = 250,
            minRating = 0f,
            experience = "Any",
            gender = "Any",
            consultationTypes = setOf(ConsultationType.IN_PERSON, ConsultationType.VIDEO),
            availableToday = false
        )
    }

    fun updateProfile(profile: PatientProfile) {
        _patientProfile.value = profile
    }

    fun setLocationGranted(granted: Boolean) {
        _locationGranted.value = granted
    }

    fun bookAppointment(
        doctor: Doctor,
        dateText: String,
        timeSlot: String,
        type: ConsultationType,
        notes: String
    ): Appointment {
        val newAppointment = Appointment(
            id = "apt_" + UUID.randomUUID().toString().take(8),
            doctorId = doctor.id,
            doctorName = doctor.name,
            doctorSpecialty = doctor.specialty,
            doctorHospital = doctor.hospital,
            doctorAvatarUrl = doctor.avatarUrl,
            dateText = dateText,
            timeSlot = timeSlot,
            consultationType = type,
            fee = if (type == ConsultationType.IN_PERSON) doctor.consultationFee else (doctor.consultationFee - 13.0).coerceAtLeast(30.0),
            status = AppointmentStatus.CONFIRMED,
            patientName = _patientProfile.value.fullName,
            notes = notes,
            daysLeftText = "Confirmed"
        )
        _appointments.update { listOf(newAppointment) + it }
        return newAppointment
    }

    fun cancelAppointment(appointmentId: String) {
        _appointments.update { list ->
            list.map {
                if (it.id == appointmentId) it.copy(status = AppointmentStatus.CANCELLED) else it
            }
        }
    }

    fun incrementHydration() {
        _hydrationProgress.update { (it + 0.1f).coerceAtMost(1.0f) }
    }

    fun getDoctorReviews(doctorId: String): List<PatientReview> {
        return initialReviews
    }

    fun getDoctorById(id: String): Doctor? {
        return _doctors.value.find { it.id == id } ?: _doctors.value.firstOrNull()
    }
}
