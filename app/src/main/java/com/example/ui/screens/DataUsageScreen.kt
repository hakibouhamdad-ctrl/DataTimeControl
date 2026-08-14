package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NetworkCell
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Wifi
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppDataUsage
import com.example.model.DataUsageFilter
import com.example.model.NetworkTypeFilter
import com.example.ui.components.AppLogoIcon
import com.example.ui.theme.AccentBlue
import com.example.ui.theme.AccentGreen
import com.example.ui.theme.AccentOrange

@Composable
fun DataUsageScreen(
    apps: List<AppDataUsage>,
    selectedFilter: DataUsageFilter,
    onSelectFilter: (DataUsageFilter) -> Unit,
    networkFilter: NetworkTypeFilter,
    onSelectNetworkFilter: (NetworkTypeFilter) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredApps = apps.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
        it.category.contains(searchQuery, ignoreCase = true)
    }.sortedByDescending { it.getDataMb(selectedFilter, networkFilter) }

    val totalMb = filteredApps.sumOf { it.getDataMb(selectedFilter, networkFilter).toDouble() }.toFloat()
    val totalWifiMb = filteredApps.sumOf { it.getDataMb(selectedFilter, NetworkTypeFilter.WIFI).toDouble() }.toFloat()
    val totalMobileMb = filteredApps.sumOf { it.getDataMb(selectedFilter, NetworkTypeFilter.MOBILE).toDouble() }.toFloat()

    val formattedTotal = if (totalMb >= 1000f) {
        String.format(java.util.Locale.US, "%.2f Go", totalMb / 1024f)
    } else {
        String.format(java.util.Locale.US, "%.0f Mo", totalMb)
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
                text = "Consommation en Mo / Go",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Volume précis des données téléchargées et envoyées",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Timeframe Filters (Aujourd'hui / Ce mois / Total)
        item {
            DataFilterBar(
                selectedFilter = selectedFilter,
                onSelectFilter = onSelectFilter
            )
        }

        // Network Type Pills (Tout / Wi-Fi / Données mobiles)
        item {
            NetworkTypeFilterBar(
                selectedFilter = networkFilter,
                onSelectFilter = onSelectNetworkFilter
            )
        }

        // Hero Summary Card
        item {
            DataHeroMetricCard(
                totalFormatted = formattedTotal,
                periodLabel = selectedFilter.label,
                wifiMb = totalWifiMb,
                mobileMb = totalMobileMb
            )
        }

        // Search Bar
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = {
                    Text(
                        "Filtrer par nom ou type...",
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
                    focusedBorderColor = AccentGreen,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("search_field_data_usage")
            )
        }

        // App List Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "DÉTAIL PAR APPLICATION (${filteredApps.size})",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Volume",
                    style = MaterialTheme.typography.labelMedium,
                    color = AccentGreen,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // List Items
        items(filteredApps, key = { it.id }) { app ->
            AppDataUsageItemCard(
                app = app,
                filter = selectedFilter,
                netFilter = networkFilter,
                maxMb = filteredApps.firstOrNull()?.getDataMb(selectedFilter, networkFilter) ?: 1f,
                modifier = Modifier.testTag("app_data_item_${app.id}")
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun DataFilterBar(
    selectedFilter: DataUsageFilter,
    onSelectFilter: (DataUsageFilter) -> Unit,
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
            DataUsageFilter.values().forEach { filter ->
                val isSelected = filter == selectedFilter
                val bg = if (isSelected) AccentGreen else Color.Transparent
                val textColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(bg)
                        .clickable { onSelectFilter(filter) }
                        .padding(vertical = 10.dp)
                        .testTag("filter_data_${filter.name.lowercase()}"),
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
fun NetworkTypeFilterBar(
    selectedFilter: NetworkTypeFilter,
    onSelectFilter: (NetworkTypeFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        NetworkTypeFilter.values().forEach { filter ->
            val isSelected = filter == selectedFilter
            val chipBg = if (isSelected) {
                MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
            val chipBorder = if (isSelected) AccentBlue else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
            val textColor = if (isSelected) AccentBlue else MaterialTheme.colorScheme.onSurfaceVariant

            Surface(
                onClick = { onSelectFilter(filter) },
                shape = RoundedCornerShape(20.dp),
                color = chipBg,
                border = androidx.compose.foundation.BorderStroke(1.dp, chipBorder),
                modifier = Modifier
                    .weight(1f)
                    .testTag("filter_net_${filter.name.lowercase()}")
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    when (filter) {
                        NetworkTypeFilter.WIFI -> {
                            Icon(
                                imageVector = Icons.Default.Wifi,
                                contentDescription = null,
                                tint = textColor,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                        }
                        NetworkTypeFilter.MOBILE -> {
                            Icon(
                                imageVector = Icons.Default.NetworkCell,
                                contentDescription = null,
                                tint = textColor,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                        }
                        NetworkTypeFilter.ALL -> {}
                    }
                    Text(
                        text = filter.label,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = textColor
                    )
                }
            }
        }
    }
}

@Composable
fun DataHeroMetricCard(
    totalFormatted: String,
    periodLabel: String,
    wifiMb: Float,
    mobileMb: Float,
    modifier: Modifier = Modifier
) {
    val total = (wifiMb + mobileMb).coerceAtLeast(1f)
    val wifiRatio = (wifiMb / total).coerceIn(0f, 1f)

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            AccentGreen.copy(alpha = 0.35f)
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
                    text = "Total Consommé ($periodLabel)",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.SemiBold
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(AccentGreen.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "Statut : Normal",
                        style = MaterialTheme.typography.labelSmall,
                        color = AccentGreen,
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

            // Wi-Fi vs Mobile Split Bar
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color(0xFF3B82F6).copy(alpha = 0.2f))
                ) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        // Wi-Fi part (Blue)
                        Box(
                            modifier = Modifier
                                .weight(wifiRatio.coerceAtLeast(0.01f))
                                .fillMaxSize()
                                .background(AccentBlue)
                        )
                        // Mobile part (Green)
                        Box(
                            modifier = Modifier
                                .weight((1f - wifiRatio).coerceAtLeast(0.01f))
                                .fillMaxSize()
                                .background(AccentGreen)
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(AccentBlue))
                        Text(
                            text = "Wi-Fi : ${if (wifiMb >= 1000f) String.format(java.util.Locale.US, "%.1f Go", wifiMb / 1024f) else String.format(java.util.Locale.US, "%.0f Mo", wifiMb)}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(AccentGreen))
                        Text(
                            text = "Mobile : ${if (mobileMb >= 1000f) String.format(java.util.Locale.US, "%.1f Go", mobileMb / 1024f) else String.format(java.util.Locale.US, "%.0f Mo", mobileMb)}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AppDataUsageItemCard(
    app: AppDataUsage,
    filter: DataUsageFilter,
    netFilter: NetworkTypeFilter,
    maxMb: Float,
    modifier: Modifier = Modifier
) {
    val mb = app.getDataMb(filter, netFilter)
    val ratio = if (maxMb > 0f) (mb / maxMb).coerceIn(0f, 1f) else 0f
    val animatedProgress by animateFloatAsState(
        targetValue = ratio,
        animationSpec = tween(durationMillis = 500),
        label = "dataProgress"
    )

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
                            text = when (filter) {
                                DataUsageFilter.AUJOURDHUI -> "${app.dataMbDayWifi.toInt()} Mo Wi-Fi • ${app.dataMbDayMobile.toInt()} Mo Mobile"
                                DataUsageFilter.CE_MOIS -> "${(app.dataMbMonthWifi / 1024f).let { String.format(java.util.Locale.US, "%.1f Go", it) }} Wi-Fi • ${(app.dataMbMonthMobile / 1024f).let { String.format(java.util.Locale.US, "%.1f Go", it) }} Mobile"
                                DataUsageFilter.TOTAL -> "${(app.dataMbTotalWifi / 1024f).toInt()} Go Wi-Fi • ${(app.dataMbTotalMobile / 1024f).toInt()} Go Mobile"
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Text(
                    text = app.formatDataUsage(filter, netFilter),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = AccentGreen
                )
            }

            // Consumption proportional gauge
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
                                listOf(AccentGreen.copy(alpha = 0.7f), AccentGreen)
                            )
                        )
                )
            }
        }
    }
}
