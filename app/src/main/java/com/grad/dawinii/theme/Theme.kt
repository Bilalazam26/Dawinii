package com.grad.dawinii.theme

import android.app.Activity
import android.os.Build
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext

val lightColorScheme = lightColorScheme(

)

val darkColorScheme  = darkColorScheme(
    primary = PrimaryColor,
    primaryContainer = Color.White,
    background = background,
    secondary = SecondaryColor,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = PrimaryColor,
    surface = background,
    onSurface = PrimaryColor,
)

@Composable
fun DawiniiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColors: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColors && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }
    /*
    val activity = LocalActivity.current as Activity
    SideEffect {
        val window = activity.window
        window.statusBarColor = colorScheme.background.toArgb()
        window.navigationBarColor = colorScheme.background.toArgb()
    }*/

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}