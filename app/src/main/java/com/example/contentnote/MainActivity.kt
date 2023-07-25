package com.example.contentnote

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.contentnote.navigation.NotesNavHost
import com.example.contentnote.ui.theme.ContentNoteTheme
import com.example.contentnote.ui.theme.MainViewModel
import com.example.contentnote.ui.theme.MainViewModelFactory

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContentNoteTheme {
                val context = LocalContext.current
                val mainViewModel: MainViewModel =
                    viewModel(factory = MainViewModelFactory(context.applicationContext as Application))
                Scaffold(topBar = {
                    TopAppBar(
                        title = { Text(text = "C.Note") },
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
                            NotesNavHost(mainViewModel)
                        }
                    }
                )
                // A surface container using the 'background' color from the theme

            }
        }
    }
}
