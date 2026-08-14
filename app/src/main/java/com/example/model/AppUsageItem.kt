package com.example.model

enum class AppLogoType {
    YOUTUBE,
    WHATSAPP,
    TIKTOK,
    INSTAGRAM,
    CHROME,
    SPOTIFY,
    NETFLIX,
    FACEBOOK,
    TWITTER_X,
    TELEGRAM,
    REDDIT,
    SNAPCHAT,
    GMAIL,
    MAPS
}

enum class DrawerSection(val title: String, val iconEmoji: String) {
    PERCENTAGE("Consommation en %", "📊"),
    DATA_MB("Consommation en Mo/Go", "📶"),
    SCREEN_TIME("Temps d'écran", "⏱️"),
    INFOS_TIPS("Infos & Tips", "ℹ️")
}

enum class PercentFilter(val label: String) {
    JOUR("Jour"),
    MOIS("Mois"),
    GLOBAL("Global")
}

enum class DataUsageFilter(val label: String) {
    AUJOURDHUI("Aujourd'hui"),
    CE_MOIS("Ce mois"),
    TOTAL("Total")
}

enum class NetworkTypeFilter(val label: String) {
    ALL("Tout"),
    WIFI("Wi-Fi"),
    MOBILE("Données mobiles")
}

enum class ScreenTimeFilter(val label: String) {
    JOUR("Jour"),
    MOIS("Mois"),
    ANNEE("Année")
}

data class AppDataUsage(
    val id: String,
    val name: String,
    val category: String,
    val logoType: AppLogoType,
    // Percentages
    val percentDay: Float,
    val percentMonth: Float,
    val percentGlobal: Float,
    // Data in MB
    val dataMbDay: Float,
    val dataMbDayWifi: Float,
    val dataMbDayMobile: Float,
    val dataMbMonth: Float,
    val dataMbMonthWifi: Float,
    val dataMbMonthMobile: Float,
    val dataMbTotal: Float,
    val dataMbTotalWifi: Float,
    val dataMbTotalMobile: Float,
    // Screen Time in Minutes
    val screenMinutesDay: Int,
    val screenMinutesMonth: Int,
    val screenMinutesYear: Int,
    val dailyLaunches: Int,
    val trendPercent: Float // e.g. +2.4%
) {
    fun getPercentage(filter: PercentFilter): Float = when (filter) {
        PercentFilter.JOUR -> percentDay
        PercentFilter.MOIS -> percentMonth
        PercentFilter.GLOBAL -> percentGlobal
    }

    fun getDataMb(filter: DataUsageFilter, netFilter: NetworkTypeFilter): Float {
        return when (filter) {
            DataUsageFilter.AUJOURDHUI -> when (netFilter) {
                NetworkTypeFilter.ALL -> dataMbDay
                NetworkTypeFilter.WIFI -> dataMbDayWifi
                NetworkTypeFilter.MOBILE -> dataMbDayMobile
            }
            DataUsageFilter.CE_MOIS -> when (netFilter) {
                NetworkTypeFilter.ALL -> dataMbMonth
                NetworkTypeFilter.WIFI -> dataMbMonthWifi
                NetworkTypeFilter.MOBILE -> dataMbMonthMobile
            }
            DataUsageFilter.TOTAL -> when (netFilter) {
                NetworkTypeFilter.ALL -> dataMbTotal
                NetworkTypeFilter.WIFI -> dataMbTotalWifi
                NetworkTypeFilter.MOBILE -> dataMbTotalMobile
            }
        }
    }

    fun getScreenTimeMinutes(filter: ScreenTimeFilter): Int = when (filter) {
        ScreenTimeFilter.JOUR -> screenMinutesDay
        ScreenTimeFilter.MOIS -> screenMinutesMonth
        ScreenTimeFilter.ANNEE -> screenMinutesYear
    }

    fun formatScreenTime(filter: ScreenTimeFilter): String {
        val totalMinutes = getScreenTimeMinutes(filter)
        val hours = totalMinutes / 60
        val mins = totalMinutes % 60
        return when {
            hours > 0 && mins > 0 -> "${hours}h ${mins}m"
            hours > 0 -> "${hours}h"
            else -> "${mins}m"
        }
    }

    fun formatDataUsage(filter: DataUsageFilter, netFilter: NetworkTypeFilter): String {
        val mb = getDataMb(filter, netFilter)
        return if (mb >= 1000f) {
            String.format(java.util.Locale.US, "%.1f Go", mb / 1024f)
        } else {
            String.format(java.util.Locale.US, "%.0f Mo", mb)
        }
    }
}
