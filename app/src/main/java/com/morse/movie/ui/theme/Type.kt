package com.morse.movie.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/*
 h1, h2, h3, h4, h5, h6, subtitle1, subtitle2, body1, body2, caption, button, overline
 */

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = MoonFont,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = MoonFont,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = CairoFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = CairoFont,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ) ,
    button = TextStyle(
        fontFamily = CairoFont,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    )
)