package com.k3437.lux.ui.theme

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import kotlin.collections.contains
import kotlin.collections.remove

private val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = Black, // Dark 모드 배경색
    surface = DarkGrey,   // Dark 모드 Surface 색상
    onPrimary = Black,
    onSecondary = Black,
    onTertiary = Black,
    onBackground = White, // Dark 모드 텍스트 색상
    onSurface = White,    // Dark 모드 텍스트
    onError = Cyan,
)

private val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = White, // Light 모드 배경색
    surface = White,     // Light 모드 Surface 색상
    onPrimary = White,
    onSecondary = White,
    onTertiary = White,
    onBackground = Black, // Light 모드 텍스트 색상
    onSurface = Black,    // Light 모드 텍스트 색상
    onError = Cyan,
)

object ThemePreferences {
    private const val PREFS_NAME = "theme_prefs"
    private const val KEY_DARK_MODE = "dark_mode_forced" // null을 표현하기 위해 Boolean? 대신 Int 사용 가능

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // null: 사용자 선택 없음, true: 다크 모드 강제, false: 라이트 모드 강제
    fun getUserForcedDarkMode(context: Context): Boolean? {
        val prefs = getPrefs(context)
        if (!prefs.contains(KEY_DARK_MODE)) {
            return null
        }
        return prefs.getBoolean(KEY_DARK_MODE, false) // 기본값은 의미 없음 (contains로 확인했으므로)
    }

    fun setUserForcedDarkMode(context: Context, isForced: Boolean?) {
        val editor = getPrefs(context).edit()
        if (isForced == null) {
            editor.remove(KEY_DARK_MODE)
        } else {
            editor.putBoolean(KEY_DARK_MODE, isForced)
        }
        editor.apply()
    }
}

@Composable
fun LuxTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> {
            DarkColorScheme
        }

        else -> {
            LightColorScheme
        }
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}