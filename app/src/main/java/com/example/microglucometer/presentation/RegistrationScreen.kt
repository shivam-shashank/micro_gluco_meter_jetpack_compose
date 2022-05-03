package com.example.microglucometer.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.microglucometer.models.User
import com.example.microglucometer.presentation.destinations.RecordsScreenDestination
import com.example.microglucometer.presentation.destinations.UploadImageScreenDestination
import com.example.microglucometer.ui.theme.Brown200
import com.example.microglucometer.ui.theme.Brown700
import com.example.microglucometer.widgets.CustomOutlinedTextField
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination()
@Composable
fun RegistrationScreen(navigator: DestinationsNavigator) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Registration") },
                actions = {
                    IconButton(
                        onClick = {
                            navigator.navigate(RecordsScreenDestination)
                        },
                    ) {
                        Icon(Icons.Filled.Folder,"Records")
                    }
                }
            )
        }
    ) {
        RegistrationForm(navigator)
    }
}

@Composable
fun RegistrationForm(navigator: DestinationsNavigator) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var name by rememberSaveable { mutableStateOf("") }
    var gender by rememberSaveable { mutableStateOf("") }
    var age by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }

    var validateName by rememberSaveable { mutableStateOf(true) }
    var validateAge by rememberSaveable { mutableStateOf(true) }
    var validatePhoneNumber by rememberSaveable { mutableStateOf(true) }

    val validateNameError = "Please, input a valid name"
    val validateGenderError = "Please select gender"
    val validateAgeError = "Age must be between 0 - 100"
    val validatePhoneNumberError = "Phone Number must be of 10 digits"

    fun validateData(name: String, age: String, phoneNumber: String): Boolean {
        validateName = name.isNotBlank()

        validateAge = if (age.isBlank()) false
        else age.toInt() in 1..99

        validatePhoneNumber = phoneNumber.length == 10

        return validateName && validateAge && validatePhoneNumber
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 12.dp),
        horizontalAlignment = CenterHorizontally,
    ) {

        Column(
            horizontalAlignment = CenterHorizontally,
        ) {

            CustomOutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = "Name",
                leadingIconImageVector = Icons.Filled.Person,
                leadingIconContentDescription = "Name Icon",
                showError = !validateName,
                errorMessage = validateNameError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(vertical = 16.dp),
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = if (gender == "Male") Brown700 else Brown200,
                            shape = RoundedCornerShape(16.dp),
                        )
                        .padding(vertical = 20.dp)
                        .clickable { gender = "Male" },
                    contentAlignment = Center,
                ) {
                    Column(
                        horizontalAlignment = CenterHorizontally,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Male,
                            contentDescription = "Male Icon",
                            tint = Color.White,
                            modifier = Modifier.size(54.dp),
                        )
                        Text(
                            text = "Male",
                            fontSize = 22.sp,
                            color = Color.White,
                        )
                    }
                }

                Spacer(modifier = Modifier.width(24.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = if (gender == "Female") Brown700 else Brown200,
                            shape = RoundedCornerShape(16.dp),
                        )
                        .padding(vertical = 20.dp)
                        .clickable { gender = "Female" },
                    contentAlignment = Center,
                ) {
                    Column(
                        horizontalAlignment = CenterHorizontally,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Female,
                            contentDescription = "Female Icon",
                            tint = Color.White,
                            modifier = Modifier.size(54.dp),
                        )
                        Text(
                            text = "Female",
                            fontSize = 22.sp,
                            color = Color.White,
                        )
                    }
                }
            }

            CustomOutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = "Age",
                leadingIconImageVector = Icons.Filled.ConfirmationNumber,
                leadingIconContentDescription = "Age Icon",
                showError = !validateAge,
                errorMessage = validateAgeError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )

            CustomOutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = "Phone Number",
                leadingIconImageVector = Icons.Filled.Phone,
                leadingIconContentDescription = "Phone Number Icon",
                showError = !validatePhoneNumber,
                errorMessage = validatePhoneNumberError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                )
            )

        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                      if (validateData(name, age, phoneNumber)) {
                          if (gender == ""){
                              Toast.makeText(context, validateGenderError, Toast.LENGTH_SHORT).show()
                          } else {
                              navigator.navigate(
                                  UploadImageScreenDestination(
                                      user = User(
                                          name,
                                          age,
                                          gender,
                                          phoneNumber,
                                          "",
                                      ),
                                  ),
                              )

                              name = ""
                              gender = ""
                              age = ""
                              phoneNumber = ""
                          }
                      }
            },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(48.dp),
        ) {
            Text(
                text = "Register",
                fontSize = 18.sp,
            )
        }

    }

}
