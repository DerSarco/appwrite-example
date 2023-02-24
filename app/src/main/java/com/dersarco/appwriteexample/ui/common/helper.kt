package com.dersarco.appwriteexample.ui.common

import android.content.Context
import android.widget.Toast

fun showToast(message: String, context: Context) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}