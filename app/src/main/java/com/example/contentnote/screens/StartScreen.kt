package com.example.contentnote.screens

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.contentnote.navigation.NavRoute
import com.example.contentnote.ui.theme.ContentNoteTheme
import com.example.contentnote.ui.theme.MainViewModel
import com.example.contentnote.ui.theme.MainViewModelFactory
import com.example.contentnote.utils.TYPE_FIREBASE
import com.example.contentnote.utils.TYPE_ROOM

@ExperimentalMaterial3Api
@Composable
fun StartScreen(navController: NavHostController) {
    val context = LocalContext.current
    val mainViewModel: MainViewModel = viewModel(factory = MainViewModelFactory(context.applicationContext as Application))

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        val paddingValues = it
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "What will we use?",
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Button(
                onClick = {
                    mainViewModel.initDatabase(TYPE_ROOM){
                        navController.navigate(route = NavRoute.Main.route)
                    }
                },
                modifier = Modifier
                    .width(200.dp)

            ) {
                Text(text = "Room database")
            }
            Button(
                onClick = {
                    mainViewModel.initDatabase(TYPE_FIREBASE){
                        navController.navigate(route = NavRoute.Main.route)
                    }
                },
                modifier = Modifier
                    .width(200.dp)
            ) {
                Text(text = "Firebase database")
            }
        }
    }

}

@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewStartScreen() {
    ContentNoteTheme {
        StartScreen(navController = rememberNavController())
    }
}