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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ConsultationType
import com.example.model.FilterCriteria
import com.example.ui.theme.MedixinBlue
import com.example.ui.theme.MedixinBorder
import com.example.ui.theme.MedixinDarkText
import com.example.ui.theme.MedixinMutedText

@Composable
fun FilterDoctorsScreen(
    initialFilters: FilterCriteria,
    onApply: (FilterCriteria) -> Unit,
    onReset: () -> Unit,
    onClose: () -> Unit
) {
    var selectedSpecialties by remember { mutableStateOf(initialFilters.selectedSpecialties) }
    var locationQuery by remember { mutableStateOf(initialFilters.location) }
    var maxFee by remember { mutableFloatStateOf(initialFilters.maxFee.toFloat()) }
    var minRating by remember { mutableFloatStateOf(initialFilters.minRating) }
    var experience by remember { mutableStateOf(initialFilters.experience) }
    var gender by remember { mutableStateOf(initialFilters.gender) }
    var availableToday by remember { mutableStateOf(initialFilters.availableToday) }
    var selectedTypes by remember { mutableStateOf(initialFilters.consultationTypes) }

    val allSpecialties = listOf(
        "Cardiology", "Dermatology", "General Practice", "Dental", "Neurology", "Pediatrics", "Orthopedics"
    )

    val activeCount = (if (selectedSpecialties.isNotEmpty()) selectedSpecialties.size else 0) +
            (if (locationQuery.isNotBlank()) 1 else 0) +
            (if (maxFee < 250f) 1 else 0) +
            (if (minRating > 0f) 1 else 0) +
            (if (availableToday) 1 else 0)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FD))
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag("filter_doctors_screen")
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
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
                        .background(Color(0xFFF1F5F9))
                        .clickable { onClose() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = MedixinDarkText,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Filters",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = MedixinDarkText
                    )
                    Text(
                        text = "Screen 12 • Medixin Care",
                        fontSize = 11.sp,
                        color = Color(0xFF94A3B8)
                    )
                }

                TextButton(
                    onClick = {
                        selectedSpecialties = emptySet()
                        locationQuery = ""
                        maxFee = 250f
                        minRating = 0f
                        experience = "Any"
                        gender = "Any"
                        availableToday = false
                        onReset()
                    }
                ) {
                    Text(
                        text = "Reset",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MedixinBlue
                    )
                }
            }

            // Scrollable Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Active Filters Banner
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFEEF4FF))
                        .border(1.dp, Color(0xFFD9E6FF), RoundedCornerShape(12.dp))
                        .padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(MedixinBlue))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "$activeCount active filters selected",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E3A8A)
                            )
                        }
                        Text(
                            text = "Screen 11 Linked",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = MedixinBlue
                        )
                    }
                }

                // 1. Specialty Section
                FilterCard(title = "Specialty", subtitle = "Select multiple") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            // First row
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                allSpecialties.take(2).forEach { spec ->
                                    val isSelected = selectedSpecialties.contains(spec)
                                    ChipItem(
                                        text = spec,
                                        isSelected = isSelected,
                                        onClick = {
                                            selectedSpecialties = if (isSelected) selectedSpecialties - spec else selectedSpecialties + spec
                                        }
                                    )
                                }
                            }
                            // Second row
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                allSpecialties.drop(2).take(2).forEach { spec ->
                                    val isSelected = selectedSpecialties.contains(spec)
                                    ChipItem(
                                        text = spec,
                                        isSelected = isSelected,
                                        onClick = {
                                            selectedSpecialties = if (isSelected) selectedSpecialties - spec else selectedSpecialties + spec
                                        }
                                    )
                                }
                            }
                            // Third row
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                allSpecialties.drop(4).forEach { spec ->
                                    val isSelected = selectedSpecialties.contains(spec)
                                    ChipItem(
                                        text = spec,
                                        isSelected = isSelected,
                                        onClick = {
                                            selectedSpecialties = if (isSelected) selectedSpecialties - spec else selectedSpecialties + spec
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                // 2. Location Section
                FilterCard(title = "Location", subtitle = "Filter by clinic city or postal code") {
                    OutlinedTextField(
                        value = locationQuery,
                        onValueChange = { locationQuery = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("City or Postal Code...", fontSize = 12.sp) },
                        leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color(0xFF94A3B8), modifier = Modifier.size(18.dp)) },
                        trailingIcon = {
                            if (locationQuery.isNotEmpty()) {
                                IconButton(onClick = { locationQuery = "" }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Clear", tint = Color(0xFF94A3B8), modifier = Modifier.size(16.dp))
                                }
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MedixinBlue,
                            unfocusedBorderColor = MedixinBorder
                        ),
                        singleLine = true
                    )
                }

                // 3. Consultation Fee
                FilterCard(
                    title = "Consultation Fee",
                    badge = "$30 - $${maxFee.toInt()}"
                ) {
                    Column {
                        Slider(
                            value = maxFee,
                            onValueChange = { maxFee = it },
                            valueRange = 20f..250f,
                            colors = SliderDefaults.colors(
                                thumbColor = Color.White,
                                activeTrackColor = MedixinBlue,
                                inactiveTrackColor = Color(0xFFE2E8F0)
                            )
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Min: $20", fontSize = 11.sp, color = Color(0xFF94A3B8))
                            Text("Average: $50/visit", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = MedixinDarkText)
                            Text("Max: $250+", fontSize = 11.sp, color = Color(0xFF94A3B8))
                        }
                    }
                }

                // 4. Minimum Rating
                FilterCard(title = "Minimum Rating") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(0f to "Any", 3.5f to "★ 3.5+", 4.0f to "★ 4.0+", 4.5f to "★ 4.5+").forEach { (r, label) ->
                            val isSelected = minRating == r
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isSelected) Color(0xFFEEF4FF) else Color(0xFFF8FAFC))
                                    .border(1.dp, if (isSelected) MedixinBlue else MedixinBorder, RoundedCornerShape(10.dp))
                                    .clickable { minRating = r }
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = label,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) Color(0xFF1E3A8A) else MedixinDarkText
                                )
                            }
                        }
                    }
                }

                // 5. Doctor Experience
                FilterCard(title = "Doctor Experience") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("1 - 5 yrs", "5 - 10 yrs", "10+ yrs").forEach { exp ->
                            val isSelected = experience == exp
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isSelected) Color(0xFFEEF4FF) else Color(0xFFF8FAFC))
                                    .border(1.dp, if (isSelected) MedixinBlue else MedixinBorder, RoundedCornerShape(10.dp))
                                    .clickable { experience = exp }
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = exp,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) Color(0xFF1E3A8A) else MedixinDarkText
                                )
                            }
                        }
                    }
                }

                // 6. Gender Preference
                FilterCard(title = "Gender Preference") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("Any", "Female", "Male").forEach { g ->
                            val isSelected = gender == g
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isSelected) Color(0xFFEEF4FF) else Color(0xFFF8FAFC))
                                    .border(1.dp, if (isSelected) MedixinBlue else MedixinBorder, RoundedCornerShape(10.dp))
                                    .clickable { gender = g }
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = g,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) Color(0xFF1E3A8A) else MedixinDarkText
                                )
                            }
                        }
                    }
                }

                // 7. Consultation Type
                FilterCard(title = "Consultation Type") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Video
                        val isVideoSelected = selectedTypes.contains(ConsultationType.VIDEO)
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isVideoSelected) Color(0xFFEEF4FF) else Color(0xFFF8FAFC))
                                .border(1.dp, if (isVideoSelected) MedixinBlue else MedixinBorder, RoundedCornerShape(12.dp))
                                .clickable {
                                    selectedTypes = if (isVideoSelected) selectedTypes - ConsultationType.VIDEO else selectedTypes + ConsultationType.VIDEO
                                }
                                .padding(12.dp)
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.Videocam, contentDescription = null, tint = MedixinBlue, modifier = Modifier.size(18.dp))
                                    if (isVideoSelected) {
                                        Box(modifier = Modifier.size(16.dp).clip(CircleShape).background(MedixinBlue), contentAlignment = Alignment.Center) {
                                            Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(10.dp))
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text("Video Call", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MedixinDarkText)
                                Text("Online Consultation", fontSize = 10.sp, color = MedixinMutedText)
                            }
                        }

                        // In-Clinic
                        val isInClinicSelected = selectedTypes.contains(ConsultationType.IN_PERSON)
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isInClinicSelected) Color(0xFFEEF4FF) else Color(0xFFF8FAFC))
                                .border(1.dp, if (isInClinicSelected) MedixinBlue else MedixinBorder, RoundedCornerShape(12.dp))
                                .clickable {
                                    selectedTypes = if (isInClinicSelected) selectedTypes - ConsultationType.IN_PERSON else selectedTypes + ConsultationType.IN_PERSON
                                }
                                .padding(12.dp)
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.Work, contentDescription = null, tint = MedixinBlue, modifier = Modifier.size(18.dp))
                                    if (isInClinicSelected) {
                                        Box(modifier = Modifier.size(16.dp).clip(CircleShape).background(MedixinBlue), contentAlignment = Alignment.Center) {
                                            Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(10.dp))
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text("In-Clinic", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MedixinDarkText)
                                Text("Physical Visit", fontSize = 10.sp, color = MedixinMutedText)
                            }
                        }
                    }
                }

                // 8. Available Today
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .border(1.dp, MedixinBorder, RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Available Today", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MedixinDarkText)
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color(0xFF10B981)))
                            }
                            Text("Only show doctors with slots in next 24h", fontSize = 11.5.sp, color = MedixinMutedText)
                        }

                        Switch(
                            checked = availableToday,
                            onCheckedChange = { availableToday = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = MedixinBlue
                            )
                        )
                    }
                }
            }

            // Bottom Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .border(1.dp, MedixinBorder)
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        selectedSpecialties = emptySet()
                        locationQuery = ""
                        maxFee = 250f
                        minRating = 0f
                        availableToday = false
                        onReset()
                    },
                    modifier = Modifier
                        .weight(0.4f)
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF1F5F9),
                        contentColor = MedixinDarkText
                    )
                ) {
                    Text("Reset All", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }

                Button(
                    onClick = {
                        val criteria = FilterCriteria(
                            selectedSpecialties = selectedSpecialties,
                            location = locationQuery,
                            maxFee = maxFee.toInt(),
                            minRating = minRating,
                            experience = experience,
                            gender = gender,
                            consultationTypes = selectedTypes,
                            availableToday = availableToday
                        )
                        onApply(criteria)
                    },
                    modifier = Modifier
                        .weight(0.6f)
                        .height(50.dp)
                        .shadow(6.dp, RoundedCornerShape(12.dp), spotColor = MedixinBlue.copy(alpha = 0.3f)),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MedixinBlue,
                        contentColor = Color.White
                    )
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Show Results", fontSize = 13.5.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterCard(
    title: String,
    subtitle: String? = null,
    badge: String? = null,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp, MedixinBorder, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(title, fontSize = 13.5.sp, fontWeight = FontWeight.Bold, color = MedixinDarkText)
                if (badge != null) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFFEEF4FF))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(badge, fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = MedixinBlue)
                    }
                } else if (subtitle != null) {
                    Text(subtitle, fontSize = 11.5.sp, color = Color(0xFF94A3B8))
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
private fun ChipItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (isSelected) Color(0xFFEEF4FF) else Color(0xFFF8FAFC))
            .border(1.dp, if (isSelected) MedixinBlue else MedixinBorder, RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 7.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = text,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) Color(0xFF1E3A8A) else MedixinDarkText
            )
            if (isSelected) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = MedixinBlue,
                    modifier = Modifier.size(13.dp)
                )
            }
        }
    }
}
