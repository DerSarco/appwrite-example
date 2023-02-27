package com.dersarco.appwriteexample.ui.screens.createuser

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.dersarco.appwriteexample.appwrite.AppWriteAccount
import com.dersarco.appwriteexample.appwrite.AppWriteResponse
import com.dersarco.appwriteexample.ui.common.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CreateUserView(onButtonBackPressed: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var userName by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var email by rememberSaveable {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Create User") },
                navigationIcon = {
                    Icon(
                        modifier = Modifier.clickable { onButtonBackPressed() },
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back Arraow"
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
                Text(text = "Email")
                TextField(value = email, onValueChange = { email = it })
            }
            Column {
                Text(text = "Password")
                TextField(value = password, onValueChange = { password = it })
            }
            Column {
                Text(text = "Username (Optional)")
                TextField(value = userName, onValueChange = { userName = it })
            }
            Button(onClick = { createUser(userName, password, email, coroutineScope, context) }) {
                Text(text = "Create User")
            }
        }
    }
}

fun createUser(
    username: String,
    password: String,
    email: String,
    coroutineScope: CoroutineScope,
    context: Context
) {
    coroutineScope.launch {
        when (val response = AppWriteAccount(context).registerUser(username, password, email)) {
            is AppWriteResponse.Success -> showToast(
                "${response.data.name} has been created!",
                context
            )
            is AppWriteResponse.Error -> showToast("${response.code}: ${response.message}", context)
        }
    }
}
