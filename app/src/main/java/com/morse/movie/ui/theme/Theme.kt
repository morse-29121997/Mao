package com.morse.movie.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


/*
✔ Colors without On
1 - A primary color is the color displayed most frequently across your app's screens and components likes ( Top app bar )
2 - A secondary color provides more ways to accent and distinguish your product likes
        Floating action buttons
        Selection controls, like sliders and switches
        Highlighting selected text
        Progress bars
        Links and headlines
3 - Surface colors affect surfaces of components, such as cards, sheets, and menus.
4 - The background color appears behind scrollable content.
        The baseline background and surface color is #FFFFFF.
5 - Error color indicates errors in components, such as invalid text in a text field.
        The baseline error color is #B00020.

✔ Colors with On
This is for icons , text colors so onPrimary with effect on primary component colors

*/

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200,
)

@Composable
fun MovieTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
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