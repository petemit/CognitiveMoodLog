package com.mindbuilders.cognitivemoodlog.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = PrimaryBluePrimary,
    primaryVariant = PrimaryDarkColor,
    secondary = SecondaryDarkColor,
    background = DarkBackgroundColor,
    onPrimary = Color.White,
    surface = PrimaryDarkColor,
    onSecondary = Color.White
)

private val LightColorPalette = lightColors(
    primary = PrimaryBluePrimary,
    primaryVariant = PrimaryBluePrimary,
    secondary = SecondaryLightColor,
    background = LightBackgroundColor,
    onPrimary = Color.White,
    surface = PrimaryBluePrimary,
    onSurface = Color.Black


    /* Other default colors to override
background = Color.White,
surface = Color.White,
onPrimary = Color.White,
onSecondary = Color.Black,
onBackground = Color.Black,
onSurface = Color.Black,
*/
)

@Composable
fun CognitiveMoodLogTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}