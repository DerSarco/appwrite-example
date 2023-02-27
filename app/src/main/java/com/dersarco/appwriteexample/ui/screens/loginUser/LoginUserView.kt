package com.dersarco.appwriteexample.ui.screens.loginUser

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import io.appwrite.models.Account
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get


@Composable
fun LoginUserView(appWriteAccount: AppWriteAccount = get(), onButtonBackPressed: () -> Unit) {

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var email by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    var userData by remember {
        mutableStateOf<Account<Any>?>(null)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Create User") },
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
                Text(text = "Email")
                TextField(value = email, onValueChange = { email = it })
            }
            Column {
                Text(text = "Password")
                TextField(value = password, onValueChange = { password = it })
            }
            Button(onClick = {
                onLogin(email, password, coroutineScope, appWriteAccount, context) {
                    userData = it
                }
            }) {
                Text(text = "Login")
            }
            if (userData != null) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(text = userData!!.email)
                    Text(text = userData!!.name)
                    Text(text = userData!!.createdAt)
                    Text(text = userData!!.emailVerification.toString())
                    Text(text = userData!!.phoneVerification.toString())
                }
            }
        }
    }
}

fun onLogin(
    email: String,
    password: String,
    coroutineScope: CoroutineScope,
    appWriteAccount: AppWriteAccount,
    context: Context,
    onUserDataRetrieved: (Account<Any>?) -> Unit
) {
    coroutineScope.launch {
        when (val response = appWriteAccount.loginUser(email, password)) {
            is AppWriteResponse.Success -> {
                onUserDataRetrieved(response.data)
            }
            is AppWriteResponse.Error -> {
                showToast("${response.code}: ${response.message}", context)
            }
        }
    }
}
