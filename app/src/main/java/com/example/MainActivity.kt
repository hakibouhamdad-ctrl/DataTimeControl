package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.DrawerSection
import com.example.ui.components.AppHeader
import com.example.ui.components.DrawerNavigationContent
import com.example.ui.screens.DataUsageScreen
import com.example.ui.screens.InfosTipsScreen
import com.example.ui.screens.PercentageScreen
import com.example.ui.screens.ScreenTimeScreen
import com.example.ui.theme.AccentBlue
import com.example.ui.theme.DataTimeControlTheme
import com.example.viewmodel.DataTimeControlViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: DataTimeControlViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkMode by viewModel.isDarkMode.collectAsStateWithLifecycle()

            DataTimeControlTheme(darkTheme = isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DataTimeControlApp(
                        viewModel = viewModel,
                        isDarkMode = isDarkMode
                    )
                }
            }
        }
    }
}

@Composable
fun DataTimeControlApp(
    viewModel: DataTimeControlViewModel,
    isDarkMode: Boolean
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val currentSection by viewModel.currentSection.collectAsStateWithLifecycle()
    val apps by viewModel.apps.collectAsStateWithLifecycle()

    val percentFilter by viewModel.percentFilter.collectAsStateWithLifecycle()
    val dataUsageFilter by viewModel.dataUsageFilter.collectAsStateWithLifecycle()
    val networkFilter by viewModel.networkFilter.collectAsStateWithLifecycle()
    val screenTimeFilter by viewModel.screenTimeFilter.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.surface
            ) {
                DrawerNavigationContent(
                    currentSection = currentSection,
                    onSelectSection = { section ->
                        viewModel.selectSection(section)
                        scope.launch { drawerState.close() }
                    },
                    isDarkMode = isDarkMode,
                    onToggleTheme = { viewModel.toggleTheme() }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                AppHeader(
                    title = currentSection.title,
                    isDarkMode = isDarkMode,
                    onToggleTheme = { viewModel.toggleTheme() },
                    onOpenDrawer = {
                        scope.launch { drawerState.open() }
                    }
                )
            },
            bottomBar = {
                // Bottom Navigation Bar for quick access between the 4 sections
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp,
                    modifier = Modifier.testTag("bottom_navigation_bar")
                ) {
                    DrawerSection.values().forEach { section ->
                        val isSelected = currentSection == section
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { viewModel.selectSection(section) },
                            icon = {
                                Text(
                                    text = section.iconEmoji,
                                    fontSize = if (isSelected) 22.sp else 18.sp
                                )
                            },
                            label = {
                                Text(
                                    text = when (section) {
                                        DrawerSection.PERCENTAGE -> "Data %"
                                        DrawerSection.DATA_MB -> "Mo / Go"
                                        DrawerSection.SCREEN_TIME -> "Temps"
                                        DrawerSection.INFOS_TIPS -> "Infos"
                                    },
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = AccentBlue,
                                selectedTextColor = AccentBlue,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                indicatorColor = AccentBlue.copy(alpha = 0.18f)
                            ),
                            modifier = Modifier.testTag("nav_item_${section.name.lowercase()}")
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                AnimatedContent(
                    targetState = currentSection,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "sectionTransition"
                ) { section ->
                    when (section) {
                        DrawerSection.PERCENTAGE -> PercentageScreen(
                            apps = apps,
                            selectedFilter = percentFilter,
                            onSelectFilter = { viewModel.setPercentFilter(it) },
                            searchQuery = searchQuery,
                            onSearchQueryChange = { viewModel.setSearchQuery(it) }
                        )
                        DrawerSection.DATA_MB -> DataUsageScreen(
                            apps = apps,
                            selectedFilter = dataUsageFilter,
                            onSelectFilter = { viewModel.setDataUsageFilter(it) },
                            networkFilter = networkFilter,
                            onSelectNetworkFilter = { viewModel.setNetworkFilter(it) },
                            searchQuery = searchQuery,
                            onSearchQueryChange = { viewModel.setSearchQuery(it) }
                        )
                        DrawerSection.SCREEN_TIME -> ScreenTimeScreen(
                            apps = apps,
                            selectedFilter = screenTimeFilter,
                            onSelectFilter = { viewModel.setScreenTimeFilter(it) },
                            searchQuery = searchQuery,
                            onSearchQueryChange = { viewModel.setSearchQuery(it) }
                        )
                        DrawerSection.INFOS_TIPS -> InfosTipsScreen()
                    }
                }
            }
        }
    }
}
