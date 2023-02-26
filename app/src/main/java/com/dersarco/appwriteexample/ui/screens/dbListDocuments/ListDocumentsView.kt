package com.dersarco.appwriteexample.ui.screens.dbListDocuments

import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dersarco.appwriteexample.appwrite.AppWriteDatabase
import com.dersarco.appwriteexample.appwrite.AppWriteResponse
import com.dersarco.appwriteexample.data.entities.Person
import com.dersarco.appwriteexample.ui.common.showToast
import com.google.gson.Gson
import io.appwrite.models.DocumentList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ListDocumentsView(onButtonBackPressed: () -> Unit) {

    val context = LocalContext.current

    var documentList by remember {
        mutableStateOf<com.dersarco.appwriteexample.data.entities.DocumentList?>(null)
    }

    LaunchedEffect(key1 = Unit) {
        when (val response = AppWriteDatabase(context).listDocuments()) {
            is AppWriteResponse.Success -> documentList = response.data
            is AppWriteResponse.Error -> {
                documentList = null
                showToast("${response.code}: ${response.message}", context)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "List Documents") },
                navigationIcon = {
                    Icon(
                        modifier = Modifier.clickable { onButtonBackPressed() },
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back Arrow"
                    )
                }
            )
        }
    ) { paddingValues ->
        if (documentList == null) {
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
                items(documentList!!.documents) {
                    Card(
                        modifier = Modifier.padding(8.dp).fillMaxWidth(),
                        backgroundColor = Color.LightGray,
                        elevation = 18.dp
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(text = "Name: ${it.data.name}")
                            Text(text = "Age: ${it.data.age.toInt()}")
                        }
                    }
                }
            }
        }

    }
}
