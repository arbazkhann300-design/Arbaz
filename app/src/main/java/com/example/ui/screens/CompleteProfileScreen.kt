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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.PatientProfile
import com.example.ui.theme.MedixinBlue
import com.example.ui.theme.MedixinBorder
import com.example.ui.theme.MedixinDarkText
import com.example.ui.theme.MedixinMutedText

@Composable
fun CompleteProfileScreen(
    currentProfile: PatientProfile,
    onSaveProfile: (PatientProfile) -> Unit,
    onBack: () -> Unit,
    onSkip: () -> Unit
) {
    var fullName by remember { mutableStateOf(currentProfile.fullName.ifBlank { "Mizanur Rahman Abrar" }) }
    var dob by remember { mutableStateOf(currentProfile.dob.ifBlank { "05/14/1998" }) }
    var gender by remember { mutableStateOf(currentProfile.gender.ifBlank { "Male" }) }
    var phone by remember { mutableStateOf(currentProfile.phone.ifBlank { "(555) 234-8910" }) }
    var address by remember { mutableStateOf(currentProfile.address.ifBlank { "452 Elm Street, Suite 201" }) }
    var genderMenuExpanded by remember { mutableStateOf(false) }

    val genderOptions = listOf("Male", "Female", "Non-binary", "Prefer not to say")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag("complete_profile_screen")
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back Button
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, MedixinBorder, RoundedCornerShape(12.dp))
                        .background(Color(0xFFF8FAFC))
                        .clickable { onBack() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MedixinDarkText,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Step Tracker: Step 2 of 3
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "STEP 2 OF 3",
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = MedixinBlue,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Box(modifier = Modifier.size(width = 20.dp, height = 4.dp).clip(CircleShape).background(MedixinBlue))
                        Box(modifier = Modifier.size(width = 20.dp, height = 4.dp).clip(CircleShape).background(MedixinBlue))
                        Box(modifier = Modifier.size(width = 20.dp, height = 4.dp).clip(CircleShape).background(Color(0xFFE2E8F0)))
                    }
                }

                // Skip Button
                TextButton(onClick = onSkip) {
                    Text(
                        text = "Skip",
                        fontSize = 13.sp,
                        color = MedixinMutedText,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Scrollable Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            ) {
                // Avatar with Camera Overlay
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp, bottom = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(92.dp)
                            .shadow(12.dp, CircleShape, spotColor = MedixinBlue.copy(alpha = 0.2f))
                            .clip(CircleShape)
                            .border(3.dp, MedixinBlue.copy(alpha = 0.3f), CircleShape)
                            .background(Color(0xFFEFF6FF)),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = currentProfile.avatarUrl,
                            contentDescription = "Profile Photo",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    // Camera Icon Overlay
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.BottomEnd)
                            .clip(CircleShape)
                            .background(MedixinBlue)
                            .border(2.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Change Photo",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Text(
                    text = "Complete Your Profile",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MedixinDarkText,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Please fill in your primary health details to personalize your healthcare experience.",
                    fontSize = 13.sp,
                    color = MedixinMutedText,
                    lineHeight = 19.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Full Name
                Text(text = "Full Name *", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = MedixinDarkText)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFF94A3B8), modifier = Modifier.size(20.dp)) },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MedixinBlue,
                        unfocusedBorderColor = MedixinBorder
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Date of Birth
                Text(text = "Date of Birth *", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = MedixinDarkText)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = dob,
                    onValueChange = { dob = it },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = null, tint = Color(0xFF94A3B8), modifier = Modifier.size(20.dp)) },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MedixinBlue,
                        unfocusedBorderColor = MedixinBorder
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Gender
                Text(text = "Gender *", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = MedixinDarkText)
                Spacer(modifier = Modifier.height(6.dp))
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = gender,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { genderMenuExpanded = true },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFF94A3B8), modifier = Modifier.size(20.dp)) },
                        trailingIcon = {
                            IconButton(onClick = { genderMenuExpanded = !genderMenuExpanded }) {
                                Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color(0xFF94A3B8))
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MedixinBlue,
                            unfocusedBorderColor = MedixinBorder
                        )
                    )
                    DropdownMenu(
                        expanded = genderMenuExpanded,
                        onDismissRequest = { genderMenuExpanded = false }
                    ) {
                        genderOptions.forEach { opt ->
                            DropdownMenuItem(
                                text = { Text(opt) },
                                onClick = {
                                    gender = opt
                                    genderMenuExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Phone Number
                Text(text = "Phone Number *", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = MedixinDarkText)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(start = 12.dp, end = 4.dp)
                        ) {
                            Icon(Icons.Default.Phone, contentDescription = null, tint = Color(0xFF94A3B8), modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("+1", fontSize = 13.5.sp, fontWeight = FontWeight.SemiBold, color = MedixinDarkText)
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(modifier = Modifier.width(1.dp).height(18.dp).background(MedixinBorder))
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MedixinBlue,
                        unfocusedBorderColor = MedixinBorder
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Residential Address (Optional)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Residential Address", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = MedixinDarkText)
                    Text(text = "Optional", fontSize = 11.5.sp, color = Color(0xFF94A3B8))
                }
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color(0xFF94A3B8), modifier = Modifier.size(20.dp)) },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MedixinBlue,
                        unfocusedBorderColor = MedixinBorder
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))

                // HIPAA micro notice
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = "Security",
                        tint = Color(0xFF10B981),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Your medical records and personal info are encrypted under HIPAA compliance standards.",
                        fontSize = 11.5.sp,
                        color = MedixinMutedText,
                        lineHeight = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))
            }

            // Fixed Bottom Bar Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 14.dp)
            ) {
                Button(
                    onClick = {
                        val updated = currentProfile.copy(
                            fullName = fullName,
                            dob = dob,
                            gender = gender,
                            phone = phone,
                            address = address
                        )
                        onSaveProfile(updated)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .shadow(8.dp, RoundedCornerShape(12.dp), spotColor = MedixinBlue.copy(alpha = 0.35f)),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MedixinBlue,
                        contentColor = Color.White
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "CONTINUE",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Arrow",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}
