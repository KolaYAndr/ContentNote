package com.example.contentnote.screens

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.contentnote.model.Note
import com.example.contentnote.navigation.NavRoute
import com.example.contentnote.ui.theme.ContentNoteTheme
import com.example.contentnote.ui.theme.MainViewModel
import com.example.contentnote.ui.theme.MainViewModelFactory
import androidx.compose.foundation.lazy.items
import com.example.contentnote.utils.Constants
import com.example.contentnote.utils.Constants.Keys.ADD_NEW_NOTE
import com.example.contentnote.utils.DB_TYPE
import com.example.contentnote.utils.TYPE_FIREBASE
import com.example.contentnote.utils.TYPE_ROOM

@ExperimentalMaterial3Api
@Composable
fun MainScreen(navController: NavHostController, mainViewModel: MainViewModel) {
    val notes = mainViewModel.readAllNotes().observeAsState().value

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.navigate(route = NavRoute.Add.route)
        }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = ADD_NEW_NOTE,
                tint = Color.White
            )
        }
    }) {
        val paddingValues = it
        if (notes != null) {
            LazyColumn {
                items(items = notes, key = { note ->
                    note.id
                }) { note ->
                    NoteItem(note, navController)
                }
            }
        }
    }
}

@Composable
private fun NoteItem(note: Note, navController: NavHostController) {
    val noteId = when(DB_TYPE){
        TYPE_FIREBASE -> note.firebaseId
        TYPE_ROOM -> note.id
        else -> Constants.Keys.EMPTY
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 24.dp)
            .clickable {
                navController.navigate(route = NavRoute.Note.route + "/${noteId}")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = note.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = note.subtitle)
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewMainScreen() {
    ContentNoteTheme {
        val context = LocalContext.current
        val mainViewModel: MainViewModel =
            viewModel(factory = MainViewModelFactory(context.applicationContext as Application))
        MainScreen(navController = rememberNavController(), mainViewModel)
    }
}