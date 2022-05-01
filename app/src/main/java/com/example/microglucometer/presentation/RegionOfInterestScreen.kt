package com.example.microglucometer.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.microglucometer.methods.ImageConversion
import com.example.microglucometer.models.UploadImage
import com.example.microglucometer.models.User
import com.example.microglucometer.presentation.destinations.RegistrationScreenDestination
import com.example.microglucometer.presentation.destinations.ReportScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.slaviboy.composeunits.dh

@Destination
@Composable
fun RegionOfInterestScreen(
    navigator: DestinationsNavigator,
    user: User,
    uploadImage: UploadImage,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Region Of Interest") },
                navigationIcon = {
                    IconButton(
                        onClick = { navigator.navigateUp() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) {
        RegionOfInterestBody(
            navigator,
            user,
            uploadImage,
        )
    }
}

@Composable
fun RegionOfInterestBody(
    navigator: DestinationsNavigator,
    user: User,
    uploadImage: UploadImage,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            uploadImage.byteArray.let {

                val bitmap = ImageConversion().convertByteArraysToBitmap(it)

                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Image",
                    alignment = Alignment.TopCenter,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(0.3.dh),
                    contentScale = ContentScale.FillBounds,
                )
            }

            Text(
                modifier = Modifier.padding(vertical = 12.dp),
                text = "Original Image",
                fontSize = 24.sp,
                textDecoration = TextDecoration.Underline,
            )

            uploadImage.byteArray.let {

                val bitmap = ImageConversion().convertByteArraysToBitmap(it)

                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Image",
                    alignment = Alignment.TopCenter,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(0.3.dh),
                    contentScale = ContentScale.FillBounds,
                )
            }

            Text(
                modifier = Modifier.padding(vertical = 12.dp),
                text = "Region Of Interest",
                fontSize = 24.sp,
                textDecoration = TextDecoration.Underline,
            )

        }

        Spacer(modifier = Modifier.weight(1f))
        
        Button(
            onClick = {
                navigator.navigate(
                    ReportScreenDestination(
                        user,
                        uploadImage,
                    ),
                ){
                    popUpTo(RegistrationScreenDestination.route) { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(48.dp),
        ) {
            Text(
                text = "Confirm",
                fontSize = 18.sp,
            )
        }
    }
}