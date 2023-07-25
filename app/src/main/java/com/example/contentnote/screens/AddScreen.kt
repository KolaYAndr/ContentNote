package com.example.contentnote.screens

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
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
import com.example.contentnote.utils.Constants.Keys.ADD_NEW_NOTE
import com.example.contentnote.utils.Constants.Keys.NOTE_SUBTITLE
import com.example.contentnote.utils.Constants.Keys.NOTE_TITLE
import com.example.contentnote.utils.Constants.Keys.SAVE_NOTE

@ExperimentalMaterial3Api
@Composable
fun AddScreen(navController: NavHostController, mainViewModel: MainViewModel) {
    val title = remember { mutableStateOf("") }
    val subtitle = remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val isTitleError = remember { mutableStateOf(false) }
    Scaffold {
        val paddingValues = it
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = ADD_NEW_NOTE,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
            )
            OutlinedTextField(
                value = title.value,
                onValueChange = { newText ->
                    title.value = newText
                },
                label = { Text(text = NOTE_TITLE) },
                keyboardActions = KeyboardActions(onAny = {
                    focusManager.clearFocus()
                    isTitleError.value = title.value.isEmpty()
                }),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                isError = isTitleError.value
            )
            OutlinedTextField(
                value = subtitle.value,
                onValueChange = { newText ->
                    subtitle.value = newText
                },
                label = { Text(text = NOTE_SUBTITLE) },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .padding(bottom = 8.dp, start = 8.dp, end = 8.dp),
            )
            Button(
                modifier = Modifier.padding(bottom = 8.dp),
                enabled = title.value.isNotEmpty() && subtitle.value.isNotEmpty(),
                onClick = {
                    mainViewModel.addNote(
                        note = Note(
                            title = title.value,
                            subtitle = subtitle.value
                        )
                    ) {
                        navController.navigate(route = NavRoute.Main.route)
                    }
                }
            )
            {
                Text(text = SAVE_NOTE)
            }
        }
    }
}


@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewAddScreen() {
    ContentNoteTheme {
        val context = LocalContext.current
        val mainViewModel: MainViewModel =
            viewModel(factory = MainViewModelFactory(context.applicationContext as Application))
        AddScreen(navController = rememberNavController(), mainViewModel)
    }
}