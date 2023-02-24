package com.dersarco.appwriteexample.ui.screens.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dersarco.appwriteexample.R
import com.dersarco.appwriteexample.ui.navigation.createUserView
import com.dersarco.appwriteexample.ui.navigation.loginUserView

@Composable
fun WelcomeView(onGoToView: (String) -> Unit) {

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column {
                Button(onClick = { onGoToView("/$createUserView") }) {
                    Text(text = "Go to $createUserView")
                }
                Button(onClick = { onGoToView("/$loginUserView") }) {
                    Text(text = "Go to $loginUserView")
                }
            }
        }
    }
}
