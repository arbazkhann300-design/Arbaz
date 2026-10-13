package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Appointment
import com.example.model.Doctor
import com.example.model.PatientProfile
import com.example.model.Specialty
import com.example.ui.theme.MedixinBlue
import com.example.ui.theme.MedixinBorder
import com.example.ui.theme.MedixinDarkText
import com.example.ui.theme.MedixinMutedText
import com.example.viewmodel.MainTab

@Composable
fun MainScreen(
    selectedTab: MainTab,
    onTabSelected: (MainTab) -> Unit,
    patientProfile: PatientProfile,
    upcomingAppointment: Appointment?,
    recommendedDoctors: List<Doctor>,
    allDoctors: List<Doctor>,
    favorites: Set<String>,
    specialties: List<Specialty>,
    appointments: List<Appointment>,
    hydrationProgress: Float,
    onSearchClick: () -> Unit,
    onSpecialtyClick: (String) -> Unit,
    onDoctorClick: (Doctor) -> Unit,
    onBookDoctor: (Doctor) -> Unit,
    onToggleFavorite: (String) -> Unit,
    onViewAppointmentDetail: (Appointment) -> Unit,
    onCancelAppointment: (String) -> Unit,
    onEditProfile: () -> Unit,
    onLocationSettings: () -> Unit,
    onSignOut: () -> Unit,
    onIncrementHydration: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .statusBarsPadding()
            .testTag("main_screen")
    ) {
        // Tab Content
        Box(modifier = Modifier.fillMaxSize()) {
            when (selectedTab) {
                MainTab.HOME -> {
                    HomeScreen(
                        patientProfile = patientProfile,
                        upcomingAppointment = upcomingAppointment,
                        recommendedDoctors = recommendedDoctors,
                        specialties = specialties,
                        hydrationProgress = hydrationProgress,
                        onSearchClick = onSearchClick,
                        onSpecialtyClick = onSpecialtyClick,
                        onDoctorClick = onDoctorClick,
                        onBookDoctor = onBookDoctor,
                        onViewAppointmentDetail = onViewAppointmentDetail,
                        onProfileClick = { onTabSelected(MainTab.PROFILE) },
                        onIncrementHydration = onIncrementHydration
                    )
                }

                MainTab.DOCTORS -> {
                    SearchDoctorsScreen(
                        doctors = allDoctors,
                        searchQuery = "",
                        recentSearches = emptyList(),
                        favorites = favorites,
                        selectedSort = "Rating (High to Low)",
                        onQueryChange = { onSearchClick() },
                        onClearQuery = {},
                        onSortChange = {},
                        onOpenFilters = onSearchClick,
                        onToggleFavorite = onToggleFavorite,
                        onDoctorClick = onDoctorClick,
                        onBack = { onTabSelected(MainTab.HOME) }
                    )
                }

                MainTab.APPOINTMENTS -> {
                    AppointmentsScreen(
                        appointments = appointments,
                        onCancelAppointment = onCancelAppointment,
                        onViewDetail = onViewAppointmentDetail,
                        onBookNew = { onTabSelected(MainTab.DOCTORS) }
                    )
                }

                MainTab.PROFILE -> {
                    ProfileScreen(
                        patientProfile = patientProfile,
                        hydrationProgress = hydrationProgress,
                        onEditProfile = onEditProfile,
                        onLocationSettings = onLocationSettings,
                        onSignOut = onSignOut
                    )
                }
            }
        }

        // Bottom Navigation Bar
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding()
                .background(Color.White.copy(alpha = 0.98f))
                .border(1.dp, Color(0xFFF1F5F9))
                .shadow(12.dp, spotColor = Color.Black.copy(alpha = 0.08f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomNavItem(
                    title = "Home",
                    icon = Icons.Default.Home,
                    isSelected = selectedTab == MainTab.HOME,
                    onClick = { onTabSelected(MainTab.HOME) }
                )
                BottomNavItem(
                    title = "Doctors",
                    icon = Icons.Default.MedicalServices,
                    isSelected = selectedTab == MainTab.DOCTORS,
                    onClick = { onTabSelected(MainTab.DOCTORS) }
                )
                BottomNavItem(
                    title = "Appointments",
                    icon = Icons.Default.CalendarMonth,
                    isSelected = selectedTab == MainTab.APPOINTMENTS,
                    onClick = { onTabSelected(MainTab.APPOINTMENTS) }
                )
                BottomNavItem(
                    title = "Profile",
                    icon = Icons.Default.Person,
                    isSelected = selectedTab == MainTab.PROFILE,
                    onClick = { onTabSelected(MainTab.PROFILE) }
                )
            }
        }
    }
}

@Composable
private fun BottomNavItem(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = if (isSelected) MedixinBlue else MedixinMutedText,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = title,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) MedixinBlue else MedixinMutedText
        )
    }
}
