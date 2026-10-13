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
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.Doctor
import com.example.ui.theme.MedixinBlue
import com.example.ui.theme.MedixinBorder
import com.example.ui.theme.MedixinDarkText
import com.example.ui.theme.MedixinMutedText

@Composable
fun SearchDoctorsScreen(
    doctors: List<Doctor>,
    searchQuery: String,
    recentSearches: List<String>,
    favorites: Set<String>,
    selectedSort: String,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
    onSortChange: (String) -> Unit,
    onOpenFilters: () -> Unit,
    onToggleFavorite: (String) -> Unit,
    onDoctorClick: (Doctor) -> Unit,
    onBack: () -> Unit
) {
    var sortMenuExpanded by remember { mutableStateOf(false) }
    val sortOptions = listOf("Rating (High to Low)", "Price (Low to High)", "Experience")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F9FC))
            .statusBarsPadding()
            .testTag("search_doctors_screen")
    ) {
        // App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, MedixinBorder, RoundedCornerShape(12.dp))
                    .background(Color.White)
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

            Text(
                text = "Find a Doctor",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MedixinDarkText
            )

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, MedixinBorder, RoundedCornerShape(12.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Info",
                    tint = MedixinMutedText,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Search Input and Filter Action
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onQueryChange,
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .testTag("search_input"),
                placeholder = { Text("Doctor, specialty or clinic", fontSize = 13.sp, color = Color(0xFF94A3B8)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color(0xFF94A3B8),
                        modifier = Modifier.size(20.dp)
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = onClearQuery) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear", tint = Color(0xFF94A3B8))
                        }
                    }
                },
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MedixinBlue,
                    unfocusedBorderColor = Color(0xFFE2E8F0),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                singleLine = true
            )

            // Filter button
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .shadow(4.dp, RoundedCornerShape(14.dp), spotColor = MedixinBlue.copy(alpha = 0.25f))
                    .clip(RoundedCornerShape(14.dp))
                    .background(MedixinBlue)
                    .clickable { onOpenFilters() }
                    .testTag("filter_btn"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Tune,
                    contentDescription = "Filter",
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
                // Filter badge
                Box(
                    modifier = Modifier
                        .size(9.dp)
                        .align(Alignment.TopEnd)
                        .padding(top = 4.dp, end = 4.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFBBF24))
                        .border(1.5.dp, Color.White, CircleShape)
                )
            }
        }

        // Main List or Empty State
        if (searchQuery.isBlank() && doctors.isEmpty()) {
            // Recent Searches and Popular Specialties state
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                if (recentSearches.isNotEmpty()) {
                    Text(
                        text = "Recent Searches",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MedixinDarkText
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    recentSearches.forEach { search ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.White)
                                .border(1.dp, MedixinBorder, RoundedCornerShape(12.dp))
                                .clickable { onQueryChange(search) }
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.History, contentDescription = null, tint = Color(0xFF94A3B8), modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(search, fontSize = 13.sp, color = MedixinDarkText)
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }

                Text(
                    text = "Popular Specialties",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MedixinDarkText
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("Cardiology", "Dermatology", "General Practice", "Dental", "Neurology").forEach { spec ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.White)
                                .border(1.dp, MedixinBorder, RoundedCornerShape(12.dp))
                                .clickable { onQueryChange(spec) }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Text(spec, fontSize = 12.5.sp, fontWeight = FontWeight.Medium, color = MedixinDarkText)
                        }
                    }
                }
            }
        } else if (doctors.isEmpty()) {
            // No Results State
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFEEF4FF)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.SearchOff,
                        contentDescription = "No Results",
                        tint = MedixinBlue,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "No Doctors Found",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MedixinDarkText
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "We couldn't find any doctor matching your current search or filters. Try adjusting your keywords.",
                    fontSize = 13.sp,
                    color = MedixinMutedText,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onClearQuery,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MedixinBlue)
                ) {
                    Text("Reset Search")
                }
            }
        } else {
            // Results list
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            ) {
                item {
                    // Results count and Sort control
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${doctors.size} doctors found",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MedixinDarkText
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color(0xFF10B981)))
                        }

                        // Sort dropdown trigger
                        Box {
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color.White)
                                    .border(1.dp, MedixinBorder, RoundedCornerShape(10.dp))
                                    .clickable { sortMenuExpanded = true }
                                    .padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = selectedSort,
                                    fontSize = 11.5.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MedixinDarkText
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = MedixinMutedText,
                                    modifier = Modifier.size(16.dp)
                                )
                            }

                            DropdownMenu(
                                expanded = sortMenuExpanded,
                                onDismissRequest = { sortMenuExpanded = false }
                            ) {
                                sortOptions.forEach { sortOpt ->
                                    DropdownMenuItem(
                                        text = { Text(sortOpt, fontSize = 13.sp) },
                                        onClick = {
                                            onSortChange(sortOpt)
                                            sortMenuExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                items(doctors, key = { it.id }) { doc ->
                    DoctorCardSearch(
                        doctor = doc,
                        isFavorite = favorites.contains(doc.id),
                        onToggleFavorite = { onToggleFavorite(doc.id) },
                        onClick = { onDoctorClick(doc) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
private fun DoctorCardSearch(
    doctor: Doctor,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .shadow(2.dp, RoundedCornerShape(14.dp), spotColor = Color.Black.copy(alpha = 0.04f))
            .background(Color.White)
            .border(1.dp, Color(0xFFE7ECF3), RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .padding(14.dp)
            .testTag("doctor_card_${doctor.id}")
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // Doctor Photo (72 x 72) with green availability dot
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFF1F5F9))
                ) {
                    AsyncImage(
                        model = doctor.avatarUrl,
                        contentDescription = doctor.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    if (doctor.availableToday) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .align(Alignment.BottomEnd)
                                .padding(1.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF10B981))
                                .border(1.5.dp, Color.White, CircleShape)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Doctor Information
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = doctor.name,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MedixinDarkText,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Verified",
                            tint = MedixinBlue,
                            modifier = Modifier.size(15.dp)
                        )
                    }

                    Text(
                        text = "${doctor.specialty} • ${doctor.hospital}",
                        fontSize = 12.sp,
                        color = MedixinMutedText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = Color(0xFFF59E0B),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "${doctor.rating}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFD97706)
                        )
                        Text(
                            text = " (${doctor.reviewCount} reviews)",
                            fontSize = 11.sp,
                            color = Color(0xFF94A3B8)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "• ${doctor.experienceYears} yrs exp",
                            fontSize = 11.sp,
                            color = MedixinMutedText
                        )
                    }
                }

                // Favorite button
                IconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color(0xFFEF4444) else Color(0xFF94A3B8),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = Color(0xFFF1F5F9))
            Spacer(modifier = Modifier.height(8.dp))

            // Footer row: Next slot, fee, and View Profile action
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AccessTime,
                            contentDescription = null,
                            tint = MedixinBlue,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Next: ${doctor.nextSlotText}",
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Medium,
                            color = MedixinDarkText
                        )
                    }
                    Text(
                        text = "$${doctor.consultationFee.toInt()} / session",
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = MedixinDarkText
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFEEF4FF))
                        .border(1.dp, Color(0xFFD9E6FF), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "View Profile",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MedixinBlue
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = MedixinBlue,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }
        }
    }
}
