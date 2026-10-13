package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.MedixinRepository
import com.example.model.Appointment
import com.example.model.ConsultationType
import com.example.model.Doctor
import com.example.model.FilterCriteria
import com.example.model.PatientProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

sealed class Screen {
    object Splash : Screen()
    data class Onboarding(val page: Int = 0) : Screen()
    object SignIn : Screen()
    object CompleteProfile : Screen()
    object LocationPreference : Screen()
    data class Main(val selectedTab: MainTab = MainTab.HOME) : Screen()
    object SearchDoctors : Screen()
    object FilterDoctors : Screen()
    data class DoctorReviews(val doctor: Doctor) : Screen()
    data class BookingType(val doctor: Doctor) : Screen()
    data class BookingDate(val doctor: Doctor, val type: ConsultationType) : Screen()
    data class BookingTime(val doctor: Doctor, val type: ConsultationType, val dateText: String) : Screen()
    data class BookingReview(val doctor: Doctor, val type: ConsultationType, val dateText: String, val timeSlot: String) : Screen()
    data class BookingSuccess(val appointment: Appointment) : Screen()
    data class AppointmentDetail(val appointment: Appointment) : Screen()
}

enum class MainTab {
    HOME,
    DOCTORS,
    APPOINTMENTS,
    PROFILE
}

class MedixinViewModel(
    val repository: MedixinRepository = MedixinRepository()
) : ViewModel() {

    private val _screenStack = MutableStateFlow<List<Screen>>(listOf(Screen.Splash))
    val currentScreen: StateFlow<Screen> = MutableStateFlow<Screen>(Screen.Splash).apply {
        // keep in sync with top of stack
    }

    private val _activeScreen = MutableStateFlow<Screen>(Screen.Splash)
    val activeScreen: StateFlow<Screen> = _activeScreen.asStateFlow()

    val doctors = repository.doctors
    val appointments = repository.appointments
    val patientProfile = repository.patientProfile
    val favorites = repository.favorites
    val recentSearches = repository.recentSearches
    val filterCriteria = repository.filterCriteria
    val hydrationProgress = repository.hydrationProgress
    val locationGranted = repository.locationGranted

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedSort = MutableStateFlow("Rating (High to Low)")
    val selectedSort: StateFlow<String> = _selectedSort.asStateFlow()

    // Filtered doctor list based on search query, filter criteria, and sort
    val filteredDoctors: StateFlow<List<Doctor>> = combine(
        doctors,
        searchQuery,
        filterCriteria,
        selectedSort
    ) { allDocs, query, filters, sort ->
        var list = allDocs.filter { doc ->
            val matchesQuery = query.isBlank() ||
                    doc.name.contains(query, ignoreCase = true) ||
                    doc.specialty.contains(query, ignoreCase = true) ||
                    doc.hospital.contains(query, ignoreCase = true)

            val matchesSpecialty = filters.selectedSpecialties.isEmpty() ||
                    filters.selectedSpecialties.any { it.equals(doc.specialty, ignoreCase = true) }

            val matchesFee = doc.consultationFee <= filters.maxFee
            val matchesRating = doc.rating >= filters.minRating
            val matchesToday = !filters.availableToday || doc.availableToday

            matchesQuery && matchesSpecialty && matchesFee && matchesRating && matchesToday
        }

        when (sort) {
            "Rating (High to Low)" -> list.sortedByDescending { it.rating }
            "Price (Low to High)" -> list.sortedBy { it.consultationFee }
            "Experience" -> list.sortedByDescending { it.experienceYears }
            else -> list
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun navigateTo(screen: Screen) {
        _screenStack.value = _screenStack.value + screen
        _activeScreen.value = screen
    }

    fun replaceScreen(screen: Screen) {
        _screenStack.value = listOf(screen)
        _activeScreen.value = screen
    }

    fun navigateBack(): Boolean {
        val stack = _screenStack.value
        return if (stack.size > 1) {
            val newStack = stack.dropLast(1)
            _screenStack.value = newStack
            _activeScreen.value = newStack.last()
            true
        } else {
            false
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        if (query.isNotBlank()) {
            repository.addRecentSearch(query)
        }
    }

    fun clearSearchQuery() {
        _searchQuery.value = ""
    }

    fun setSort(sort: String) {
        _selectedSort.value = sort
    }

    fun toggleFavorite(doctorId: String) {
        repository.toggleFavorite(doctorId)
    }

    fun updateFilters(criteria: FilterCriteria) {
        repository.updateFilters(criteria)
    }

    fun resetFilters() {
        repository.resetFilters()
    }

    fun updateProfile(profile: PatientProfile) {
        repository.updateProfile(profile)
    }

    fun setLocationGranted(granted: Boolean) {
        repository.setLocationGranted(granted)
    }

    fun bookAppointment(
        doctor: Doctor,
        dateText: String,
        timeSlot: String,
        type: ConsultationType,
        notes: String
    ): Appointment {
        return repository.bookAppointment(doctor, dateText, timeSlot, type, notes)
    }

    fun cancelAppointment(appointmentId: String) {
        repository.cancelAppointment(appointmentId)
    }

    fun incrementHydration() {
        repository.incrementHydration()
    }

    fun getDoctorReviews(doctorId: String) = repository.getDoctorReviews(doctorId)
}
