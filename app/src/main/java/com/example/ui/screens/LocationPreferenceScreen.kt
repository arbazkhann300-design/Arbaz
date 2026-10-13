package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MedixinBlue
import com.example.ui.theme.MedixinBorder
import com.example.ui.theme.MedixinCyan
import com.example.ui.theme.MedixinDarkText
import com.example.ui.theme.MedixinMutedText

@Composable
fun LocationPreferenceScreen(
    onLocationConfirmed: (city: String, zip: String) -> Unit,
    onBack: () -> Unit,
    onSkip: () -> Unit
) {
    var city by remember { mutableStateOf("San Francisco, CA") }
    var zipCode by remember { mutableStateOf("94102") }
    var locationGranted by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "radar")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag("location_preference_screen")
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

                // Step Tracker: Step 3 of 3
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "STEP 3 OF 3",
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = MedixinBlue,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Box(modifier = Modifier.size(width = 20.dp, height = 4.dp).clip(CircleShape).background(MedixinBlue))
                        Box(modifier = Modifier.size(width = 20.dp, height = 4.dp).clip(CircleShape).background(MedixinBlue))
                        Box(modifier = Modifier.size(width = 20.dp, height = 4.dp).clip(CircleShape).background(MedixinBlue))
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
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Radar / GPS Graphic with Pin
                Box(
                    modifier = Modifier
                        .size(170.dp)
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Pulsing Outer Rings
                    Box(
                        modifier = Modifier
                            .size(150.dp)
                            .scale(pulseScale)
                            .clip(CircleShape)
                            .border(1.5.dp, Color(0xFFCCE3FF), CircleShape)
                            .background(Color(0xFFF0F7FF).copy(alpha = 0.4f))
                    )

                    Box(
                        modifier = Modifier
                            .size(105.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE0EFFF))
                    )

                    // Floating Badge: 12 Nearby
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .shadow(4.dp, RoundedCornerShape(12.dp), spotColor = MedixinBlue.copy(alpha = 0.15f))
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color(0xFF10B981)))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("12 Nearby", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MedixinDarkText)
                        }
                    }

                    // Main Pin Diamond
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .rotate(45f)
                            .shadow(8.dp, RoundedCornerShape(16.dp), spotColor = MedixinBlue.copy(alpha = 0.35f))
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(MedixinBlue, MedixinCyan)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Pin",
                            tint = Color.White,
                            modifier = Modifier
                                .size(32.dp)
                                .rotate(-45f)
                        )
                    }
                }

                Text(
                    text = "Find Care Near You",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MedixinDarkText,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Enable location services so Medixin can recommend top-rated clinics, pharmacies, and specialists closest to your current area.",
                    fontSize = 13.5.sp,
                    color = MedixinMutedText,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Primary Button: ALLOW LOCATION ACCESS
                Button(
                    onClick = {
                        locationGranted = true
                        city = "San Francisco, CA"
                        zipCode = "94103"
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .shadow(8.dp, RoundedCornerShape(14.dp), spotColor = MedixinBlue.copy(alpha = 0.3f)),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (locationGranted) Color(0xFF059669) else MedixinBlue,
                        contentColor = Color.White
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = if (locationGranted) Icons.Default.CheckCircle else Icons.Default.NearMe,
                            contentDescription = "Location icon",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (locationGranted) "LOCATION ACCESS GRANTED ✓" else "ALLOW LOCATION ACCESS",
                            fontSize = 13.5.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "We protect your privacy. Location is never stored without consent.",
                    fontSize = 11.sp,
                    color = Color(0xFF94A3B8),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                // OR ENTER LOCATION MANUALLY
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    HorizontalDivider(modifier = Modifier.weight(1f), color = MedixinBorder)
                    Text(
                        text = "OR ENTER LOCATION MANUALLY",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF94A3B8),
                        letterSpacing = 0.5.sp,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                    HorizontalDivider(modifier = Modifier.weight(1f), color = MedixinBorder)
                }

                Spacer(modifier = Modifier.height(18.dp))

                // City or Region
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "City or Region", fontSize = 12.5.sp, fontWeight = FontWeight.SemiBold, color = MedixinDarkText)
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = city,
                        onValueChange = { city = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("e.g. San Francisco, CA", fontSize = 13.sp) },
                        leadingIcon = { Icon(Icons.Default.LocationCity, contentDescription = null, tint = Color(0xFF94A3B8), modifier = Modifier.size(18.dp)) },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MedixinBlue,
                            unfocusedBorderColor = MedixinBorder
                        ),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Postal / ZIP Code
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Postal / ZIP Code", fontSize = 12.5.sp, fontWeight = FontWeight.SemiBold, color = MedixinDarkText)
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = zipCode,
                        onValueChange = { zipCode = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("e.g. 94102", fontSize = 13.sp) },
                        leadingIcon = { Icon(Icons.Default.Mail, contentDescription = null, tint = Color(0xFF94A3B8), modifier = Modifier.size(18.dp)) },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MedixinBlue,
                            unfocusedBorderColor = MedixinBorder
                        ),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            // Fixed Bottom Bar Button: CONTINUE TO CONSULTATIONS ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 14.dp)
            ) {
                OutlinedButton(
                    onClick = { onLocationConfirmed(city, zipCode) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = Brush.horizontalGradient(listOf(MedixinBlue.copy(alpha = 0.5f), MedixinBlue))
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color(0xFFEFF6FF),
                        contentColor = MedixinBlue
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "CONTINUE TO CONSULTATIONS",
                            fontSize = 13.5.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.3.sp
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
