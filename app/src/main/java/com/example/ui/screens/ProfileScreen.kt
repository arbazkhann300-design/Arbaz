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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.model.PatientProfile
import com.example.ui.theme.MedixinBlue
import com.example.ui.theme.MedixinBorder
import com.example.ui.theme.MedixinDarkText
import com.example.ui.theme.MedixinMutedText

@Composable
fun ProfileScreen(
    patientProfile: PatientProfile,
    hydrationProgress: Float,
    onEditProfile: () -> Unit,
    onLocationSettings: () -> Unit,
    onSignOut: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .verticalScroll(rememberScrollState())
            .padding(bottom = 90.dp)
            .testTag("profile_screen")
    ) {
        // App Bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
            Text(
                text = "Patient Profile",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MedixinDarkText
            )
            Text(
                text = "Manage personal medical information and settings",
                fontSize = 12.sp,
                color = MedixinMutedText
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Profile Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(20.dp))
                .shadow(3.dp, RoundedCornerShape(20.dp), spotColor = Color.Black.copy(alpha = 0.05f))
                .background(Color.White)
                .border(1.dp, MedixinBorder, RoundedCornerShape(20.dp))
                .padding(18.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(68.dp)
                            .clip(CircleShape)
                            .border(2.dp, MedixinBlue, CircleShape)
                    ) {
                        AsyncImage(
                            model = patientProfile.avatarUrl,
                            contentDescription = patientProfile.fullName,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = patientProfile.fullName,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = MedixinDarkText
                        )
                        Text(
                            text = patientProfile.email,
                            fontSize = 12.sp,
                            color = MedixinMutedText
                        )
                        Text(
                            text = patientProfile.phone,
                            fontSize = 12.sp,
                            color = MedixinMutedText
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFEEF4FF))
                            .clickable { onEditProfile() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Profile",
                            tint = MedixinBlue,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = Color(0xFFF1F5F9))
                Spacer(modifier = Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = MedixinBlue,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = patientProfile.address,
                        fontSize = 12.sp,
                        color = MedixinDarkText
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Medical ID Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(Color(0xFFEEF4FF))
                .border(1.dp, Color(0xFFD9E6FF), RoundedCornerShape(18.dp))
                .padding(16.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Badge, contentDescription = null, tint = MedixinBlue, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Medixin Digital Health ID", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E3A8A))
                    }
                    Text("#MED-891024", fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = MedixinBlue)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Blood Type", fontSize = 11.sp, color = MedixinMutedText)
                        Text("O+ Positive", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MedixinDarkText)
                    }
                    Column {
                        Text("Allergies", fontSize = 11.sp, color = MedixinMutedText)
                        Text("Penicillin (Mild)", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MedixinDarkText)
                    }
                    Column {
                        Text("Emergency", fontSize = 11.sp, color = MedixinMutedText)
                        Text("(555) 901-4421", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MedixinDarkText)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Settings Items
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(Color.White)
                .border(1.dp, MedixinBorder, RoundedCornerShape(18.dp))
        ) {
            SettingRow(
                icon = Icons.Default.LocationOn,
                title = "Location Preference",
                subtitle = "San Francisco, CA (94102)",
                onClick = onLocationSettings
            )
            HorizontalDivider(color = Color(0xFFF1F5F9))
            SettingRow(
                icon = Icons.Default.Notifications,
                title = "Notifications & Reminders",
                subtitle = "SMS, Push notifications enabled",
                onClick = {}
            )
            HorizontalDivider(color = Color(0xFFF1F5F9))
            SettingRow(
                icon = Icons.Default.Lock,
                title = "HIPAA Privacy & Security",
                subtitle = "End-to-end encrypted medical data",
                onClick = {}
            )
            HorizontalDivider(color = Color(0xFFF1F5F9))
            SettingRow(
                icon = Icons.Default.Help,
                title = "Help & Clinical Support",
                subtitle = "FAQ and 24/7 care concierge",
                onClick = {}
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Sign Out Button
        Box(modifier = Modifier.padding(horizontal = 20.dp)) {
            Button(
                onClick = onSignOut,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFEF2F2),
                    contentColor = Color(0xFFDC2626)
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Logout, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Sign Out", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun SettingRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFF8FAFC)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = MedixinBlue, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(title, fontSize = 13.5.sp, fontWeight = FontWeight.SemiBold, color = MedixinDarkText)
                Text(subtitle, fontSize = 11.5.sp, color = MedixinMutedText)
            }
        }

        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color(0xFFCBD5E1), modifier = Modifier.size(16.dp))
    }
}
