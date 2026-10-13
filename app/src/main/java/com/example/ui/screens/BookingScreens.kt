package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.EventBusy
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.NightlightRound
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.Appointment
import com.example.model.ConsultationType
import com.example.model.Doctor
import com.example.model.PatientProfile
import com.example.ui.theme.MedixinBlue
import com.example.ui.theme.MedixinBorder
import com.example.ui.theme.MedixinDarkText
import com.example.ui.theme.MedixinMutedText

// -------------------------------------------------------------
// STEP 1: CHOOSE CONSULTATION TYPE (Screen 15)
// -------------------------------------------------------------
@Composable
fun BookingStep1Screen(
    doctor: Doctor,
    onContinue: (ConsultationType) -> Unit,
    onBack: () -> Unit
) {
    var selectedType by remember { mutableStateOf(ConsultationType.IN_PERSON) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FF))
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag("booking_step1_screen")
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(1.dp, MedixinBorder, CircleShape)
                        .clickable { onBack() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = MedixinDarkText, modifier = Modifier.size(20.dp))
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFFEFF6FF))
                        .border(1.dp, Color(0xFFDBEAFE), RoundedCornerShape(20.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(MedixinBlue))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Step 1 of 5", fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = MedixinBlue)
                    }
                }

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(1.dp, MedixinBorder, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.HelpOutline, contentDescription = "Help", tint = MedixinMutedText, modifier = Modifier.size(20.dp))
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
            ) {
                // Progress Bar: 20%
                LinearProgressIndicator(
                    progress = { 0.2f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = MedixinBlue,
                    trackColor = Color(0xFFE2E8F0)
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "How would you like to consult?",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MedixinDarkText,
                    lineHeight = 28.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Select your preferred consultation method with ${doctor.name}.",
                    fontSize = 13.sp,
                    color = MedixinMutedText
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Compact Doctor Summary Card
                DoctorSummaryCard(doctor = doctor)

                Spacer(modifier = Modifier.height(18.dp))

                // Option 1: In-Person Visit
                ConsultationOptionCard(
                    title = "In-Person Visit",
                    subtitle = "Face-to-face clinical consultation at St. Jude Medical Plaza, Suite 402.",
                    tag = "Popular",
                    duration = "30–45 mins duration",
                    fee = "$${doctor.consultationFee.toInt()}.00",
                    isSelected = selectedType == ConsultationType.IN_PERSON,
                    isClinic = true,
                    onClick = { selectedType = ConsultationType.IN_PERSON }
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Option 2: Video Consultation
                ConsultationOptionCard(
                    title = "Video Consultation",
                    subtitle = "High-definition secure tele-health call from the comfort of your home.",
                    tag = "Fastest",
                    duration = "25–30 mins duration",
                    fee = "$${(doctor.consultationFee - 13).toInt().coerceAtLeast(30)}.00",
                    isSelected = selectedType == ConsultationType.VIDEO,
                    isClinic = false,
                    onClick = { selectedType = ConsultationType.VIDEO }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Guarantee Banner
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFF1F5F9))
                        .border(1.dp, MedixinBorder, RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Security, contentDescription = null, tint = MedixinBlue, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Free cancellation up to 2 hours before your appointment.",
                            fontSize = 11.5.sp,
                            color = MedixinDarkText
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            // Fixed Bottom Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .border(1.dp, MedixinBorder)
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Total Payable", fontSize = 12.sp, color = MedixinMutedText)
                        val totalFee = if (selectedType == ConsultationType.IN_PERSON) doctor.consultationFee else (doctor.consultationFee - 13).coerceAtLeast(30.0)
                        Text("$${totalFee.toInt()}.00", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = MedixinDarkText)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { onContinue(selectedType) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .shadow(8.dp, RoundedCornerShape(12.dp), spotColor = MedixinBlue.copy(alpha = 0.35f)),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MedixinBlue, contentColor = Color.White)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("CONTINUE", fontSize = 14.5.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// STEP 2: CHOOSE DATE (Screen 16)
// -------------------------------------------------------------
@Composable
fun BookingStep2Screen(
    doctor: Doctor,
    consultationType: ConsultationType,
    onContinue: (selectedDate: String) -> Unit,
    onBack: () -> Unit
) {
    var selectedDay by remember { mutableIntStateOf(16) }
    var currentMonthIndex by remember { mutableIntStateOf(9) } // 9 = October (0-indexed)
    val monthNames = listOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
    val selectedDateText = "Wednesday, ${monthNames[currentMonthIndex].take(3)} $selectedDay, 2024"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag("booking_step2_screen")
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(1.dp, MedixinBorder, CircleShape)
                        .clickable { onBack() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = MedixinDarkText, modifier = Modifier.size(20.dp))
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Choose Date", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MedixinDarkText)
                    Text("STEP 2 OF 5 • Consultation", fontSize = 11.sp, color = MedixinBlue, fontWeight = FontWeight.Bold)
                }

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(1.dp, MedixinBorder, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.HelpOutline, contentDescription = "Help", tint = MedixinMutedText, modifier = Modifier.size(20.dp))
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
            ) {
                // Doctor Summary Card
                DoctorSummaryCard(doctor = doctor)

                Spacer(modifier = Modifier.height(14.dp))

                // Calendar Main Card
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .shadow(2.dp, RoundedCornerShape(18.dp), spotColor = Color.Black.copy(alpha = 0.04f))
                        .background(Color.White)
                        .border(1.dp, MedixinBorder, RoundedCornerShape(18.dp))
                        .padding(16.dp)
                ) {
                    Column {
                        // Month Navigation Header
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "${monthNames[currentMonthIndex]} 2024",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MedixinDarkText
                                )
                                Text("Select an open clinic day", fontSize = 11.sp, color = MedixinMutedText)
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFFF1F5F9))
                                        .clickable {
                                            if (currentMonthIndex > 0) currentMonthIndex--
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Prev", tint = MedixinDarkText, modifier = Modifier.size(18.dp))
                                }
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFFF1F5F9))
                                        .clickable {
                                            if (currentMonthIndex < 11) currentMonthIndex++
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next", tint = MedixinDarkText, modifier = Modifier.size(18.dp))
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Weekday Headers
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            listOf("SU", "MO", "TU", "WE", "TH", "FR", "SA").forEach { dayLabel ->
                                Text(
                                    text = dayLabel,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF94A3B8),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.width(36.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Week Rows
                        val weeks = listOf(
                            listOf(29 to false, 30 to false, 1 to false, 2 to false, 3 to false, 4 to false, 5 to false),
                            listOf(6 to false, 7 to false, 8 to false, 9 to false, 10 to false, 11 to false, 12 to false),
                            listOf(13 to false, 14 to true, 15 to true, 16 to true, 17 to true, 18 to true, 19 to true),
                            listOf(20 to false, 21 to true, 22 to true, 23 to true, 24 to false, 25 to true, 26 to true),
                            listOf(27 to false, 28 to true, 29 to true, 30 to true, 31 to true, 1 to false, 2 to false)
                        )

                        weeks.forEach { week ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                week.forEach { (dayNum, isAvailable) ->
                                    val isSelected = isAvailable && dayNum == selectedDay
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(
                                                when {
                                                    isSelected -> MedixinBlue
                                                    isAvailable -> Color.White
                                                    else -> Color(0xFFF8FAFC)
                                                }
                                            )
                                            .clickable(enabled = isAvailable) {
                                                selectedDay = dayNum
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(
                                                text = "$dayNum",
                                                fontSize = 13.sp,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                                                color = when {
                                                    isSelected -> Color.White
                                                    isAvailable -> MedixinDarkText
                                                    else -> Color(0xFFCBD5E1)
                                                },
                                                textDecoration = if (!isAvailable && dayNum < 25 && dayNum > 12) TextDecoration.LineThrough else TextDecoration.None
                                            )
                                            if (isAvailable && !isSelected) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(4.dp)
                                                        .clip(CircleShape)
                                                        .background(MedixinBlue)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        HorizontalDivider(color = Color(0xFFF1F5F9))
                        Spacer(modifier = Modifier.height(10.dp))

                        // Calendar Legend
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            LegendItem(color = MedixinBlue, text = "Selected")
                            LegendItem(color = MedixinBlue, text = "Available", isDot = true)
                            LegendItem(color = Color(0xFFCBD5E1), text = "Unavailable")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Selected Date Feedback Card
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFFEEF4FF))
                        .border(1.dp, Color(0xFFD9E6FF), RoundedCornerShape(14.dp))
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(MedixinBlue),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Event, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text("SELECTED DATE", fontSize = 10.5.sp, fontWeight = FontWeight.Bold, color = MedixinBlue)
                                Text(selectedDateText, fontSize = 13.5.sp, fontWeight = FontWeight.Bold, color = MedixinDarkText)
                            }
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFFECFDF5))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text("8 Slots Open", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF047857))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            // Fixed Bottom Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .border(1.dp, MedixinBorder)
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Consultation Fee", fontSize = 12.sp, color = MedixinMutedText)
                        Text("$${doctor.consultationFee.toInt()}.00 USD", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = MedixinDarkText)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { onContinue(selectedDateText) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .shadow(8.dp, RoundedCornerShape(12.dp), spotColor = MedixinBlue.copy(alpha = 0.35f)),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MedixinBlue, contentColor = Color.White)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Continue to Time Slot", fontSize = 14.5.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// STEP 3: AVAILABLE TIMES (Screen 17)
// -------------------------------------------------------------
@Composable
fun BookingStep3Screen(
    doctor: Doctor,
    consultationType: ConsultationType,
    dateText: String,
    onContinue: (selectedTimeSlot: String) -> Unit,
    onBack: () -> Unit
) {
    var selectedSlot by remember { mutableStateOf("10:15 AM") }
    var activeViewMode by remember { mutableStateOf("Normal") }

    val morningSlots = listOf("09:00 AM", "09:30 AM", "10:15 AM", "11:00 AM", "11:30 AM")
    val afternoonSlots = listOf("01:30 PM", "02:00 PM", "02:45 PM", "03:30 PM", "04:15 PM", "04:45 PM")
    val eveningSlots = listOf("05:15 PM", "05:45 PM", "06:15 PM")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag("booking_step3_screen")
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(1.dp, MedixinBorder, CircleShape)
                        .clickable { onBack() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = MedixinDarkText, modifier = Modifier.size(20.dp))
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Available Times", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = MedixinDarkText)
                    Text("Step 3 of 5", fontSize = 11.5.sp, color = MedixinBlue, fontWeight = FontWeight.Bold)
                }

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(1.dp, MedixinBorder, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.HelpOutline, contentDescription = "Help", tint = MedixinMutedText, modifier = Modifier.size(20.dp))
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
            ) {
                // Interactive View State Switcher (Normal View, No Slots, Skeleton)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFFE2E8F0))
                        .padding(3.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf("Normal", "No Slots", "Skeleton").forEach { mode ->
                        val isSelected = activeViewMode == mode
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(18.dp))
                                .background(if (isSelected) Color.White else Color.Transparent)
                                .clickable { activeViewMode = mode }
                                .padding(vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (mode == "Normal") "Normal View" else mode,
                                fontSize = 11.5.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) MedixinBlue else MedixinDarkText
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Doctor Context Card
                DoctorSummaryCard(doctor = doctor)

                Spacer(modifier = Modifier.height(12.dp))

                // Selected Date Banner
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFFEEF4FF))
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = MedixinBlue, modifier = Modifier.size(18.dp))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(dateText, fontSize = 13.5.sp, fontWeight = FontWeight.Bold, color = MedixinDarkText)
                                Text("Standard Consultation (30 mins)", fontSize = 11.sp, color = MedixinMutedText)
                            }
                        }

                        TextButton(onClick = { onBack() }) {
                            Text("Change", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MedixinBlue)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Timezone Notice
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AccessTime, contentDescription = null, tint = Color(0xFF00677C), modifier = Modifier.size(15.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("All times displayed in EST (GMT-5)", fontSize = 11.5.sp, color = MedixinMutedText)
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFD9E2FF))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text("11 Slots Open", fontSize = 10.5.sp, fontWeight = FontWeight.Bold, color = MedixinBlue)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (activeViewMode == "Normal") {
                    // Morning Section
                    TimeSection(
                        title = "Morning",
                        icon = Icons.Default.WbSunny,
                        slotCount = "4 slots",
                        slots = morningSlots,
                        selectedSlot = selectedSlot,
                        disabledSlots = setOf("11:00 AM", "11:30 AM"),
                        onSelectSlot = { selectedSlot = it }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Afternoon Section
                    TimeSection(
                        title = "Afternoon",
                        icon = Icons.Default.WbSunny,
                        slotCount = "5 slots",
                        slots = afternoonSlots,
                        selectedSlot = selectedSlot,
                        disabledSlots = setOf("03:30 PM"),
                        onSelectSlot = { selectedSlot = it }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Evening Section
                    TimeSection(
                        title = "Evening",
                        icon = Icons.Default.NightlightRound,
                        slotCount = "2 slots",
                        slots = eveningSlots,
                        selectedSlot = selectedSlot,
                        disabledSlots = setOf("06:15 PM"),
                        onSelectSlot = { selectedSlot = it }
                    )
                } else if (activeViewMode == "No Slots") {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFF1F5F9)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.EventBusy, contentDescription = null, tint = MedixinBlue, modifier = Modifier.size(32.dp))
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("No Available Slots", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MedixinDarkText)
                        Text("Please select another date from the calendar.", fontSize = 12.sp, color = MedixinMutedText)
                    }
                } else {
                    // Skeleton State
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        repeat(3) {
                            Box(modifier = Modifier.fillMaxWidth().height(100.dp).clip(RoundedCornerShape(14.dp)).background(Color(0xFFE2E8F0)))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            // Fixed Bottom Bar with Selected Slot Confirmation
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .border(1.dp, MedixinBorder)
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Column {
                    // Active Slot Summary Ribbon
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFF0F6FF))
                            .padding(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier.size(24.dp).clip(CircleShape).background(MedixinBlue),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "${dateText.split(",").take(2).joinToString(",")} • $selectedSlot",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MedixinDarkText
                                    )
                                    Text("Arrive 10 min early • 30m visit", fontSize = 11.sp, color = MedixinMutedText)
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFFDFF7FC))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text("Confirmed", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF00677C))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = { onContinue(selectedSlot) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .shadow(8.dp, RoundedCornerShape(12.dp), spotColor = MedixinBlue.copy(alpha = 0.35f)),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MedixinBlue, contentColor = Color.White)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("CONTINUE TO DETAILS", fontSize = 14.5.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// STEP 4 & 5: PATIENT DETAILS & CONFIRMATION
// -------------------------------------------------------------
@Composable
fun BookingStep4Screen(
    doctor: Doctor,
    consultationType: ConsultationType,
    dateText: String,
    timeSlot: String,
    patientProfile: PatientProfile,
    onConfirmBooking: (notes: String) -> Unit,
    onBack: () -> Unit
) {
    var reasonForVisit by remember { mutableStateOf("Routine Cardiovascular Wellness Check") }
    var notes by remember { mutableStateOf("Experiencing mild palpitations when exercising. Bringing previous ECG report.") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag("booking_step4_screen")
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(1.dp, MedixinBorder, CircleShape)
                        .clickable { onBack() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = MedixinDarkText, modifier = Modifier.size(20.dp))
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Appointment Details", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = MedixinDarkText)
                    Text("Step 4 of 5", fontSize = 11.5.sp, color = MedixinBlue, fontWeight = FontWeight.Bold)
                }

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(1.dp, MedixinBorder, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.HelpOutline, contentDescription = "Help", tint = MedixinMutedText, modifier = Modifier.size(20.dp))
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
            ) {
                // Booking Summary Card
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .border(1.dp, MedixinBorder, RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AsyncImage(
                                model = doctor.avatarUrl,
                                contentDescription = doctor.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(50.dp).clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(doctor.name, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = MedixinDarkText)
                                Text("${doctor.specialty} • ${doctor.hospital}", fontSize = 12.sp, color = MedixinMutedText)
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(color = Color(0xFFF1F5F9))
                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Date & Time", fontSize = 11.sp, color = MedixinMutedText)
                                Text("$dateText at $timeSlot", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = MedixinDarkText)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("Type", fontSize = 11.sp, color = MedixinMutedText)
                                Text(consultationType.title, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = MedixinBlue)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Patient Information
                Text("Patient Information", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MedixinDarkText)
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .border(1.dp, MedixinBorder, RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(patientProfile.fullName, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MedixinDarkText)
                        Text("Phone: ${patientProfile.phone} • Email: ${patientProfile.email}", fontSize = 12.sp, color = MedixinMutedText)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Reason for Visit
                Text("Reason for Consultation", fontSize = 13.5.sp, fontWeight = FontWeight.SemiBold, color = MedixinDarkText)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = reasonForVisit,
                    onValueChange = { reasonForVisit = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MedixinBlue, unfocusedBorderColor = MedixinBorder)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Notes for Doctor
                Text("Notes for the Doctor (Optional)", fontSize = 13.5.sp, fontWeight = FontWeight.SemiBold, color = MedixinDarkText)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    modifier = Modifier.fillMaxWidth().height(90.dp),
                    placeholder = { Text("Describe any symptoms, previous treatments or questions...", fontSize = 12.5.sp) },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MedixinBlue, unfocusedBorderColor = MedixinBorder),
                    maxLines = 4
                )

                Spacer(modifier = Modifier.height(24.dp))
            }

            // Bottom Bar: Confirm Booking
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .border(1.dp, MedixinBorder)
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Button(
                    onClick = { onConfirmBooking("$reasonForVisit. $notes") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .shadow(8.dp, RoundedCornerShape(12.dp), spotColor = MedixinBlue.copy(alpha = 0.35f)),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MedixinBlue, contentColor = Color.White)
                ) {
                    Text("CONFIRM APPOINTMENT", fontSize = 14.5.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.5.sp)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// HELPER COMPOSABLES
// -------------------------------------------------------------
@Composable
private fun DoctorSummaryCard(doctor: Doctor) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp, MedixinBorder, RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFE2E8F0))
            ) {
                AsyncImage(
                    model = doctor.avatarUrl,
                    contentDescription = doctor.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .align(Alignment.BottomEnd)
                        .padding(1.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF10B981))
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = doctor.name,
                        fontSize = 14.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = MedixinDarkText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.Default.CheckCircle, contentDescription = "Verified", tint = MedixinBlue, modifier = Modifier.size(15.dp))
                }
                Text(
                    text = "${doctor.specialty} • ${doctor.hospital}",
                    fontSize = 12.sp,
                    color = MedixinMutedText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(3.dp))
                    Text("${doctor.rating}", fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = MedixinDarkText)
                    Text(" (${doctor.reviewCount})", fontSize = 10.5.sp, color = Color(0xFF94A3B8))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("• Available Today", fontSize = 11.sp, fontWeight = FontWeight.Medium, color = Color(0xFF059669))
                }
            }
        }
    }
}

@Composable
private fun ConsultationOptionCard(
    title: String,
    subtitle: String,
    tag: String,
    duration: String,
    fee: String,
    isSelected: Boolean,
    isClinic: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) Color(0xFFEEF4FF) else Color.White)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) MedixinBlue else MedixinBorder,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) MedixinBlue else Color(0xFFEEF4FF)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isClinic) Icons.Default.LocationOn else Icons.Default.Videocam,
                            contentDescription = null,
                            tint = if (isSelected) Color.White else MedixinBlue,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(title, fontSize = 14.5.sp, fontWeight = FontWeight.Bold, color = MedixinDarkText)
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (isSelected) Color(0xFFDBEAFE) else Color(0xFFEFF6FF))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(tag, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MedixinBlue)
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(subtitle, fontSize = 12.sp, color = MedixinMutedText, lineHeight = 17.sp)
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Radio Indicator
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .border(2.dp, if (isSelected) MedixinBlue else Color(0xFFCBD5E1), CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    if (isSelected) {
                        Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(MedixinBlue))
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = if (isSelected) Color(0xFFDBEAFE) else Color(0xFFF1F5F9))
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AccessTime, contentDescription = null, tint = MedixinMutedText, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(duration, fontSize = 11.5.sp, color = MedixinMutedText)
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text("Consultation Fee", fontSize = 10.sp, color = MedixinMutedText)
                    Text(fee, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MedixinBlue)
                }
            }
        }
    }
}

@Composable
private fun TimeSection(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    slotCount: String,
    slots: List<String>,
    selectedSlot: String,
    disabledSlots: Set<String>,
    onSelectSlot: (String) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFEFF6FF)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = MedixinBlue, modifier = Modifier.size(16.dp))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MedixinDarkText)
            }
            Text(slotCount, fontSize = 11.5.sp, color = MedixinMutedText)
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 3 Column Slots Grid
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            slots.chunked(3).forEach { chunk ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    chunk.forEach { slot ->
                        val isSelected = selectedSlot == slot
                        val isDisabled = disabledSlots.contains(slot)

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    when {
                                        isSelected -> MedixinBlue
                                        isDisabled -> Color(0xFFF1F5F9)
                                        else -> Color.White
                                    }
                                )
                                .border(
                                    1.dp,
                                    if (isSelected) MedixinBlue else MedixinBorder,
                                    RoundedCornerShape(12.dp)
                                )
                                .clickable(enabled = !isDisabled) { onSelectSlot(slot) },
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (isSelected) {
                                    Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                }
                                Text(
                                    text = slot,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = when {
                                        isSelected -> Color.White
                                        isDisabled -> Color(0xFF94A3B8)
                                        else -> MedixinDarkText
                                    },
                                    textDecoration = if (isDisabled) TextDecoration.LineThrough else TextDecoration.None
                                )
                            }
                        }
                    }
                    // Fill remaining columns if less than 3
                    if (chunk.size < 3) {
                        repeat(3 - chunk.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LegendItem(color: Color, text: String, isDot: Boolean = false) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        if (isDot) {
            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(color))
        } else {
            Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(color))
        }
        Spacer(modifier = Modifier.width(6.dp))
        Text(text, fontSize = 11.5.sp, color = MedixinMutedText)
    }
}
