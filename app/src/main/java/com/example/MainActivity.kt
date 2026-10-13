package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.Appointment
import com.example.ui.screens.AppointmentDetailScreen
import com.example.ui.screens.AppointmentsScreen
import com.example.ui.screens.BookingStep1Screen
import com.example.ui.screens.BookingStep2Screen
import com.example.ui.screens.BookingStep3Screen
import com.example.ui.screens.BookingStep4Screen
import com.example.ui.screens.CompleteProfileScreen
import com.example.ui.screens.DoctorReviewsScreen
import com.example.ui.screens.FilterDoctorsScreen
import com.example.ui.screens.LocationPreferenceScreen
import com.example.ui.screens.MainScreen
import com.example.ui.screens.OnboardingScreen
import com.example.ui.screens.SearchDoctorsScreen
import com.example.ui.screens.SignInScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.theme.MedixinBlue
import com.example.ui.theme.MedixinBorder
import com.example.ui.theme.MedixinDarkText
import com.example.ui.theme.MedixinMutedText
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.MainTab
import com.example.viewmodel.MedixinViewModel
import com.example.viewmodel.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MedixinApp()
            }
        }
    }
}

@Composable
fun MedixinApp(
    viewModel: MedixinViewModel = viewModel()
) {
    val activeScreen by viewModel.activeScreen.collectAsState()
    val allDoctors by viewModel.doctors.collectAsState()
    val filteredDoctors by viewModel.filteredDoctors.collectAsState()
    val appointments by viewModel.appointments.collectAsState()
    val patientProfile by viewModel.patientProfile.collectAsState()
    val favorites by viewModel.favorites.collectAsState()
    val recentSearches by viewModel.recentSearches.collectAsState()
    val selectedSort by viewModel.selectedSort.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filterCriteria by viewModel.filterCriteria.collectAsState()
    val hydrationProgress by viewModel.hydrationProgress.collectAsState()

    val upcomingAppointment = appointments.firstOrNull { it.status == com.example.model.AppointmentStatus.CONFIRMED }

    AnimatedContent(
        targetState = activeScreen,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        label = "screen_transition"
    ) { screen ->
        when (screen) {
            is Screen.Splash -> {
                SplashScreen(
                    onSplashFinished = {
                        viewModel.replaceScreen(Screen.Onboarding(0))
                    }
                )
            }

            is Screen.Onboarding -> {
                OnboardingScreen(
                    initialPage = screen.page,
                    onFinish = {
                        viewModel.replaceScreen(Screen.SignIn)
                    }
                )
            }

            is Screen.SignIn -> {
                SignInScreen(
                    onSignInSuccess = {
                        viewModel.replaceScreen(Screen.Main(MainTab.HOME))
                    },
                    onNavigateToRegister = {
                        viewModel.navigateTo(Screen.CompleteProfile)
                    }
                )
            }

            is Screen.CompleteProfile -> {
                BackHandler { viewModel.navigateBack() }
                CompleteProfileScreen(
                    currentProfile = patientProfile,
                    onSaveProfile = { updated ->
                        viewModel.updateProfile(updated)
                        viewModel.navigateTo(Screen.LocationPreference)
                    },
                    onBack = { viewModel.navigateBack() },
                    onSkip = { viewModel.navigateTo(Screen.LocationPreference) }
                )
            }

            is Screen.LocationPreference -> {
                BackHandler { viewModel.navigateBack() }
                LocationPreferenceScreen(
                    onLocationConfirmed = { city, zip ->
                        viewModel.updateProfile(patientProfile.copy(address = "$city $zip"))
                        viewModel.setLocationGranted(true)
                        viewModel.replaceScreen(Screen.Main(MainTab.HOME))
                    },
                    onBack = { viewModel.navigateBack() },
                    onSkip = {
                        viewModel.replaceScreen(Screen.Main(MainTab.HOME))
                    }
                )
            }

            is Screen.Main -> {
                MainScreen(
                    selectedTab = screen.selectedTab,
                    onTabSelected = { tab ->
                        viewModel.replaceScreen(Screen.Main(tab))
                    },
                    patientProfile = patientProfile,
                    upcomingAppointment = upcomingAppointment,
                    recommendedDoctors = allDoctors.take(4),
                    allDoctors = filteredDoctors,
                    favorites = favorites,
                    specialties = viewModel.repository.specialties.value,
                    appointments = appointments,
                    hydrationProgress = hydrationProgress,
                    onSearchClick = {
                        viewModel.navigateTo(Screen.SearchDoctors)
                    },
                    onSpecialtyClick = { specialtyName ->
                        viewModel.setSearchQuery(specialtyName)
                        viewModel.navigateTo(Screen.SearchDoctors)
                    },
                    onDoctorClick = { doc ->
                        viewModel.navigateTo(Screen.DoctorReviews(doc))
                    },
                    onBookDoctor = { doc ->
                        viewModel.navigateTo(Screen.BookingType(doc))
                    },
                    onToggleFavorite = { docId ->
                        viewModel.toggleFavorite(docId)
                    },
                    onViewAppointmentDetail = { apt ->
                        viewModel.navigateTo(Screen.AppointmentDetail(apt))
                    },
                    onCancelAppointment = { aptId ->
                        viewModel.cancelAppointment(aptId)
                    },
                    onEditProfile = {
                        viewModel.navigateTo(Screen.CompleteProfile)
                    },
                    onLocationSettings = {
                        viewModel.navigateTo(Screen.LocationPreference)
                    },
                    onSignOut = {
                        viewModel.replaceScreen(Screen.SignIn)
                    },
                    onIncrementHydration = {
                        viewModel.incrementHydration()
                    }
                )
            }

            is Screen.SearchDoctors -> {
                BackHandler { viewModel.navigateBack() }
                SearchDoctorsScreen(
                    doctors = filteredDoctors,
                    searchQuery = searchQuery,
                    recentSearches = recentSearches,
                    favorites = favorites,
                    selectedSort = selectedSort,
                    onQueryChange = { viewModel.setSearchQuery(it) },
                    onClearQuery = { viewModel.clearSearchQuery() },
                    onSortChange = { viewModel.setSort(it) },
                    onOpenFilters = { viewModel.navigateTo(Screen.FilterDoctors) },
                    onToggleFavorite = { viewModel.toggleFavorite(it) },
                    onDoctorClick = { doc -> viewModel.navigateTo(Screen.DoctorReviews(doc)) },
                    onBack = { viewModel.navigateBack() }
                )
            }

            is Screen.FilterDoctors -> {
                BackHandler { viewModel.navigateBack() }
                FilterDoctorsScreen(
                    initialFilters = filterCriteria,
                    onApply = { criteria ->
                        viewModel.updateFilters(criteria)
                        viewModel.navigateBack()
                    },
                    onReset = {
                        viewModel.resetFilters()
                    },
                    onClose = { viewModel.navigateBack() }
                )
            }

            is Screen.DoctorReviews -> {
                BackHandler { viewModel.navigateBack() }
                DoctorReviewsScreen(
                    doctor = screen.doctor,
                    reviews = viewModel.getDoctorReviews(screen.doctor.id),
                    onBack = { viewModel.navigateBack() },
                    onBookClick = { viewModel.navigateTo(Screen.BookingType(screen.doctor)) }
                )
            }

            is Screen.BookingType -> {
                BackHandler { viewModel.navigateBack() }
                BookingStep1Screen(
                    doctor = screen.doctor,
                    onContinue = { selectedType ->
                        viewModel.navigateTo(Screen.BookingDate(screen.doctor, selectedType))
                    },
                    onBack = { viewModel.navigateBack() }
                )
            }

            is Screen.BookingDate -> {
                BackHandler { viewModel.navigateBack() }
                BookingStep2Screen(
                    doctor = screen.doctor,
                    consultationType = screen.type,
                    onContinue = { selectedDate ->
                        viewModel.navigateTo(Screen.BookingTime(screen.doctor, screen.type, selectedDate))
                    },
                    onBack = { viewModel.navigateBack() }
                )
            }

            is Screen.BookingTime -> {
                BackHandler { viewModel.navigateBack() }
                BookingStep3Screen(
                    doctor = screen.doctor,
                    consultationType = screen.type,
                    dateText = screen.dateText,
                    onContinue = { timeSlot ->
                        viewModel.navigateTo(Screen.BookingReview(screen.doctor, screen.type, screen.dateText, timeSlot))
                    },
                    onBack = { viewModel.navigateBack() }
                )
            }

            is Screen.BookingReview -> {
                BackHandler { viewModel.navigateBack() }
                BookingStep4Screen(
                    doctor = screen.doctor,
                    consultationType = screen.type,
                    dateText = screen.dateText,
                    timeSlot = screen.timeSlot,
                    patientProfile = patientProfile,
                    onConfirmBooking = { notes ->
                        val booked = viewModel.bookAppointment(
                            doctor = screen.doctor,
                            dateText = screen.dateText,
                            timeSlot = screen.timeSlot,
                            type = screen.type,
                            notes = notes
                        )
                        viewModel.navigateTo(Screen.BookingSuccess(booked))
                    },
                    onBack = { viewModel.navigateBack() }
                )
            }

            is Screen.BookingSuccess -> {
                BookingSuccessView(
                    appointment = screen.appointment,
                    onViewAppointments = {
                        viewModel.replaceScreen(Screen.Main(MainTab.APPOINTMENTS))
                    },
                    onGoHome = {
                        viewModel.replaceScreen(Screen.Main(MainTab.HOME))
                    }
                )
            }

            is Screen.AppointmentDetail -> {
                BackHandler { viewModel.navigateBack() }
                AppointmentDetailScreen(
                    appointment = screen.appointment,
                    onBack = { viewModel.navigateBack() },
                    onCancelAppointment = { aptId ->
                        viewModel.cancelAppointment(aptId)
                    }
                )
            }
        }
    }
}

@Composable
fun BookingSuccessView(
    appointment: Appointment,
    onViewAppointments: () -> Unit,
    onGoHome: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(24.dp)
            .testTag("booking_success_screen"),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFECFDF5))
                    .shadow(12.dp, CircleShape, spotColor = Color(0xFF10B981).copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Success",
                    tint = Color(0xFF10B981),
                    modifier = Modifier.size(54.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Appointment Confirmed!",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MedixinDarkText
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Your consultation with ${appointment.doctorName} is confirmed for ${appointment.dateText} at ${appointment.timeSlot}.",
                fontSize = 13.5.sp,
                color = MedixinMutedText,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Booking card receipt
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .border(1.dp, MedixinBorder, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Booking ID", fontSize = 12.sp, color = MedixinMutedText)
                        Text(appointment.id, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MedixinDarkText)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Format", fontSize = 12.sp, color = MedixinMutedText)
                        Text(appointment.consultationType.title, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MedixinBlue)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Amount Paid", fontSize = 12.sp, color = MedixinMutedText)
                        Text("$${appointment.fee.toInt()}.00 USD", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MedixinDarkText)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onViewAppointments,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .shadow(8.dp, RoundedCornerShape(12.dp), spotColor = MedixinBlue.copy(alpha = 0.3f)),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MedixinBlue, contentColor = Color.White)
            ) {
                Text("View in Appointments", fontSize = 14.5.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = onGoHome,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Back to Home", fontSize = 13.5.sp, fontWeight = FontWeight.SemiBold, color = MedixinDarkText)
            }
        }
    }
}
