package com.example.ui.screens

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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.model.Doctor
import com.example.model.PatientReview
import com.example.ui.theme.MedixinBlue
import com.example.ui.theme.MedixinBorder
import com.example.ui.theme.MedixinDarkText
import com.example.ui.theme.MedixinMutedText

@Composable
fun DoctorReviewsScreen(
    doctor: Doctor,
    reviews: List<PatientReview>,
    onBack: () -> Unit,
    onBookClick: () -> Unit
) {
    var selectedFilterIndex by remember { mutableIntStateOf(0) }
    val filters = listOf("All (128)", "5 Star ★ (105)", "4 Star ★ (15)", "Recent")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag("doctor_reviews_screen")
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

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Patient Reviews",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MedixinDarkText
                    )
                    Text(
                        text = "${doctor.name} • ${doctor.specialty}",
                        fontSize = 11.5.sp,
                        color = MedixinMutedText
                    )
                }

                // Verified Pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFECFDF5))
                        .border(1.dp, Color(0xFFA7F3D0), RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color(0xFF10B981)))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Verified",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF047857)
                        )
                    }
                }
            }

            // Scrollable Content
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 20.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(14.dp))

                    // Informational Banner
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color(0xFFEEF4FF))
                            .border(1.dp, Color(0xFFD9E6FF), RoundedCornerShape(14.dp))
                            .padding(12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(MedixinBlue),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("i", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Showing authentic patient feedback from verified consultations.",
                                fontSize = 12.sp,
                                color = Color(0xFF1E3A8A)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Rating Summary Card
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
                            // Top Half: Big Score + Bars
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Score Column
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .padding(end = 16.dp)
                                        .width(100.dp)
                                ) {
                                    Text(
                                        text = "${doctor.rating}",
                                        fontSize = 40.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = MedixinDarkText,
                                        lineHeight = 42.sp
                                    )
                                    Row {
                                        repeat(5) {
                                            Icon(
                                                imageVector = Icons.Default.Star,
                                                contentDescription = null,
                                                tint = Color(0xFFF59E0B),
                                                modifier = Modifier.size(14.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "${doctor.reviewCount} reviews",
                                        fontSize = 11.sp,
                                        color = MedixinMutedText
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .width(1.dp)
                                        .height(70.dp)
                                        .background(MedixinBorder)
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                // Rating Distribution Bars
                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    RatingBarRow(stars = "5", count = "105", progress = 0.82f)
                                    RatingBarRow(stars = "4", count = "15", progress = 0.12f)
                                    RatingBarRow(stars = "3", count = "5", progress = 0.04f)
                                    RatingBarRow(stars = "2", count = "2", progress = 0.02f)
                                    RatingBarRow(stars = "1", count = "1", progress = 0.01f)
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))
                            HorizontalDivider(color = Color(0xFFF1F5F9))
                            Spacer(modifier = Modifier.height(12.dp))

                            // Bottom 3 Micro-Metrics
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                MetricColumn(value = "98%", label = "Recommend")
                                Box(modifier = Modifier.width(1.dp).height(24.dp).background(MedixinBorder))
                                MetricColumn(value = "${doctor.experienceYears} yrs", label = "Experience")
                                Box(modifier = Modifier.width(1.dp).height(24.dp).background(MedixinBorder))
                                MetricColumn(value = "< 10 min", label = "Avg. Wait")
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Horizontal Filter Pills
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        filters.forEachIndexed { idx, label ->
                            val isSelected = selectedFilterIndex == idx
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(if (isSelected) MedixinBlue else Color.White)
                                    .border(1.dp, if (isSelected) MedixinBlue else MedixinBorder, RoundedCornerShape(20.dp))
                                    .clickable { selectedFilterIndex = idx }
                                    .padding(horizontal = 14.dp, vertical = 7.dp)
                            ) {
                                Text(
                                    text = label,
                                    fontSize = 11.5.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (isSelected) Color.White else MedixinDarkText
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Verified Patient Reviews",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MedixinDarkText
                        )
                        Text(
                            text = "Most Relevant",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MedixinBlue
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                }

                // Reviews List
                items(reviews, key = { it.id }) { rev ->
                    ReviewCard(review = rev)
                    Spacer(modifier = Modifier.height(12.dp))
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }

            // Fixed Bottom Action: Book Consultation
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .border(1.dp, MedixinBorder)
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Button(
                    onClick = onBookClick,
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
                            text = "Book Consultation ($${doctor.consultationFee.toInt()})",
                            fontSize = 14.5.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.3.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun RatingBarRow(stars: String, count: String, progress: Float) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(stars, fontSize = 11.sp, color = MedixinMutedText, modifier = Modifier.width(12.dp))
        Spacer(modifier = Modifier.width(4.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .weight(1f)
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = MedixinBlue,
            trackColor = Color(0xFFF1F5F9)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(count, fontSize = 11.sp, color = MedixinMutedText, modifier = Modifier.width(22.dp))
    }
}

@Composable
private fun MetricColumn(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 13.5.sp, fontWeight = FontWeight.Bold, color = MedixinDarkText)
        Text(label, fontSize = 10.5.sp, color = MedixinMutedText)
    }
}

@Composable
private fun ReviewCard(review: PatientReview) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp, MedixinBorder, RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFD9E6FF)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = review.initials,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MedixinBlue
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = review.patientName,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = MedixinDarkText
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Verified",
                                tint = MedixinBlue,
                                modifier = Modifier.size(13.dp)
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Row {
                                repeat(review.rating) {
                                    Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(12.dp))
                                }
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("• ${review.timeAgo}", fontSize = 11.sp, color = Color(0xFF94A3B8))
                        }
                    }
                }

                // Tag
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFF8FAFC))
                        .border(1.dp, MedixinBorder, RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = review.consultationType,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MedixinDarkText
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = review.comment,
                fontSize = 12.5.sp,
                lineHeight = 18.sp,
                color = MedixinDarkText
            )

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = Color(0xFFF8FAFC))
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("✓", fontSize = 11.sp, color = Color(0xFF059669), fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Verified Cardiology Patient",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF059669)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ThumbUp,
                        contentDescription = "Helpful",
                        tint = Color(0xFFF59E0B),
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Helpful (${review.helpfulCount})",
                        fontSize = 11.sp,
                        color = MedixinMutedText
                    )
                }
            }
        }
    }
}
