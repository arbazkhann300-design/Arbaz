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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EventBusy
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.Appointment
import com.example.model.AppointmentStatus
import com.example.model.ConsultationType
import com.example.ui.theme.MedixinBlue
import com.example.ui.theme.MedixinBorder
import com.example.ui.theme.MedixinDarkText
import com.example.ui.theme.MedixinMutedText

@Composable
fun AppointmentsScreen(
    appointments: List<Appointment>,
    onCancelAppointment: (String) -> Unit,
    onViewDetail: (Appointment) -> Unit,
    onBookNew: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Upcoming", "Completed", "Cancelled")

    var appointmentToCancel by remember { mutableStateOf<Appointment?>(null) }
    var actionDialogMessage by remember { mutableStateOf<String?>(null) }

    val filteredAppointments = appointments.filter { apt ->
        when (selectedTab) {
            0 -> apt.status == AppointmentStatus.CONFIRMED
            1 -> apt.status == AppointmentStatus.COMPLETED
            2 -> apt.status == AppointmentStatus.CANCELLED
            else -> true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .padding(bottom = 80.dp)
            .testTag("appointments_screen")
    ) {
        // App Bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
            Text(
                text = "Appointments",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MedixinDarkText
            )
            Text(
                text = "Manage your scheduled visits and telehealth calls",
                fontSize = 12.sp,
                color = MedixinMutedText
            )
        }

        // Tabs
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.White,
            contentColor = MedixinBlue,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = MedixinBlue,
                    height = 3.dp
                )
            }
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            fontSize = 13.5.sp,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium,
                            color = if (selectedTab == index) MedixinBlue else MedixinMutedText
                        )
                    }
                )
            }
        }

        if (filteredAppointments.isEmpty()) {
            // Empty State
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFEFF6FF)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.EventBusy,
                        contentDescription = null,
                        tint = MedixinBlue,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "No ${tabTitles[selectedTab]} Appointments",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MedixinDarkText
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = if (selectedTab == 0) "You do not have any upcoming medical appointments booked." else "No records found in this category.",
                    fontSize = 13.sp,
                    color = MedixinMutedText,
                    textAlign = TextAlign.Center
                )

                if (selectedTab == 0) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = onBookNew,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MedixinBlue)
                    ) {
                        Text("Find a Doctor", fontSize = 13.5.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(filteredAppointments, key = { it.id }) { apt ->
                    AppointmentCard(
                        appointment = apt,
                        onJoinCall = {
                            actionDialogMessage = "Connecting to encrypted telehealth room with ${apt.doctorName}..."
                        },
                        onDirections = {
                            actionDialogMessage = "Navigating to ${apt.doctorHospital} (${apt.dateText} at ${apt.timeSlot})."
                        },
                        onCancel = {
                            appointmentToCancel = apt
                        },
                        onCardClick = {
                            onViewDetail(apt)
                        }
                    )
                }
            }
        }
    }

    // Cancel Confirmation Dialog
    appointmentToCancel?.let { apt ->
        AlertDialog(
            onDismissRequest = { appointmentToCancel = null },
            title = { Text("Cancel Appointment", fontWeight = FontWeight.Bold) },
            text = {
                Text("Are you sure you want to cancel your consultation with ${apt.doctorName} on ${apt.dateText} at ${apt.timeSlot}?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onCancelAppointment(apt.id)
                        appointmentToCancel = null
                    }
                ) {
                    Text("Yes, Cancel", color = Color(0xFFEF4444), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { appointmentToCancel = null }) {
                    Text("Keep Appointment", color = MedixinDarkText)
                }
            }
        )
    }

    // Action Feedback Dialog
    actionDialogMessage?.let { msg ->
        AlertDialog(
            onDismissRequest = { actionDialogMessage = null },
            title = { Text("Consultation Portal", fontWeight = FontWeight.Bold) },
            text = { Text(msg) },
            confirmButton = {
                TextButton(onClick = { actionDialogMessage = null }) {
                    Text("OK", color = MedixinBlue)
                }
            }
        )
    }
}

@Composable
private fun AppointmentCard(
    appointment: Appointment,
    onJoinCall: () -> Unit,
    onDirections: () -> Unit,
    onCancel: () -> Unit,
    onCardClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .shadow(3.dp, RoundedCornerShape(18.dp), spotColor = Color.Black.copy(alpha = 0.05f))
            .background(Color.White)
            .border(1.dp, MedixinBorder, RoundedCornerShape(18.dp))
            .clickable { onCardClick() }
            .padding(16.dp)
    ) {
        Column {
            // Top Row: Doctor info + Status pill
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(modifier = Modifier.weight(1f)) {
                    AsyncImage(
                        model = appointment.doctorAvatarUrl,
                        contentDescription = appointment.doctorName,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE2E8F0))
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = appointment.doctorName,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MedixinDarkText,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${appointment.doctorSpecialty} • ${appointment.doctorHospital}",
                            fontSize = 12.sp,
                            color = MedixinMutedText,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                // Status Tag
                val (statusBg, statusColor) = when (appointment.status) {
                    AppointmentStatus.CONFIRMED -> Color(0xFFDFF7FC) to Color(0xFF00677C)
                    AppointmentStatus.COMPLETED -> Color(0xFFECFDF5) to Color(0xFF047857)
                    AppointmentStatus.CANCELLED -> Color(0xFFFEF2F2) to Color(0xFFDC2626)
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(statusBg)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = appointment.status.name,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = statusColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color(0xFFF1F5F9))
            Spacer(modifier = Modifier.height(10.dp))

            // Time & Mode Ribbon
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFF8FAFC))
                    .padding(horizontal = 10.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = MedixinBlue, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${appointment.dateText} • ${appointment.timeSlot}",
                            fontSize = 12.5.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MedixinDarkText
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (appointment.consultationType == ConsultationType.VIDEO) Icons.Default.Videocam else Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = MedixinBlue,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (appointment.consultationType == ConsultationType.VIDEO) "Video Call" else "In-Clinic",
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Medium,
                            color = MedixinBlue
                        )
                    }
                }
            }

            // Action row if confirmed
            if (appointment.status == AppointmentStatus.CONFIRMED) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = {
                            if (appointment.consultationType == ConsultationType.VIDEO) onJoinCall() else onDirections()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(42.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MedixinBlue)
                    ) {
                        Text(
                            text = if (appointment.consultationType == ConsultationType.VIDEO) "Join Call" else "Directions",
                            fontSize = 12.5.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    OutlinedButton(
                        onClick = onCancel,
                        modifier = Modifier
                            .weight(0.7f)
                            .height(42.dp),
                        shape = RoundedCornerShape(10.dp),
                        border = ButtonDefaults.outlinedButtonBorder.copy(
                            brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFE2E8F0))
                        )
                    ) {
                        Text(
                            text = "Cancel",
                            fontSize = 12.5.sp,
                            color = Color(0xFFEF4444),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}
