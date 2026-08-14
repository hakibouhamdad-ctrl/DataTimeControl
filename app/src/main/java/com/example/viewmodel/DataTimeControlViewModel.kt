package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.model.AppDataUsage
import com.example.model.AppLogoType
import com.example.model.DataUsageFilter
import com.example.model.DrawerSection
import com.example.model.NetworkTypeFilter
import com.example.model.PercentFilter
import com.example.model.ScreenTimeFilter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine

class DataTimeControlViewModel : ViewModel() {

    // Theme state: Default is DARK MODE (true)
    private val _isDarkMode = MutableStateFlow(true)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    // Navigation Drawer current section: Default is PERCENTAGE
    private val _currentSection = MutableStateFlow(DrawerSection.PERCENTAGE)
    val currentSection: StateFlow<DrawerSection> = _currentSection.asStateFlow()

    // View 1 Filters
    private val _percentFilter = MutableStateFlow(PercentFilter.JOUR)
    val percentFilter: StateFlow<PercentFilter> = _percentFilter.asStateFlow()

    // View 2 Filters
    private val _dataUsageFilter = MutableStateFlow(DataUsageFilter.AUJOURDHUI)
    val dataUsageFilter: StateFlow<DataUsageFilter> = _dataUsageFilter.asStateFlow()

    private val _networkFilter = MutableStateFlow(NetworkTypeFilter.ALL)
    val networkFilter: StateFlow<NetworkTypeFilter> = _networkFilter.asStateFlow()

    // View 3 Filters
    private val _screenTimeFilter = MutableStateFlow(ScreenTimeFilter.JOUR)
    val screenTimeFilter: StateFlow<ScreenTimeFilter> = _screenTimeFilter.asStateFlow()

    // Search query
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Daily Data Limit in GB (e.g., 5.0 GB)
    private val _dailyDataLimitGb = MutableStateFlow(5.0f)
    val dailyDataLimitGb: StateFlow<Float> = _dailyDataLimitGb.asStateFlow()

    // Daily Screen Time Limit in Hours (e.g., 6.0 h)
    private val _dailyScreenLimitHours = MutableStateFlow(6.0f)
    val dailyScreenLimitHours: StateFlow<Float> = _dailyScreenLimitHours.asStateFlow()

    // Raw app dataset
    private val _apps = MutableStateFlow(generateInitialApps())
    val apps: StateFlow<List<AppDataUsage>> = _apps.asStateFlow()

    fun toggleTheme() {
        _isDarkMode.value = !_isDarkMode.value
    }

    fun setDarkMode(dark: Boolean) {
        _isDarkMode.value = dark
    }

    fun selectSection(section: DrawerSection) {
        _currentSection.value = section
    }

    fun setPercentFilter(filter: PercentFilter) {
        _percentFilter.value = filter
    }

    fun setDataUsageFilter(filter: DataUsageFilter) {
        _dataUsageFilter.value = filter
    }

    fun setNetworkFilter(filter: NetworkTypeFilter) {
        _networkFilter.value = filter
    }

    fun setScreenTimeFilter(filter: ScreenTimeFilter) {
        _screenTimeFilter.value = filter
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setDailyDataLimit(limitGb: Float) {
        _dailyDataLimitGb.value = limitGb
    }

    fun setDailyScreenLimit(limitHours: Float) {
        _dailyScreenLimitHours.value = limitHours
    }

    private fun generateInitialApps(): List<AppDataUsage> {
        return listOf(
            AppDataUsage(
                id = "youtube",
                name = "YouTube",
                category = "Streaming Vidéo",
                logoType = AppLogoType.YOUTUBE,
                percentDay = 45.0f,
                percentMonth = 38.5f,
                percentGlobal = 41.2f,
                dataMbDay = 1843.2f, // ~1.8 Go
                dataMbDayWifi = 1228.8f,
                dataMbDayMobile = 614.4f,
                dataMbMonth = 42500.0f,
                dataMbMonthWifi = 32000.0f,
                dataMbMonthMobile = 10500.0f,
                dataMbTotal = 195000.0f,
                dataMbTotalWifi = 150000.0f,
                dataMbTotalMobile = 45000.0f,
                screenMinutesDay = 100, // 1h 40m
                screenMinutesMonth = 2850,
                screenMinutesYear = 32400,
                dailyLaunches = 14,
                trendPercent = 4.2f
            ),
            AppDataUsage(
                id = "whatsapp",
                name = "WhatsApp",
                category = "Messagerie & Appels",
                logoType = AppLogoType.WHATSAPP,
                percentDay = 20.0f,
                percentMonth = 18.0f,
                percentGlobal = 19.5f,
                dataMbDay = 450.0f,
                dataMbDayWifi = 280.0f,
                dataMbDayMobile = 170.0f,
                dataMbMonth = 6800.0f,
                dataMbMonthWifi = 4500.0f,
                dataMbMonthMobile = 2300.0f,
                dataMbTotal = 32000.0f,
                dataMbTotalWifi = 21000.0f,
                dataMbTotalMobile = 11000.0f,
                screenMinutesDay = 35, // 35m
                screenMinutesMonth = 1050,
                screenMinutesYear = 12600,
                dailyLaunches = 48,
                trendPercent = -1.5f
            ),
            AppDataUsage(
                id = "tiktok",
                name = "TikTok",
                category = "Réseaux Sociaux",
                logoType = AppLogoType.TIKTOK,
                percentDay = 15.0f,
                percentMonth = 22.0f,
                percentGlobal = 18.4f,
                dataMbDay = 1228.8f, // ~1.2 Go
                dataMbDayWifi = 819.2f,
                dataMbDayMobile = 409.6f,
                dataMbMonth = 31200.0f,
                dataMbMonthWifi = 21000.0f,
                dataMbMonthMobile = 10200.0f,
                dataMbTotal = 145000.0f,
                dataMbTotalWifi = 98000.0f,
                dataMbTotalMobile = 47000.0f,
                screenMinutesDay = 135, // 2h 15m
                screenMinutesMonth = 3800,
                screenMinutesYear = 45000,
                dailyLaunches = 28,
                trendPercent = 6.8f
            ),
            AppDataUsage(
                id = "instagram",
                name = "Instagram",
                category = "Réseaux Sociaux",
                logoType = AppLogoType.INSTAGRAM,
                percentDay = 10.0f,
                percentMonth = 11.5f,
                percentGlobal = 10.8f,
                dataMbDay = 750.0f,
                dataMbDayWifi = 500.0f,
                dataMbDayMobile = 250.0f,
                dataMbMonth = 16800.0f,
                dataMbMonthWifi = 11200.0f,
                dataMbMonthMobile = 5600.0f,
                dataMbTotal = 82000.0f,
                dataMbTotalWifi = 56000.0f,
                dataMbTotalMobile = 26000.0f,
                screenMinutesDay = 55, // 55m
                screenMinutesMonth = 1650,
                screenMinutesYear = 19800,
                dailyLaunches = 22,
                trendPercent = 2.1f
            ),
            AppDataUsage(
                id = "spotify",
                name = "Spotify",
                category = "Musique & Audio",
                logoType = AppLogoType.SPOTIFY,
                percentDay = 6.0f,
                percentMonth = 5.2f,
                percentGlobal = 5.6f,
                dataMbDay = 310.0f,
                dataMbDayWifi = 120.0f,
                dataMbDayMobile = 190.0f,
                dataMbMonth = 7200.0f,
                dataMbMonthWifi = 3000.0f,
                dataMbMonthMobile = 4200.0f,
                dataMbTotal = 38000.0f,
                dataMbTotalWifi = 16000.0f,
                dataMbTotalMobile = 22000.0f,
                screenMinutesDay = 25, // 25m
                screenMinutesMonth = 750,
                screenMinutesYear = 9000,
                dailyLaunches = 8,
                trendPercent = 0.5f
            ),
            AppDataUsage(
                id = "chrome",
                name = "Google Chrome",
                category = "Navigation Web",
                logoType = AppLogoType.CHROME,
                percentDay = 4.0f,
                percentMonth = 4.8f,
                percentGlobal = 4.5f,
                dataMbDay = 220.0f,
                dataMbDayWifi = 140.0f,
                dataMbDayMobile = 80.0f,
                dataMbMonth = 5400.0f,
                dataMbMonthWifi = 3600.0f,
                dataMbMonthMobile = 1800.0f,
                dataMbTotal = 28000.0f,
                dataMbTotalWifi = 19000.0f,
                dataMbTotalMobile = 9000.0f,
                screenMinutesDay = 40, // 40m
                screenMinutesMonth = 1200,
                screenMinutesYear = 14400,
                dailyLaunches = 18,
                trendPercent = -3.2f
            ),
            AppDataUsage(
                id = "netflix",
                name = "Netflix",
                category = "Streaming & Films",
                logoType = AppLogoType.NETFLIX,
                percentDay = 3.5f,
                percentMonth = 6.5f,
                percentGlobal = 5.0f,
                dataMbDay = 2560.0f, // 2.5 Go
                dataMbDayWifi = 2300.0f,
                dataMbDayMobile = 260.0f,
                dataMbMonth = 38000.0f,
                dataMbMonthWifi = 34000.0f,
                dataMbMonthMobile = 4000.0f,
                dataMbTotal = 160000.0f,
                dataMbTotalWifi = 145000.0f,
                dataMbTotalMobile = 15000.0f,
                screenMinutesDay = 45, // 45m
                screenMinutesMonth = 1950,
                screenMinutesYear = 22500,
                dailyLaunches = 3,
                trendPercent = 1.8f
            ),
            AppDataUsage(
                id = "telegram",
                name = "Telegram",
                category = "Messagerie & Communauté",
                logoType = AppLogoType.TELEGRAM,
                percentDay = 2.5f,
                percentMonth = 3.0f,
                percentGlobal = 2.8f,
                dataMbDay = 320.0f,
                dataMbDayWifi = 200.0f,
                dataMbDayMobile = 120.0f,
                dataMbMonth = 6200.0f,
                dataMbMonthWifi = 4100.0f,
                dataMbMonthMobile = 2100.0f,
                dataMbTotal = 29000.0f,
                dataMbTotalWifi = 20000.0f,
                dataMbTotalMobile = 9000.0f,
                screenMinutesDay = 20, // 20m
                screenMinutesMonth = 600,
                screenMinutesYear = 7200,
                dailyLaunches = 15,
                trendPercent = 0.8f
            ),
            AppDataUsage(
                id = "facebook",
                name = "Facebook",
                category = "Réseaux Sociaux",
                logoType = AppLogoType.FACEBOOK,
                percentDay = 2.0f,
                percentMonth = 2.5f,
                percentGlobal = 2.3f,
                dataMbDay = 280.0f,
                dataMbDayWifi = 180.0f,
                dataMbDayMobile = 100.0f,
                dataMbMonth = 5100.0f,
                dataMbMonthWifi = 3300.0f,
                dataMbMonthMobile = 1800.0f,
                dataMbTotal = 24000.0f,
                dataMbTotalWifi = 16000.0f,
                dataMbTotalMobile = 8000.0f,
                screenMinutesDay = 15, // 15m
                screenMinutesMonth = 450,
                screenMinutesYear = 5400,
                dailyLaunches = 10,
                trendPercent = -0.4f
            ),
            AppDataUsage(
                id = "twitter_x",
                name = "X (Twitter)",
                category = "Actualités & Microblogging",
                logoType = AppLogoType.TWITTER_X,
                percentDay = 1.8f,
                percentMonth = 2.0f,
                percentGlobal = 1.9f,
                dataMbDay = 210.0f,
                dataMbDayWifi = 130.0f,
                dataMbDayMobile = 80.0f,
                dataMbMonth = 4200.0f,
                dataMbMonthWifi = 2700.0f,
                dataMbMonthMobile = 1500.0f,
                dataMbTotal = 19000.0f,
                dataMbTotalWifi = 12500.0f,
                dataMbTotalMobile = 6500.0f,
                screenMinutesDay = 18,
                screenMinutesMonth = 540,
                screenMinutesYear = 6500,
                dailyLaunches = 12,
                trendPercent = 1.2f
            ),
            AppDataUsage(
                id = "reddit",
                name = "Reddit",
                category = "Communautés & Forums",
                logoType = AppLogoType.REDDIT,
                percentDay = 1.2f,
                percentMonth = 1.5f,
                percentGlobal = 1.4f,
                dataMbDay = 180.0f,
                dataMbDayWifi = 120.0f,
                dataMbDayMobile = 60.0f,
                dataMbMonth = 3800.0f,
                dataMbMonthWifi = 2500.0f,
                dataMbMonthMobile = 1300.0f,
                dataMbTotal = 17500.0f,
                dataMbTotalWifi = 11500.0f,
                dataMbTotalMobile = 6000.0f,
                screenMinutesDay = 12,
                screenMinutesMonth = 360,
                screenMinutesYear = 4300,
                dailyLaunches = 6,
                trendPercent = -0.9f
            ),
            AppDataUsage(
                id = "snapchat",
                name = "Snapchat",
                category = "Photos & Éphémère",
                logoType = AppLogoType.SNAPCHAT,
                percentDay = 1.0f,
                percentMonth = 1.2f,
                percentGlobal = 1.1f,
                dataMbDay = 150.0f,
                dataMbDayWifi = 90.0f,
                dataMbDayMobile = 60.0f,
                dataMbMonth = 3100.0f,
                dataMbMonthWifi = 1900.0f,
                dataMbMonthMobile = 1200.0f,
                dataMbTotal = 14000.0f,
                dataMbTotalWifi = 9000.0f,
                dataMbTotalMobile = 5000.0f,
                screenMinutesDay = 10,
                screenMinutesMonth = 300,
                screenMinutesYear = 3600,
                dailyLaunches = 9,
                trendPercent = 0.0f
            ),
            AppDataUsage(
                id = "maps",
                name = "Google Maps",
                category = "Navigation & GPS",
                logoType = AppLogoType.MAPS,
                percentDay = 0.6f,
                percentMonth = 0.8f,
                percentGlobal = 0.7f,
                dataMbDay = 85.0f,
                dataMbDayWifi = 20.0f,
                dataMbDayMobile = 65.0f,
                dataMbMonth = 1400.0f,
                dataMbMonthWifi = 400.0f,
                dataMbMonthMobile = 1000.0f,
                dataMbTotal = 6800.0f,
                dataMbTotalWifi = 2000.0f,
                dataMbTotalMobile = 4800.0f,
                screenMinutesDay = 8,
                screenMinutesMonth = 240,
                screenMinutesYear = 2900,
                dailyLaunches = 4,
                trendPercent = -1.1f
            ),
            AppDataUsage(
                id = "gmail",
                name = "Gmail",
                category = "Productivité & E-mails",
                logoType = AppLogoType.GMAIL,
                percentDay = 0.4f,
                percentMonth = 0.5f,
                percentGlobal = 0.5f,
                dataMbDay = 45.0f,
                dataMbDayWifi = 25.0f,
                dataMbDayMobile = 20.0f,
                dataMbMonth = 950.0f,
                dataMbMonthWifi = 550.0f,
                dataMbMonthMobile = 400.0f,
                dataMbTotal = 4500.0f,
                dataMbTotalWifi = 2600.0f,
                dataMbTotalMobile = 1900.0f,
                screenMinutesDay = 7,
                screenMinutesMonth = 210,
                screenMinutesYear = 2500,
                dailyLaunches = 11,
                trendPercent = 0.3f
            )
        )
    }
}
