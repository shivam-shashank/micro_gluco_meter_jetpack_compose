package com.example.microglucometer.presentation

import android.app.Application
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.microglucometer.ui.theme.Brown700
import com.example.microglucometer.view_model.UserDetailViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun RecordsScreen(navigator: DestinationsNavigator) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Records") },
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
        RecordsScreenBody()
    }
}

@Composable
fun RecordsScreenBody() {
    val context = LocalContext.current

    val mUserDetailViewModel = UserDetailViewModel(context.applicationContext as Application)

    val userDetailList = mUserDetailViewModel.readAllData.observeAsState(listOf()).value

    if (userDetailList.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(
                text = "No Record Found",
                textAlign = TextAlign.Center,
                modifier = Modifier.align(alignment = Alignment.Center)
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.padding(16.dp),
        ) {
            items(userDetailList) { userDetail ->
                Card(
                    elevation = 6.dp,
                    border = BorderStroke(1.dp, Brown700),
                    modifier = Modifier.padding(vertical = 8.dp).fillMaxSize(),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                    ) {
                        DetailRow(label = "Name : ", value = userDetail.name)
                        DetailRow(label = "Gender : ", value = userDetail.gender)
                        DetailRow(label = "Age : ", value = userDetail.age)
                        DetailRow(label = "Phone Number : ", value = userDetail.phoneNumber)
                        DetailRow(label = "Concentration : ", value = userDetail.concentration)
                    }
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row {
        Text(text = label, color = Color.Black)
        Text(text = value, color = Brown700)
    }
}
