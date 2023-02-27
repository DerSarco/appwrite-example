package com.dersarco.appwriteexample.ui.screens.storageUpload

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.dersarco.appwriteexample.appwrite.AppWriteResponse
import com.dersarco.appwriteexample.appwrite.AppWriteStorage
import com.dersarco.appwriteexample.ui.common.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@Composable
fun StorageUploadView(appWriteStorage: AppWriteStorage = get(), onButtonBackPressed: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            getFromActivityImage(uri, appWriteStorage, coroutineScope, context)
        }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Upload Image") },
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
            Button(onClick = {
                galleryLauncher.launch("image/*")
            }
            ) {
                Text(text = "Open Gallery")
            }
        }
    }
}


fun getFromActivityImage(
    uri: Uri?,
    appWriteStorage: AppWriteStorage,
    coroutineScope: CoroutineScope,
    context: Context
) {
    if (uri == null) {
        return
    }

    coroutineScope.launch {
        when (val response = appWriteStorage.uploadToStorage(uri)) {
            is AppWriteResponse.Success -> showToast("${response.data.id} was created!", context)
            is AppWriteResponse.Error -> showToast("${response.code}: ${response.message}", context)
        }
    }
}
