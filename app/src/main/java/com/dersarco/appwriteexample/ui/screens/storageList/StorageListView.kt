package com.dersarco.appwriteexample.ui.screens.storageList

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.dersarco.appwriteexample.appwrite.AppWriteResponse
import com.dersarco.appwriteexample.appwrite.AppWriteStorage
import com.dersarco.appwriteexample.ui.common.showToast
import org.koin.androidx.compose.get

@Composable
fun StorageListView(appWriteStorage: AppWriteStorage = get(), onButtonBackPressed: () -> Unit) {

    val context = LocalContext.current

    var imageList by remember {
        mutableStateOf<List<Bitmap>?>(null)
    }
    LaunchedEffect(key1 = Unit) {
        when (val response = appWriteStorage.getStorageList()) {
            is AppWriteResponse.Success -> imageList = response.data
            is AppWriteResponse.Error -> showToast("${response.code}: ${response.message}", context)
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "List Image") },
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
            if (imageList != null) {
                LazyColumn {
                    items(imageList!!) {
                        Image(bitmap = it.asImageBitmap(), contentDescription = "image")
                    }
                }
            } else {
                Text(text = "Not data from storage retrieved")
            }
        }
    }
}
