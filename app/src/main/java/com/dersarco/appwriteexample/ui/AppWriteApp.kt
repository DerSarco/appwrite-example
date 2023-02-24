package com.dersarco.appwriteexample.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dersarco.appwriteexample.ui.theme.AppWriteExampleTheme


@Composable
fun AppWrite(content: @Composable () -> Unit) {
    AppWriteExampleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            content()
        }
    }
}