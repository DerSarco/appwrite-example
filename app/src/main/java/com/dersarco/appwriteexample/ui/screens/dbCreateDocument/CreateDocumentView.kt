package com.dersarco.appwriteexample.ui.screens.dbCreateDocument

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dersarco.appwriteexample.appwrite.AppWriteDatabase
import com.dersarco.appwriteexample.appwrite.AppWriteResponse
import com.dersarco.appwriteexample.data.entities.Person
import com.dersarco.appwriteexample.ui.common.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun CreateDocumentView(onButtonBackPressed: () -> Unit) {

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var name by rememberSaveable {
        mutableStateOf("")
    }
    var age by rememberSaveable {
        mutableStateOf(0)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Create Document") },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Column {
                Text(text = "Name")
                TextField(value = name, onValueChange = { name = it })
            }
            Column {
                Text(text = "Age")
                TextField(
                    value = age.toString(),
                    onValueChange = { age = if (it == "") 0 else it.toInt() },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            Button(onClick = {
                val person = Person(name, age)
                onCreateDocument(person, coroutineScope, context)
            }) {
                Text(text = "Create Document")
            }
        }
    }
}

fun onCreateDocument(person: Person, coroutineScope: CoroutineScope, context: Context) {
    coroutineScope.launch(Dispatchers.IO) {
        val response = AppWriteDatabase(context).createDocument(person)
        withContext(Dispatchers.Main){
            when (response) {
                is AppWriteResponse.Success -> showToast(
                    "Document ID: ${response.data.id} was created",
                    context
                )
                is AppWriteResponse.Error -> showToast("${response.code}: ${response.message}", context)
            }
        }
    }
}
