package com.example.todolistapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.example.todolistapp.R


@OptIn(ExperimentalTextApi::class)
val provider = GoogleFont.Provider(
    providerPackage = "com.google.android.gms",
    providerAuthority = "com.google.android.gms.fonts",
    certificates = R.array.com_google_android_gms_fonts_certs
)

@OptIn(ExperimentalTextApi::class)
val monserratFontName = GoogleFont("Montserrat")

@OptIn(ExperimentalTextApi::class)
val monserrat = FontFamily(
    Font(googleFont = monserratFontName, fontProvider = provider)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = monserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodySmall = TextStyle(
        fontFamily = monserrat
    ),
    bodyMedium = TextStyle(
        fontFamily = monserrat
    ),
    headlineLarge = TextStyle(
        fontFamily = monserrat
    ),
    headlineMedium = TextStyle(
        fontFamily = monserrat
    ),
    headlineSmall = TextStyle(
        fontFamily = monserrat
    ),
    displayLarge = TextStyle(
        fontFamily = monserrat
    ),
    displayMedium = TextStyle(
        fontFamily = monserrat
    ),
    displaySmall = TextStyle(
        fontFamily = monserrat
    ),
    titleLarge = TextStyle(
        fontFamily = monserrat
    ),
    titleMedium = TextStyle(
        fontFamily = monserrat
    ),
    titleSmall = TextStyle(
        fontFamily = monserrat
    ),
    labelLarge = TextStyle(
        fontFamily = monserrat
    ),
    labelMedium = TextStyle(
        fontFamily = monserrat
    ),
    labelSmall = TextStyle(
        fontFamily = monserrat
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)