package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.Appointment
import com.example.model.Doctor
import com.example.model.PatientProfile
import com.example.model.Specialty
import com.example.ui.theme.MedixinBlue
import com.example.ui.theme.MedixinBorder
import com.example.ui.theme.MedixinCyan
import com.example.ui.theme.MedixinDarkText
import com.example.ui.theme.MedixinMutedText

@Composable
fun HomeScreen(
    patientProfile: PatientProfile,
    upcomingAppointment: Appointment?,
    recommendedDoctors: List<Doctor>,
    specialties: List<Specialty>,
    hydrationProgress: Float,
    onSearchClick: () -> Unit,
    onSpecialtyClick: (String) -> Unit,
    onDoctorClick: (Doctor) -> Unit,
    onBookDoctor: (Doctor) -> Unit,
    onViewAppointmentDetail: (Appointment) -> Unit,
    onProfileClick: () -> Unit,
    onIncrementHydration: () -> Unit
) {
    var showSpecialtiesDialog by remember { mutableStateOf(false) }
    var showNotifDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FF))
            .verticalScroll(rememberScrollState())
            .padding(bottom = 90.dp)
            .testTag("home_screen")
    ) {
        // Sticky Header Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF8F9FF).copy(alpha = 0.95f))
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MedixinBlue)
                        .shadow(6.dp, RoundedCornerShape(10.dp), spotColor = MedixinBlue.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalHospital,
                        contentDescription = "Medixin Cross",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Medixin",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MedixinDarkText
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                // Notifications Bell
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(1.dp, MedixinBorder, CircleShape)
                        .clickable { showNotifDialog = true },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        tint = MedixinDarkText,
                        modifier = Modifier.size(20.dp)
                    )
                    // Unread badge dot
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .align(Alignment.TopEnd)
                            .padding(end = 2.dp, top = 2.dp)
                            .clip(CircleShape)
                            .background(MedixinBlue)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                // Profile Avatar with active online dot
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable { onProfileClick() }
                ) {
                    AsyncImage(
                        model = patientProfile.avatarUrl,
                        contentDescription = "Patient Avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .border(1.5.dp, MedixinBlue, CircleShape)
                    )
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .align(Alignment.BottomEnd)
                            .clip(CircleShape)
                            .background(Color(0xFF10B981))
                            .border(1.5.dp, Color.White, CircleShape)
                    )
                }
            }
        }

        // Welcome back greeting
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 6.dp)
        ) {
            Text(
                text = "Welcome back 👋",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = MedixinMutedText
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Good morning, ${patientProfile.fullName.split(" ").firstOrNull() ?: "Alex"}",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MedixinDarkText,
                letterSpacing = (-0.4).sp
            )
        }

        // Search Trigger Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .clip(RoundedCornerShape(16.dp))
                .shadow(4.dp, RoundedCornerShape(16.dp), spotColor = Color.Black.copy(alpha = 0.04f))
                .background(Color.White)
                .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
                .clickable { onSearchClick() }
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .testTag("search_trigger")
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color(0xFF94A3B8),
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Search doctors, specialties...",
                        fontSize = 13.5.sp,
                        color = Color(0xFF94A3B8),
                        fontWeight = FontWeight.Medium
                    )
                }

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFEEF4FF)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = "Filter",
                        tint = MedixinBlue,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        // Specialties Grid Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Specialties",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = MedixinDarkText
                )
                Text(
                    text = "See All",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MedixinBlue,
                    modifier = Modifier.clickable { showSpecialtiesDialog = true }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 4 Column Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // 1. General
                SpecialtyCard(
                    title = "General",
                    bgColor = Color(0xFFD9E2FF),
                    iconColor = MedixinBlue,
                    modifier = Modifier.weight(1f),
                    onClick = { onSpecialtyClick("General Practice") }
                )
                // 2. Dental
                SpecialtyCard(
                    title = "Dental",
                    bgColor = Color(0xFFDFF7FC),
                    iconColor = Color(0xFF00677C),
                    modifier = Modifier.weight(1f),
                    onClick = { onSpecialtyClick("Dental") }
                )
                // 3. Cardiology
                SpecialtyCard(
                    title = "Cardiology",
                    bgColor = Color(0xFFFFDAD6),
                    iconColor = Color(0xFFBA1A1A),
                    modifier = Modifier.weight(1f),
                    onClick = { onSpecialtyClick("Cardiology") }
                )
                // 4. Dermatology
                SpecialtyCard(
                    title = "Derma",
                    bgColor = Color(0xFFDBE3F1),
                    iconColor = Color(0xFF424654),
                    modifier = Modifier.weight(1f),
                    onClick = { onSpecialtyClick("Dermatology") }
                )
            }
        }

        // Upcoming Appointment Section
        upcomingAppointment?.let { apt ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Upcoming Appointment",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = MedixinDarkText
                    )
                    // Ping dot
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF00677C))
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Card with top rainbow gradient stripe
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .shadow(8.dp, RoundedCornerShape(20.dp), spotColor = MedixinBlue.copy(alpha = 0.08f))
                        .background(Color.White)
                        .border(1.dp, Color(0xFFE5E8ED), RoundedCornerShape(20.dp))
                ) {
                    Column {
                        // Top gradient stripe
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .background(
                                    Brush.horizontalGradient(
                                        colors = listOf(MedixinBlue, MedixinCyan, Color(0xFF5CDBFE))
                                    )
                                )
                        )

                        Column(modifier = Modifier.padding(16.dp)) {
                            // Doctor Row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    AsyncImage(
                                        model = apt.doctorAvatarUrl,
                                        contentDescription = apt.doctorName,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(46.dp)
                                            .clip(CircleShape)
                                            .border(1.dp, MedixinBorder, CircleShape)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = apt.doctorName,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MedixinDarkText,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = "${apt.doctorSpecialty} • ${apt.doctorHospital}",
                                            fontSize = 12.sp,
                                            color = MedixinMutedText,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }

                                // Confirmed Badge
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Color(0xFFB1EBFF))
                                        .padding(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(modifier = Modifier.size(5.dp).clip(CircleShape).background(Color(0xFF00677C)))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "CONFIRMED",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF004E5E)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // Date & Mode row
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFFEEF4FF))
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.MedicalServices,
                                            contentDescription = null,
                                            tint = MedixinBlue,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "${apt.dateText} • ${apt.timeSlot}",
                                            fontSize = 12.5.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = MedixinDarkText
                                        )
                                    }

                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color.White)
                                            .padding(horizontal = 8.dp, vertical = 3.dp)
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Default.Videocam,
                                                contentDescription = null,
                                                tint = MedixinBlue,
                                                modifier = Modifier.size(13.dp)
                                            )
                                            Spacer(modifier = Modifier.width(3.dp))
                                            Text(
                                                text = if (apt.consultationType == com.example.model.ConsultationType.VIDEO) "Video" else "In-Person",
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = MedixinBlue
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Action footer
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(Color(0xFFD9E2FF))
                                        .padding(horizontal = 8.dp, vertical = 3.dp)
                                ) {
                                    Text(
                                        text = apt.daysLeftText,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MedixinBlue
                                    )
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .clickable { onViewAppointmentDetail(apt) }
                                        .padding(vertical = 4.dp)
                                ) {
                                    Text(
                                        text = "View Details",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MedixinBlue
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                        contentDescription = null,
                                        tint = MedixinBlue,
                                        modifier = Modifier.size(15.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Recommended Doctors Section (Horizontal Carousel)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recommended Doctors",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = MedixinDarkText
                )
                Text(
                    text = "See All",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MedixinBlue,
                    modifier = Modifier.clickable { onSearchClick() }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Carousel list
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                recommendedDoctors.forEach { doc ->
                    DoctorCardHome(
                        doctor = doc,
                        onCardClick = { onDoctorClick(doc) },
                        onBookClick = { onBookDoctor(doc) }
                    )
                }
            }
        }

        // Hydration Goal Tracker Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 6.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(Color(0xFFE8F2FF))
                .clickable { onIncrementHydration() }
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFB1EBFF).copy(alpha = 0.5f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.WaterDrop,
                            contentDescription = "Hydration",
                            tint = Color(0xFF00677C),
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "Hydration Goal: ${(hydrationProgress * 100).toInt()}% reached",
                            fontSize = 13.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = MedixinDarkText
                        )
                        Text(
                            text = "Stay refreshed before your afternoon consultations.",
                            fontSize = 11.5.sp,
                            color = MedixinMutedText
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .shadow(2.dp, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Logged",
                        tint = MedixinBlue,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }

    // Modal Dialog: All Specialties
    if (showSpecialtiesDialog) {
        AlertDialog(
            onDismissRequest = { showSpecialtiesDialog = false },
            title = { Text("Medical Specialties", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    specialties.forEach { spec ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFF8FAFC))
                                .clickable {
                                    showSpecialtiesDialog = false
                                    onSpecialtyClick(spec.name)
                                }
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(spec.name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                            Text("${spec.doctorCount} doctors", color = MedixinMutedText, fontSize = 12.sp)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showSpecialtiesDialog = false }) {
                    Text("Close", color = MedixinBlue)
                }
            }
        )
    }

    // Modal Dialog: Notifications
    if (showNotifDialog) {
        AlertDialog(
            onDismissRequest = { showNotifDialog = false },
            title = { Text("Notifications", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("• Upcoming video consultation with Dr. Sarah Jenkins tomorrow at 10:30 AM.")
                    Text("• Prescription refill confirmed by Metro Clinic.")
                    Text("• Health check reminder: 65% of daily hydration reached.")
                }
            },
            confirmButton = {
                TextButton(onClick = { showNotifDialog = false }) {
                    Text("Done", color = MedixinBlue)
                }
            }
        )
    }
}

@Composable
private fun SpecialtyCard(
    title: String,
    bgColor: Color,
    iconColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .shadow(2.dp, RoundedCornerShape(16.dp), spotColor = Color.Black.copy(alpha = 0.03f))
            .background(Color.White)
            .border(1.dp, Color(0xFFF1F5F9), RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(bgColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.MedicalServices,
                contentDescription = title,
                tint = iconColor,
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            fontSize = 11.5.sp,
            fontWeight = FontWeight.SemiBold,
            color = MedixinDarkText,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun DoctorCardHome(
    doctor: Doctor,
    onCardClick: () -> Unit,
    onBookClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(235.dp)
            .clip(RoundedCornerShape(20.dp))
            .shadow(4.dp, RoundedCornerShape(20.dp), spotColor = Color.Black.copy(alpha = 0.05f))
            .background(Color.White)
            .border(1.dp, Color(0xFFF1F5F9), RoundedCornerShape(20.dp))
            .clickable { onCardClick() }
            .padding(12.dp)
    ) {
        Column {
            // Photo with Rating Tag
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(125.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFFE2E8F0))
            ) {
                AsyncImage(
                    model = doctor.avatarUrl,
                    contentDescription = doctor.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Rating overlay tag
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White.copy(alpha = 0.92f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = Color(0xFFF59E0B),
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "${doctor.rating}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MedixinDarkText
                        )
                        Text(
                            text = " (${doctor.reviewCount})",
                            fontSize = 10.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = doctor.name,
                fontSize = 14.5.sp,
                fontWeight = FontWeight.Bold,
                color = MedixinDarkText,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "${doctor.degrees} • ${doctor.specialty}",
                fontSize = 11.5.sp,
                color = MedixinMutedText,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Fee and Book Now button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Consultation",
                        fontSize = 10.sp,
                        color = Color(0xFF94A3B8)
                    )
                    Text(
                        text = "$${doctor.consultationFee.toInt()}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MedixinBlue
                    )
                }

                Button(
                    onClick = onBookClick,
                    modifier = Modifier.height(34.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MedixinBlue,
                        contentColor = Color.White
                    ),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 0.dp)
                ) {
                    Text(
                        text = "Book Now",
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
