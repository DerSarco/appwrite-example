package com.dersarco.appwriteexample.ui.screens.realtime

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel

@Composable
fun RealTimeView(
    realTimeViewModel: RealTimeViewModel = getViewModel(),
    onButtonBackPressed: () -> Unit
) {
    realTimeViewModel.subscribe()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Realtime") },
                navigationIcon = {
                    Icon(
                        modifier = Modifier.clickable { onButtonBackPressed() },
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back Arrow"
                    )
                },
                actions = {
                    Icon(
                        modifier = Modifier.clickable {
                            realTimeViewModel.createNewEntry()
                        },
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add"
                    )
                }
            )
        }
    ) { paddingValues ->
        DocumentListView(realTimeViewModel, paddingValues)
    }
}

@Composable
fun DocumentListView(realTimeViewModel: RealTimeViewModel, paddingValues: PaddingValues) {
    val documentList = realTimeViewModel.documentsStream.collectAsState()

    if (documentList.value == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "No data found...")
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            items(documentList.value!!) {
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    backgroundColor = Color.LightGray,
                    elevation = 18.dp
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(text = "Name: ${it.name}", color = Color.Black)
                        Text(text = "Age: ${it.age.toInt()}", color = Color.Black)
                    }
                }
            }
        }
    }
}
