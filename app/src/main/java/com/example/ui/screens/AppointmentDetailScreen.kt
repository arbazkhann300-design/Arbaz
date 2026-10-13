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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MedicalInformation
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
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
fun AppointmentDetailScreen(
    appointment: Appointment,
    onBack: () -> Unit,
    onCancelAppointment: (String) -> Unit
) {
    var showActionAlert by remember { mutableStateOf<String?>(null) }
    var showCancelConfirm by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag("appointment_detail_screen")
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .border(1.dp, MedixinBorder, CircleShape)
                        .background(Color.White)
                        .clickable { onBack() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MedixinDarkText,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Text(
                    text = "Consultation Details",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = MedixinDarkText
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFEEF4FF))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = appointment.status.name,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MedixinBlue
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Doctor Card
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color.White)
                        .border(1.dp, MedixinBorder, RoundedCornerShape(18.dp))
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            model = appointment.doctorAvatarUrl,
                            contentDescription = appointment.doctorName,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .border(1.5.dp, MedixinBlue, CircleShape)
                        )

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = appointment.doctorName,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MedixinDarkText
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MedixinBlue, modifier = Modifier.size(16.dp))
                            }
                            Text(
                                text = "${appointment.doctorSpecialty} • ${appointment.doctorHospital}",
                                fontSize = 12.5.sp,
                                color = MedixinMutedText
                            )
                        }
                    }
                }

                // Schedule Details Box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color.White)
                        .border(1.dp, MedixinBorder, RoundedCornerShape(18.dp))
                        .padding(16.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = MedixinBlue, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text("Date & Time", fontSize = 11.sp, color = MedixinMutedText)
                                    Text("${appointment.dateText} at ${appointment.timeSlot}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MedixinDarkText)
                                }
                            }
                        }

                        HorizontalDivider(color = Color(0xFFF1F5F9))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (appointment.consultationType == ConsultationType.VIDEO) Icons.Default.Videocam else Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = MedixinBlue,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text("Consultation Format", fontSize = 11.sp, color = MedixinMutedText)
                                    Text(
                                        text = if (appointment.consultationType == ConsultationType.VIDEO) "HD Tele-health Video Call" else "In-Person Clinical Visit",
                                        fontSize = 13.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MedixinDarkText
                                    )
                                }
                            }
                        }
                    }
                }

                // Preparation Guidance
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFEEF4FF))
                        .border(1.dp, Color(0xFFD9E6FF), RoundedCornerShape(16.dp))
                        .padding(14.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Info, contentDescription = null, tint = MedixinBlue, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Preparation Instructions", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E3A8A))
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "• Please have any current prescription bottles or ECG reports ready.\n• Join the waiting room 5-10 minutes ahead of scheduled time.\n• Ensure camera and audio permissions are granted for tele-health calls.",
                            fontSize = 12.sp,
                            color = MedixinDarkText,
                            lineHeight = 18.sp
                        )
                    }
                }

                // Actions if confirmed
                if (appointment.status == AppointmentStatus.CONFIRMED) {
                    Button(
                        onClick = {
                            showActionAlert = if (appointment.consultationType == ConsultationType.VIDEO) {
                                "Starting secure encrypted video connection with ${appointment.doctorName}..."
                            } else {
                                "Routing to St. Jude Heart Center via Google Maps..."
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .shadow(8.dp, RoundedCornerShape(12.dp), spotColor = MedixinBlue.copy(alpha = 0.3f)),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MedixinBlue)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (appointment.consultationType == ConsultationType.VIDEO) Icons.Default.Videocam else Icons.Default.LocationOn,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (appointment.consultationType == ConsultationType.VIDEO) "Launch Video Consultation" else "Open Navigation Directions",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    OutlinedButton(
                        onClick = { showCancelConfirm = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = ButtonDefaults.outlinedButtonBorder.copy(
                            brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFFCA5A5))
                        )
                    ) {
                        Text("Cancel Appointment", color = Color(0xFFEF4444), fontSize = 13.5.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }

    if (showCancelConfirm) {
        AlertDialog(
            onDismissRequest = { showCancelConfirm = false },
            title = { Text("Confirm Cancellation", fontWeight = FontWeight.Bold) },
            text = { Text("Are you sure you want to cancel this appointment? This action cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onCancelAppointment(appointment.id)
                        showCancelConfirm = false
                        onBack()
                    }
                ) {
                    Text("Cancel Appointment", color = Color(0xFFEF4444), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showCancelConfirm = false }) {
                    Text("Keep", color = MedixinDarkText)
                }
            }
        )
    }

    showActionAlert?.let { alertText ->
        AlertDialog(
            onDismissRequest = { showActionAlert = null },
            title = { Text("Medixin Care", fontWeight = FontWeight.Bold) },
            text = { Text(alertText) },
            confirmButton = {
                TextButton(onClick = { showActionAlert = null }) {
                    Text("OK", color = MedixinBlue)
                }
            }
        )
    }
}
