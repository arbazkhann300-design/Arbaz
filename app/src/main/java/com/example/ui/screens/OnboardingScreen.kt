package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.theme.MedixinBlue
import com.example.ui.theme.MedixinBlueDeep
import com.example.ui.theme.MedixinCyan

@Composable
fun OnboardingScreen(
    initialPage: Int = 0,
    onFinish: () -> Unit
) {
    var currentPage by remember { mutableIntStateOf(initialPage) }

    val images = listOf(
        "https://lh3.googleusercontent.com/aida-public/AB6AXuCWtRLnQtFFbNUlpBOhLRq7pFmXk41gV2OwxKYGSY5O8BjU0bG1fIGOdXc-jr27isPMTFn267PvncVHX42rIibWNLBkbAiZuvhqSnaWFyfm4WlbWSXQloOGTBfQuKeq02k58hry47NC599sFsnlFS9GRMRU3fRsK-h4nEM7JDO8L-ENA1TOQNUC7Grsx_568x8Wxff6v1v708DYz9BLPNAN9jCFb-jmtMMn2hycYzdP-sWGu79jE288",
        "https://lh3.googleusercontent.com/aida-public/AB6AXuDadpH5YTiHsL2ilXBKI9NfdZ0QmZae4Jz8K79dO1SNV_T4oGGfYXOPqY9DC_d8NngN5wThzudW0XoPup9ILN7lB5wfjYF3aCEPHJbmvhrG-cqbNGbk8o2N_f0VxEKBjKidGXowZg_4apjvas2XkOynLUNxKXI8qcPmScRIdCR4A_VJExKPnMPLmpf9SzC7ft4-8NaFiVy0AeLTDKBuLMT2Ft_dHAlQoOzlHM4fuVIXlLOPDndrUEUR",
        "https://lh3.googleusercontent.com/aida-public/AB6AXuCYoD5nnretb9PC-sYPHOUNLFAp7YxD8TTcwnCtKA23hkSRRwYr4T827AYJ3Mfb4S1UAGFpjsM-E-n-aAgpE0G1O2wMK-XasFJKrDFHKENoTwcr_aEhkQkUsAVW1_0kE6PRhBBe06mDd1faIYApK4QYy7K8q-8HjkQfmIKeI9r15-ypcOhVr9LPYh-3TaegJ5fNtRTEMClhNS0YhTpd0HAt8idhGPLN-7pKM04r1yDkXeEjztFeR2cp"
    )

    val titles = listOf(
        "Book Consultation\nFor Your Better\nSolution",
        "Choose Specialist\nFrom Top-Rated\nClinics",
        "Your Healthcare,\nSimplified"
    )

    val subtitles = listOf(
        "Our team will help you find the best consultation for your needs.",
        "Connect with verified cardiologists, dermatologists, dentists and therapists.",
        "Keep appointments, reminders and your healthcare information organized in one place."
    )

    // Pulse animation for circular NEXT button
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F9FC))
            .testTag("onboarding_screen")
    ) {
        // Upper Visual Section (52% height)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.15f)
                .background(Color(0xFFE8F1FD))
        ) {
            AnimatedContent(
                targetState = currentPage,
                transitionSpec = { fadeIn(tween(400)) togetherWith fadeOut(tween(400)) },
                label = "hero_image",
                modifier = Modifier.fillMaxSize()
            ) { page ->
                AsyncImage(
                    model = images[page],
                    contentDescription = "Onboarding Visual",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Subtle gradient transition into bottom panel
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color(0xFF1E5BD8).copy(alpha = 0.2f))
                        )
                    )
            )
        }

        // Curved Bottom Panel (48% height)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.05f)
                .clip(RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(MedixinBlue, Color(0xFF2062E0), MedixinBlueDeep)
                    )
                )
                .navigationBarsPadding()
                .padding(horizontal = 28.dp, vertical = 24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Headline and Body
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = titles[currentPage],
                        fontSize = 24.sp,
                        lineHeight = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        letterSpacing = (-0.3).sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = subtitles[currentPage],
                        fontSize = 13.5.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White.copy(alpha = 0.9f),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Pagination Dots
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        for (i in 0..2) {
                            if (i == currentPage) {
                                Box(
                                    modifier = Modifier
                                        .width(24.dp)
                                        .height(8.dp)
                                        .clip(CircleShape)
                                        .background(Color.White)
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(Color.White.copy(alpha = 0.4f))
                                )
                            }
                        }
                    }
                }

                // Action Controls
                if (currentPage < 2) {
                    // Circular Glowing NEXT button + SKIP row
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(76.dp)
                                .clickable {
                                    currentPage++
                                }
                                .testTag("btn_next")
                        ) {
                            // Pulsing Outer Cyan Halo Ring
                            Box(
                                modifier = Modifier
                                    .size(72.dp)
                                    .scale(pulseScale)
                                    .clip(CircleShape)
                                    .border(1.5.dp, MedixinCyan.copy(alpha = 0.4f), CircleShape)
                                    .background(MedixinCyan.copy(alpha = 0.16f))
                            )

                            // Inner Glow Action Button
                            Box(
                                modifier = Modifier
                                    .size(58.dp)
                                    .clip(CircleShape)
                                    .shadow(8.dp, CircleShape, spotColor = MedixinCyan)
                                    .background(
                                        Brush.linearGradient(
                                            colors = listOf(MedixinCyan, Color(0xFF2598DE))
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "NEXT",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    letterSpacing = 0.5.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Skip button on bottom right
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(
                                onClick = onFinish,
                                modifier = Modifier.testTag("btn_skip")
                            ) {
                                Text(
                                    text = "SKIP",
                                    fontSize = 13.5.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White.copy(alpha = 0.85f)
                                )
                            }
                        }
                    }
                } else {
                    // Screen 04 Layout: GET STARTED Pill + BACK action
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = onFinish,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp)
                                .shadow(8.dp, RoundedCornerShape(27.dp), spotColor = Color.Black.copy(alpha = 0.2f))
                                .testTag("btn_get_started"),
                            shape = RoundedCornerShape(27.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = MedixinBlue
                            )
                        ) {
                            Text(
                                text = "GET STARTED",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        TextButton(
                            onClick = { currentPage-- },
                            modifier = Modifier.testTag("btn_back")
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back",
                                    tint = Color.White.copy(alpha = 0.85f),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "BACK",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White.copy(alpha = 0.85f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
