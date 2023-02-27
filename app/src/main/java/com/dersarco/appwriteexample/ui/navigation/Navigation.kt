package com.dersarco.appwriteexample.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.dersarco.appwriteexample.ui.screens.createuser.CreateUserView
import com.dersarco.appwriteexample.ui.screens.dbCreateDocument.CreateDocumentView
import com.dersarco.appwriteexample.ui.screens.dbListDocuments.ListDocumentsView
import com.dersarco.appwriteexample.ui.screens.loginUser.LoginUserView
import com.dersarco.appwriteexample.ui.screens.storageUpload.StorageUploadView
import com.dersarco.appwriteexample.ui.screens.welcome.WelcomeView

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = mainView) {
        navigation(
            startDestination = "/$mainView",
            route = mainView
        ) {
            composable("/$mainView") {
                WelcomeView {
                    navController.navigate(it)
                }
            }
            composable("/$createUserView") {
                CreateUserView {
                    navController.popBackStack()
                }
            }
            composable("/$loginUserView") {
                LoginUserView {
                    navController.popBackStack()
                }
            }
            composable("/$dbCreateDocument") {
                CreateDocumentView {
                    navController.popBackStack()
                }
            }
            composable("/$dbListDocuments") {
                ListDocumentsView {
                    navController.popBackStack()
                }
            }
            composable("/$storageUpload") {
                StorageUploadView {
                    navController.popBackStack()
                }
            }
        }
    }
}

const val mainView = "menu"
const val createUserView = "createUser"
const val loginUserView = "loginUser"
const val dbCreateDocument = "dbCreateDocument"
const val dbListDocuments = "dbListDocuments"
const val storageUpload = "storageUpload"
