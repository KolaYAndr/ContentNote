package com.example.contentnote

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
import androidx.compose.ui.Modifier
import com.example.contentnote.navigation.NotesNavHost
import com.example.contentnote.ui.theme.ContentNoteTheme

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContentNoteTheme {
                Scaffold(topBar = {
                    TopAppBar(
                        title = { Text(text = "C.Note") }
                    )
                },
                    content = {
                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues = it),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            NotesNavHost()
                        }
                    }
                )
                // A surface container using the 'background' color from the theme

            }
        }
    }
}
