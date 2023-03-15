package com.dersarco.appwriteexample.ui.screens.welcome

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dersarco.appwriteexample.R
import com.dersarco.appwriteexample.ui.navigation.*

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
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Accounts", modifier = Modifier.padding(top = 6.dp))
            Button(onClick = { onGoToView("/$createUserView") }) {
                Text(text = "Go to $createUserView")
            }
            Button(onClick = { onGoToView("/$loginUserView") }) {
                Text(text = "Go to $loginUserView")
            }
            Divider(modifier = Modifier.fillMaxWidth(). padding(6.dp))
            Text(text = "Database", modifier = Modifier.padding(top = 6.dp))
            Button(onClick = { onGoToView("/$dbCreateDocument") }) {
                Text(text = "Go to $dbCreateDocument")
            }
            Button(onClick = { onGoToView("/$dbListDocuments") }) {
                Text(text = "Go to $dbListDocuments")
            }

            Divider(modifier = Modifier.fillMaxWidth(). padding(6.dp))
            Text(text = "Storage", modifier = Modifier.padding(top = 6.dp))
            Button(onClick = { onGoToView("/$storageUpload") }) {
                Text(text = "Go to $storageUpload")
            }
            Button(onClick = { onGoToView("/$storageList") }) {
                Text(text = "Go to $storageList")
            }
            Divider(modifier = Modifier.fillMaxWidth(). padding(6.dp))
            Text(text = "Realtime", modifier = Modifier.padding(top = 6.dp))
            Button(onClick = { onGoToView("/$realtimeList") }) {
                Text(text = "Go to $realtimeList")
            }
        }
    }
}
