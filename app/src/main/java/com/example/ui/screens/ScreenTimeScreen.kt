package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HourglassBottom
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppDataUsage
import com.example.model.ScreenTimeFilter
import com.example.ui.components.AppLogoIcon
import com.example.ui.theme.AccentBlue
import com.example.ui.theme.AccentGreen
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.AccentRed

@Composable
fun ScreenTimeScreen(
    apps: List<AppDataUsage>,
    selectedFilter: ScreenTimeFilter,
    onSelectFilter: (ScreenTimeFilter) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredApps = apps.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
        it.category.contains(searchQuery, ignoreCase = true)
    }.sortedByDescending { it.getScreenTimeMinutes(selectedFilter) }

    val totalMinutes = filteredApps.sumOf { it.getScreenTimeMinutes(selectedFilter) }
    val totalHours = totalMinutes / 60
    val remMins = totalMinutes % 60
    val totalLaunches = filteredApps.sumOf { it.dailyLaunches }

    val formattedTotalTime = when (selectedFilter) {
        ScreenTimeFilter.JOUR -> "${totalHours}h ${remMins}m"
        ScreenTimeFilter.MOIS -> "${totalHours}h ${remMins}m"
        ScreenTimeFilter.ANNEE -> "${totalHours}h"
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Temps d'Écran",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Suivi du temps passé et fréquence d'ouverture",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Timeframe filter tabs (Jour / Mois / Année)
        item {
            ScreenTimeFilterBar(
                selectedFilter = selectedFilter,
                onSelectFilter = onSelectFilter
            )
        }

        // Hero Metric Card
        item {
            ScreenTimeHeroCard(
                totalFormatted = formattedTotalTime,
                filter = selectedFilter,
                totalMinutes = totalMinutes,
                totalLaunches = totalLaunches
            )
        }

        // Search Bar
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = {
                    Text(
                        "Rechercher une application...",
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Rechercher",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedBorderColor = AccentPurple,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("search_field_screen_time")
            )
        }

        // List Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "TEMPS PAR APPLICATION (${filteredApps.size})",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Durée",
                    style = MaterialTheme.typography.labelMedium,
                    color = AccentPurple,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // List Items
        items(filteredApps, key = { it.id }) { app ->
            AppScreenTimeItemCard(
                app = app,
                filter = selectedFilter,
                maxMinutes = filteredApps.firstOrNull()?.getScreenTimeMinutes(selectedFilter) ?: 1,
                modifier = Modifier.testTag("app_screen_time_item_${app.id}")
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ScreenTimeFilterBar(
    selectedFilter: ScreenTimeFilter,
    onSelectFilter: (ScreenTimeFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            ScreenTimeFilter.values().forEach { filter ->
                val isSelected = filter == selectedFilter
                val bg = if (isSelected) AccentPurple else Color.Transparent
                val textColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(bg)
                        .clickable { onSelectFilter(filter) }
                        .padding(vertical = 10.dp)
                        .testTag("filter_screentime_${filter.name.lowercase()}"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = filter.label,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = textColor
                    )
                }
            }
        }
    }
}

@Composable
fun ScreenTimeHeroCard(
    totalFormatted: String,
    filter: ScreenTimeFilter,
    totalMinutes: Int,
    totalLaunches: Int,
    modifier: Modifier = Modifier
) {
    val dailyLimitMinutes = 360 // 6h
    val isOverLimit = filter == ScreenTimeFilter.JOUR && totalMinutes > dailyLimitMinutes

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isOverLimit) AccentOrange.copy(alpha = 0.5f) else AccentPurple.copy(alpha = 0.35f)
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Temps d'utilisation (${filter.label})",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.SemiBold
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(
                            if (isOverLimit) AccentOrange.copy(alpha = 0.15f)
                            else AccentGreen.copy(alpha = 0.15f)
                        )
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = if (isOverLimit) "Limite proche" else "Objectif respecté",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isOverLimit) AccentOrange else AccentGreen,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Text(
                text = totalFormatted,
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            // 7-day mini distribution chart
            WeeklyDistributionChart(filter = filter)

            // Secondary Quick Stats
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.6f))
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LockOpen,
                        contentDescription = null,
                        tint = AccentBlue,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "$totalLaunches ouvertures",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(14.dp)
                        .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.NotificationsNone,
                        contentDescription = null,
                        tint = AccentOrange,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "142 alertes reçues",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
fun WeeklyDistributionChart(filter: ScreenTimeFilter) {
    val days = listOf("L", "M", "M", "J", "V", "S", "D")
    val heights = listOf(0.45f, 0.65f, 0.50f, 0.85f, 0.90f, 0.70f, 0.40f)

    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            days.forEachIndexed { index, day ->
                val ratio = heights[index]
                val isToday = index == 4
                val barColor = if (isToday) AccentPurple else AccentPurple.copy(alpha = 0.4f)

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .width(18.dp)
                            .height((50 * ratio).dp)
                            .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                            .background(barColor)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = day,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isToday) AccentPurple else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}

@Composable
fun AppScreenTimeItemCard(
    app: AppDataUsage,
    filter: ScreenTimeFilter,
    maxMinutes: Int,
    modifier: Modifier = Modifier
) {
    val minutes = app.getScreenTimeMinutes(filter)
    val ratio = if (maxMinutes > 0) (minutes.toFloat() / maxMinutes).coerceIn(0f, 1f) else 0f
    val animatedProgress by animateFloatAsState(
        targetValue = ratio,
        animationSpec = tween(durationMillis = 500),
        label = "timeProgress"
    )

    val isHighUsage = when (filter) {
        ScreenTimeFilter.JOUR -> minutes >= 90 // 1h30+
        ScreenTimeFilter.MOIS -> minutes >= 2000
        ScreenTimeFilter.ANNEE -> minutes >= 25000
    }

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    AppLogoIcon(logoType = app.logoType, size = 42.dp)
                    Column {
                        Text(
                            text = app.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${app.dailyLaunches} ouvertures/jour",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "${app.formatScreenTime(filter)}${if (filter == ScreenTimeFilter.JOUR) "/jour" else ""}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = if (isHighUsage) AccentOrange else MaterialTheme.colorScheme.onSurface
                    )
                    if (isHighUsage) {
                        Text(
                            text = "Usage soutenu",
                            style = MaterialTheme.typography.labelSmall,
                            color = AccentOrange,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            // Proportional screen time bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(animatedProgress)
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(
                            Brush.horizontalGradient(
                                listOf(AccentPurple.copy(alpha = 0.7f), AccentPurple)
                            )
                        )
                )
            }
        }
    }
}
