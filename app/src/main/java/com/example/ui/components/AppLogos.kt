package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLogoType

@Composable
fun AppLogoIcon(
    logoType: AppLogoType,
    modifier: Modifier = Modifier,
    size: Dp = 44.dp
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(size * 0.25f)),
        contentAlignment = Alignment.Center
    ) {
        when (logoType) {
            AppLogoType.YOUTUBE -> YouTubeLogo(size)
            AppLogoType.WHATSAPP -> WhatsAppLogo(size)
            AppLogoType.TIKTOK -> TikTokLogo(size)
            AppLogoType.INSTAGRAM -> InstagramLogo(size)
            AppLogoType.CHROME -> ChromeLogo(size)
            AppLogoType.SPOTIFY -> SpotifyLogo(size)
            AppLogoType.NETFLIX -> NetflixLogo(size)
            AppLogoType.FACEBOOK -> FacebookLogo(size)
            AppLogoType.TWITTER_X -> TwitterXLogo(size)
            AppLogoType.TELEGRAM -> TelegramLogo(size)
            AppLogoType.REDDIT -> RedditLogo(size)
            AppLogoType.SNAPCHAT -> SnapchatLogo(size)
            AppLogoType.GMAIL -> GmailLogo(size)
            AppLogoType.MAPS -> GoogleMapsLogo(size)
        }
    }
}

@Composable
private fun YouTubeLogo(size: Dp) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        // Red rounded rectangle
        drawRoundRect(
            color = Color(0xFFFF0000),
            size = Size(w, h),
            cornerRadius = CornerRadius(w * 0.24f, w * 0.24f)
        )
        // White play triangle
        val trianglePath = Path().apply {
            moveTo(w * 0.40f, h * 0.30f)
            lineTo(w * 0.70f, h * 0.50f)
            lineTo(w * 0.40f, h * 0.70f)
            close()
        }
        drawPath(trianglePath, color = Color.White)
    }
}

@Composable
private fun WhatsAppLogo(size: Dp) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        // Green background circle
        drawCircle(
            color = Color(0xFF25D366),
            radius = w * 0.48f,
            center = Offset(w * 0.5f, h * 0.5f)
        )
        // White speech bubble ring
        drawCircle(
            color = Color.White,
            radius = w * 0.32f,
            center = Offset(w * 0.48f, h * 0.48f),
            style = Stroke(width = w * 0.08f)
        )
        // Little handset arc
        val phonePath = Path().apply {
            moveTo(w * 0.38f, h * 0.38f)
            cubicTo(w * 0.42f, h * 0.34f, w * 0.48f, h * 0.38f, w * 0.46f, h * 0.44f)
            lineTo(w * 0.44f, h * 0.48f)
            cubicTo(w * 0.46f, h * 0.54f, w * 0.50f, h * 0.58f, w * 0.56f, h * 0.60f)
            lineTo(w * 0.60f, h * 0.58f)
            cubicTo(w * 0.66f, h * 0.56f, w * 0.70f, h * 0.62f, w * 0.66f, h * 0.66f)
            cubicTo(w * 0.58f, h * 0.74f, w * 0.32f, h * 0.54f, w * 0.38f, h * 0.38f)
        }
        drawPath(phonePath, color = Color.White)
    }
}

@Composable
private fun TikTokLogo(size: Dp) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        // Black background
        drawRoundRect(
            color = Color(0xFF010101),
            size = Size(w, h),
            cornerRadius = CornerRadius(w * 0.22f, w * 0.22f)
        )

        val notePath = Path().apply {
            moveTo(w * 0.46f, h * 0.70f)
            cubicTo(w * 0.34f, h * 0.70f, w * 0.30f, h * 0.60f, w * 0.36f, h * 0.52f)
            cubicTo(w * 0.42f, h * 0.44f, w * 0.52f, h * 0.48f, w * 0.52f, h * 0.58f)
            lineTo(w * 0.52f, h * 0.25f)
            cubicTo(w * 0.58f, h * 0.34f, w * 0.68f, h * 0.38f, w * 0.76f, h * 0.38f)
            lineTo(w * 0.76f, h * 0.48f)
            cubicTo(w * 0.66f, h * 0.48f, w * 0.58f, h * 0.42f, w * 0.56f, h * 0.36f)
            lineTo(w * 0.56f, h * 0.58f)
            cubicTo(w * 0.56f, h * 0.70f, w * 0.48f, h * 0.70f, w * 0.46f, h * 0.70f)
        }

        // Cyan offset
        drawPath(notePath, color = Color(0xFF00F2FE))
        // Magenta offset
        val magentaPath = Path().apply {
            moveTo(w * 0.44f, h * 0.68f)
            cubicTo(w * 0.32f, h * 0.68f, w * 0.28f, h * 0.58f, w * 0.34f, h * 0.50f)
            cubicTo(w * 0.40f, h * 0.42f, w * 0.50f, h * 0.46f, w * 0.50f, h * 0.56f)
            lineTo(w * 0.50f, h * 0.23f)
            cubicTo(w * 0.56f, h * 0.32f, w * 0.66f, h * 0.36f, w * 0.74f, h * 0.36f)
            lineTo(w * 0.74f, h * 0.46f)
            cubicTo(w * 0.64f, h * 0.46f, w * 0.56f, h * 0.40f, w * 0.54f, h * 0.34f)
            lineTo(w * 0.54f, h * 0.56f)
            cubicTo(w * 0.54f, h * 0.68f, w * 0.46f, h * 0.68f, w * 0.44f, h * 0.68f)
        }
        drawPath(magentaPath, color = Color(0xFFFE2C55))
        // White core
        drawPath(notePath, color = Color.White)
    }
}

@Composable
private fun InstagramLogo(size: Dp) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        val igBrush = Brush.linearGradient(
            colors = listOf(
                Color(0xFF833AB4),
                Color(0xFFFD1D1D),
                Color(0xFFFCB045)
            ),
            start = Offset(0f, 0f),
            end = Offset(w, h)
        )

        drawRoundRect(
            brush = igBrush,
            size = Size(w, h),
            cornerRadius = CornerRadius(w * 0.26f, w * 0.26f)
        )

        // Camera square
        drawRoundRect(
            color = Color.White,
            topLeft = Offset(w * 0.22f, h * 0.22f),
            size = Size(w * 0.56f, h * 0.56f),
            cornerRadius = CornerRadius(w * 0.16f, w * 0.16f),
            style = Stroke(width = w * 0.07f)
        )

        // Center lens
        drawCircle(
            color = Color.White,
            radius = w * 0.15f,
            center = Offset(w * 0.5f, h * 0.5f),
            style = Stroke(width = w * 0.07f)
        )

        // Flash dot
        drawCircle(
            color = Color.White,
            radius = w * 0.04f,
            center = Offset(w * 0.66f, h * 0.34f)
        )
    }
}

@Composable
private fun ChromeLogo(size: Dp) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val center = Offset(w * 0.5f, h * 0.5f)
        val radius = w * 0.46f

        // 3 Segment arcs for Chrome
        drawArc(
            color = Color(0xFFEA4335), // Red
            startAngle = -120f,
            sweepAngle = 120f,
            useCenter = true,
            size = Size(radius * 2, radius * 2),
            topLeft = Offset(center.x - radius, center.y - radius)
        )
        drawArc(
            color = Color(0xFF34A853), // Green
            startAngle = 0f,
            sweepAngle = 120f,
            useCenter = true,
            size = Size(radius * 2, radius * 2),
            topLeft = Offset(center.x - radius, center.y - radius)
        )
        drawArc(
            color = Color(0xFFFBBC05), // Yellow
            startAngle = 120f,
            sweepAngle = 120f,
            useCenter = true,
            size = Size(radius * 2, radius * 2),
            topLeft = Offset(center.x - radius, center.y - radius)
        )

        // White separator ring
        drawCircle(
            color = Color.White,
            radius = radius * 0.44f,
            center = center
        )

        // Blue center
        drawCircle(
            color = Color(0xFF4285F4),
            radius = radius * 0.36f,
            center = center
        )
    }
}

@Composable
private fun SpotifyLogo(size: Dp) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val center = Offset(w * 0.5f, h * 0.5f)

        // Spotify Green Circle
        drawCircle(
            color = Color(0xFF1DB954),
            radius = w * 0.48f,
            center = center
        )

        val stroke1 = w * 0.08f
        val stroke2 = w * 0.07f
        val stroke3 = w * 0.06f

        // Sound waves (Arcs)
        drawArc(
            color = Color.Black,
            startAngle = 195f,
            sweepAngle = 70f,
            useCenter = false,
            topLeft = Offset(w * 0.16f, h * 0.22f),
            size = Size(w * 0.68f, h * 0.54f),
            style = Stroke(width = stroke1, cap = StrokeCap.Round)
        )
        drawArc(
            color = Color.Black,
            startAngle = 197f,
            sweepAngle = 66f,
            useCenter = false,
            topLeft = Offset(w * 0.22f, h * 0.35f),
            size = Size(w * 0.56f, h * 0.44f),
            style = Stroke(width = stroke2, cap = StrokeCap.Round)
        )
        drawArc(
            color = Color.Black,
            startAngle = 200f,
            sweepAngle = 60f,
            useCenter = false,
            topLeft = Offset(w * 0.28f, h * 0.48f),
            size = Size(w * 0.44f, h * 0.34f),
            style = Stroke(width = stroke3, cap = StrokeCap.Round)
        )
    }
}

@Composable
private fun NetflixLogo(size: Dp) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        // Dark background
        drawRoundRect(
            color = Color(0xFF141414),
            size = Size(w, h),
            cornerRadius = CornerRadius(w * 0.2f, w * 0.2f)
        )

        // Left Red vertical bar
        drawRect(
            color = Color(0xFFB81D24),
            topLeft = Offset(w * 0.28f, h * 0.20f),
            size = Size(w * 0.14f, h * 0.60f)
        )

        // Right Red vertical bar
        drawRect(
            color = Color(0xFFB81D24),
            topLeft = Offset(w * 0.58f, h * 0.20f),
            size = Size(w * 0.14f, h * 0.60f)
        )

        // Center diagonal ribbon
        val diagPath = Path().apply {
            moveTo(w * 0.28f, h * 0.20f)
            lineTo(w * 0.42f, h * 0.20f)
            lineTo(w * 0.72f, h * 0.80f)
            lineTo(w * 0.58f, h * 0.80f)
            close()
        }
        drawPath(diagPath, color = Color(0xFFE50914))
    }
}

@Composable
private fun FacebookLogo(size: Dp) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(Color(0xFF1877F2)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "f",
            color = Color.White,
            fontSize = (size.value * 0.75f).sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun TwitterXLogo(size: Dp) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(size * 0.22f))
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "𝕏",
            color = Color.White,
            fontSize = (size.value * 0.55f).sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun TelegramLogo(size: Dp) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        // Sky Blue circle
        drawCircle(
            color = Color(0xFF2AABEE),
            radius = w * 0.48f,
            center = Offset(w * 0.5f, h * 0.5f)
        )

        // Origami Paper Airplane
        val planePath = Path().apply {
            moveTo(w * 0.25f, h * 0.50f)
            lineTo(w * 0.75f, h * 0.28f)
            lineTo(w * 0.62f, h * 0.72f)
            lineTo(w * 0.48f, h * 0.60f)
            lineTo(w * 0.42f, h * 0.68f)
            lineTo(w * 0.40f, h * 0.55f)
            close()
        }
        drawPath(planePath, color = Color.White)
    }
}

@Composable
private fun RedditLogo(size: Dp) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        // Reddit Orange
        drawCircle(
            color = Color(0xFFFF4500),
            radius = w * 0.48f,
            center = Offset(w * 0.5f, h * 0.5f)
        )

        // Snoo face oval
        drawOval(
            color = Color.White,
            topLeft = Offset(w * 0.25f, h * 0.35f),
            size = Size(w * 0.50f, h * 0.36f)
        )

        // Orange Eyes
        drawCircle(
            color = Color(0xFFFF4500),
            radius = w * 0.05f,
            center = Offset(w * 0.40f, h * 0.50f)
        )
        drawCircle(
            color = Color(0xFFFF4500),
            radius = w * 0.05f,
            center = Offset(w * 0.60f, h * 0.50f)
        )

        // Smile
        drawArc(
            color = Color(0xFFFF4500),
            startAngle = 10f,
            sweepAngle = 160f,
            useCenter = false,
            topLeft = Offset(w * 0.40f, h * 0.53f),
            size = Size(w * 0.20f, h * 0.12f),
            style = Stroke(width = w * 0.03f, cap = StrokeCap.Round)
        )
    }
}

@Composable
private fun SnapchatLogo(size: Dp) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        // Yellow background
        drawRoundRect(
            color = Color(0xFFFFFC00),
            size = Size(w, h),
            cornerRadius = CornerRadius(w * 0.22f, w * 0.22f)
        )

        // Ghost outline
        val ghostPath = Path().apply {
            moveTo(w * 0.5f, h * 0.26f)
            cubicTo(w * 0.38f, h * 0.26f, w * 0.34f, h * 0.38f, w * 0.34f, h * 0.48f)
            cubicTo(w * 0.26f, h * 0.52f, w * 0.28f, h * 0.60f, w * 0.36f, h * 0.60f)
            cubicTo(w * 0.34f, h * 0.66f, w * 0.30f, h * 0.72f, w * 0.40f, h * 0.72f)
            cubicTo(w * 0.44f, h * 0.72f, w * 0.48f, h * 0.68f, w * 0.50f, h * 0.68f)
            cubicTo(w * 0.52f, h * 0.68f, w * 0.56f, h * 0.72f, w * 0.60f, h * 0.72f)
            cubicTo(w * 0.70f, h * 0.72f, w * 0.66f, h * 0.66f, w * 0.64f, h * 0.60f)
            cubicTo(w * 0.72f, h * 0.60f, w * 0.74f, h * 0.52f, w * 0.66f, h * 0.48f)
            cubicTo(w * 0.66f, h * 0.38f, w * 0.62f, h * 0.26f, w * 0.50f, h * 0.26f)
        }
        drawPath(ghostPath, color = Color.White)
        drawPath(ghostPath, color = Color.Black, style = Stroke(width = w * 0.04f))
    }
}

@Composable
private fun GmailLogo(size: Dp) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        drawRoundRect(
            color = Color.White,
            size = Size(w, h),
            cornerRadius = CornerRadius(w * 0.22f, w * 0.22f)
        )

        // Google envelope M
        val leftRed = Path().apply {
            moveTo(w * 0.20f, h * 0.28f)
            lineTo(w * 0.32f, h * 0.28f)
            lineTo(w * 0.32f, h * 0.72f)
            lineTo(w * 0.20f, h * 0.72f)
            close()
        }
        drawPath(leftRed, color = Color(0xFFEA4335))

        val rightRed = Path().apply {
            moveTo(w * 0.68f, h * 0.28f)
            lineTo(w * 0.80f, h * 0.28f)
            lineTo(w * 0.80f, h * 0.72f)
            lineTo(w * 0.68f, h * 0.72f)
            close()
        }
        drawPath(rightRed, color = Color(0xFF4285F4))

        val foldRed = Path().apply {
            moveTo(w * 0.20f, h * 0.28f)
            lineTo(w * 0.50f, h * 0.52f)
            lineTo(w * 0.80f, h * 0.28f)
            lineTo(w * 0.68f, h * 0.28f)
            lineTo(w * 0.50f, h * 0.42f)
            lineTo(w * 0.32f, h * 0.28f)
            close()
        }
        drawPath(foldRed, color = Color(0xFFEA4335))
    }
}

@Composable
private fun GoogleMapsLogo(size: Dp) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        drawRoundRect(
            color = Color.White,
            size = Size(w, h),
            cornerRadius = CornerRadius(w * 0.22f, w * 0.22f)
        )

        // Google pin shape
        val pinPath = Path().apply {
            moveTo(w * 0.50f, h * 0.76f)
            cubicTo(w * 0.38f, h * 0.56f, w * 0.28f, h * 0.46f, w * 0.28f, h * 0.36f)
            cubicTo(w * 0.28f, h * 0.24f, w * 0.38f, h * 0.16f, w * 0.50f, h * 0.16f)
            cubicTo(w * 0.62f, h * 0.16f, w * 0.72f, h * 0.24f, w * 0.72f, h * 0.36f)
            cubicTo(w * 0.72f, h * 0.46f, w * 0.62f, h * 0.56f, w * 0.50f, h * 0.76f)
            close()
        }
        drawPath(pinPath, color = Color(0xFF34A853))

        // Top arc in Red
        drawArc(
            color = Color(0xFFEA4335),
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = true,
            topLeft = Offset(w * 0.28f, h * 0.16f),
            size = Size(w * 0.44f, h * 0.40f)
        )

        // Center dot
        drawCircle(
            color = Color(0xFF185ABC),
            radius = w * 0.08f,
            center = Offset(w * 0.50f, h * 0.36f)
        )
    }
}
