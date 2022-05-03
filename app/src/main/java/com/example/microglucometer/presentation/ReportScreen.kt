package com.example.microglucometer.presentation

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.microglucometer.database.UserDetail
import com.example.microglucometer.methods.ImageConversion
import com.example.microglucometer.models.UploadImage
import com.example.microglucometer.models.User
import com.example.microglucometer.presentation.destinations.RegistrationScreenDestination
import com.example.microglucometer.presentation.destinations.ReportScreenDestination
import com.example.microglucometer.view_model.UserDetailViewModel
import com.example.microglucometer.widgets.CustomOutlinedTextField
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.slaviboy.composeunits.dh

@Destination
@Composable
fun ReportScreen(
    navigator: DestinationsNavigator,
    user: User,
    uploadImage: UploadImage,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Report") },
                actions = {
                    IconButton(
                        onClick = {
                            navigator.navigate(RegistrationScreenDestination) {
                                popUpTo(ReportScreenDestination.route) { inclusive = true }
                            }
                        },
                    ) {
                        Icon(Icons.Filled.Add, "Records")
                    }
                }
            )
        }
    ) {
        ReportScreenBody(user, uploadImage)
    }
}

@Composable
fun ReportScreenBody(
    user: User,
    uploadImage: UploadImage,
) {
    val context = LocalContext.current

    LaunchedEffect(user.name) {
        val mUserDetailViewModel = UserDetailViewModel(context.applicationContext as Application)

        mUserDetailViewModel.addUserDetail(
            UserDetail(
                System.currentTimeMillis(),
                user.name,
                user.gender,
                user.age,
                user.phoneNumber,
                user.concentration,
            ),
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 12.dp),
        horizontalAlignment = CenterHorizontally,
    ) {

        Column(
            horizontalAlignment = CenterHorizontally,
        ) {

            CustomOutlinedTextField(
                value = user.name,
                onValueChange = { },
                label = "Name",
                readOnly = true,
                leadingIconImageVector = Icons.Filled.Person,
                leadingIconContentDescription = "Name Icon",
                showError = false,
                errorMessage = "",
                keyboardOptions = KeyboardOptions(),
                keyboardActions = KeyboardActions()
            )

            CustomOutlinedTextField(
                value = user.gender,
                onValueChange = { },
                label = "Gender",
                readOnly = true,
                leadingIconImageVector = if (user.gender == "Male") Icons.Filled.Male else Icons.Filled.Female,
                leadingIconContentDescription = "Gender Icon",
                showError = false,
                errorMessage = "",
                keyboardOptions = KeyboardOptions(),
                keyboardActions = KeyboardActions(),
            )

            CustomOutlinedTextField(
                value = user.age,
                onValueChange = { },
                label = "Age",
                readOnly = true,
                leadingIconImageVector = Icons.Filled.ConfirmationNumber,
                leadingIconContentDescription = "Age Icon",
                showError = false,
                errorMessage = "",
                keyboardOptions = KeyboardOptions(),
                keyboardActions = KeyboardActions()
            )

            CustomOutlinedTextField(
                value = user.phoneNumber,
                onValueChange = { },
                label = "Phone Number",
                readOnly = true,
                leadingIconImageVector = Icons.Filled.Phone,
                leadingIconContentDescription = "Phone Number Icon",
                showError = false,
                errorMessage = "",
                keyboardOptions = KeyboardOptions(),
                keyboardActions = KeyboardActions()
            )

            CustomOutlinedTextField(
                value = user.concentration,
                onValueChange = { },
                label = "Concentration",
                readOnly = true,
                leadingIconImageVector = Icons.Filled.Water,
                leadingIconContentDescription = "Concentration Icon",
                showError = false,
                errorMessage = "",
                keyboardOptions = KeyboardOptions(),
                keyboardActions = KeyboardActions()
            )

            uploadImage.regionOfInterestImageByteArray.let {

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
                modifier = Modifier.padding(vertical = 8.dp),
                text = "Region Of Interest",
                fontSize = 24.sp,
                textDecoration = TextDecoration.Underline,
            )

        }

    }
}