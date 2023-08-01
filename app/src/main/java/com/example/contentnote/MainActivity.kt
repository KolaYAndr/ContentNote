package com.example.contentnote

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.contentnote.navigation.NavRoute
import com.example.contentnote.navigation.NotesNavHost
import com.example.contentnote.ui.theme.ContentNoteTheme
import com.example.contentnote.ui.theme.MainViewModel
import com.example.contentnote.ui.theme.MainViewModelFactory
import com.example.contentnote.utils.DB_TYPE

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContentNoteTheme {
                val context = LocalContext.current
                val mainViewModel: MainViewModel =
                    viewModel(factory = MainViewModelFactory(context.applicationContext as Application))
                val navController = rememberNavController()
                Scaffold(topBar = {
                    TopAppBar(
                        title = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "C.Note")
                                if (DB_TYPE.value.isNotEmpty()) {
                                    Icon(imageVector = Icons.Default.ExitToApp,
                                        contentDescription = "Exit",
                                        modifier = Modifier.clickable {
                                            mainViewModel.signOut {
                                                navController.navigate(NavRoute.Start.route) {
                                                    popUpTo(NavRoute.Start.route){
                                                        inclusive = true
                                                    }
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                        },
                        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
                    )
                },
                    content = {
                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues = it),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            NotesNavHost(mainViewModel, navController)
                        }
                    }
                )
                // A surface container using the 'background' color from the theme

            }
        }
    }
}
