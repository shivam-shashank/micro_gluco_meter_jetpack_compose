package com.example.microglucometer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.microglucometer.presentation.NavGraphs
import com.example.microglucometer.ui.theme.MicroGlucoMeterTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.slaviboy.composeunits.initSize

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // init device width and height
        initSize()

        setContent {
            MicroGlucoMeterTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
    }
}