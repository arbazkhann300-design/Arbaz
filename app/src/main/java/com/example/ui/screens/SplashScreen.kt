package com.example.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MedixinBlue
import com.example.ui.theme.MedixinDarkText
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(1800)
        onSplashFinished()
    }

    val infiniteTransition = rememberInfiniteTransition(label = "spinner")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        MedixinBlue.copy(alpha = 0.12f),
                        Color(0xFF40C6E8).copy(alpha = 0.05f),
                        Color.White
                    ),
                    radius = 900f
                )
            )
            .clickable { onSplashFinished() }
            .testTag("splash_screen")
    ) {
        // Center Brand Identity
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Medixin Icon Mark
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .shadow(16.dp, RoundedCornerShape(22.dp), spotColor = MedixinBlue.copy(alpha = 0.35f))
                    .clip(RoundedCornerShape(22.dp))
                    .background(MedixinBlue),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Accessibility,
                    contentDescription = "Medixin Logo",
                    tint = Color.White,
                    modifier = Modifier.size(42.dp)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Medixin",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MedixinDarkText,
                letterSpacing = (-0.5).sp
            )
        }

        // Bottom Spinner & Home indicator spacing
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 36.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(28.dp)
                    .rotate(rotation),
                color = MedixinBlue,
                strokeWidth = 3.dp,
                trackColor = MedixinBlue.copy(alpha = 0.15f)
            )
        }
    }
}
