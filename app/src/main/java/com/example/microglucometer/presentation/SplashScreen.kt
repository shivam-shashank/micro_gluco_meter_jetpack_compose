package com.example.microglucometer.presentation

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.example.microglucometer.R
import com.example.microglucometer.presentation.destinations.RegistrationScreenDestination
import com.example.microglucometer.ui.theme.Brown700
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay

@Destination(start = true)
@Composable
fun SplashScreen(navigator: DestinationsNavigator) {
    val scale = remember {
        Animatable(0f)
    }

    // Animation
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            // tween Animation
            animationSpec = tween(
                durationMillis = 1600,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        // delay time
        delay(3000L)
        navigator.navigate(RegistrationScreenDestination)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash_logo),
            contentDescription = "Splash Screen Logo",
            modifier = Modifier.scale(scale.value)
        )

        Text(
            text = "Micro Gluco Meter",
            fontSize = 32.sp,
            style = TextStyle(color = Brown700),
            fontFamily = FontFamily.Monospace,
            textDecoration = TextDecoration.Underline,
        )
    }
}