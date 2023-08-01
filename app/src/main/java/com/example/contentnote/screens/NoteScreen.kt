package com.example.contentnote.screens

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.contentnote.utils.Constants
import com.example.contentnote.utils.DB_TYPE
import com.example.contentnote.utils.TYPE_FIREBASE
import com.example.contentnote.utils.TYPE_ROOM
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun NoteScreen(navController: NavHostController, mainViewModel: MainViewModel, noteId: String?) {
    val notes = mainViewModel.readAllNotes().observeAsState(listOf()).value
    val note = when (DB_TYPE.value) {
        TYPE_ROOM -> {
            notes.firstOrNull { it.id == noteId?.toInt() } ?: Note()
        }

        TYPE_FIREBASE -> {
            notes.firstOrNull { it.firebaseId == noteId } ?: Note()
        }

        else -> Note()
    }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val openBottomSheet = rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val isTitleError = remember { mutableStateOf(false) }

    if (openBottomSheet.value) {
        ModalBottomSheet(
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
            onDismissRequest = { openBottomSheet.value = false }
        ) {
            Surface {
                val title = remember { mutableStateOf(note.title) }
                val subtitle = remember { mutableStateOf(note.subtitle) }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = Constants.Keys.EDIT_NOTE,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    OutlinedTextField(
                        value = title.value,
                        onValueChange = { newText ->
                            title.value = newText
                        },
                        label = { Text(text = Constants.Keys.NOTE_TITLE) },
                        keyboardActions = KeyboardActions(onAny = {
                            focusManager.clearFocus()
                            isTitleError.value = title.value.isEmpty()
                        }),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        isError = isTitleError.value,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = subtitle.value,
                        onValueChange = { newText ->
                            subtitle.value = newText
                        },
                        label = { Text(text = Constants.Keys.NOTE_SUBTITLE) },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.5f)
                    )
                    Button(
                        modifier = Modifier.padding(top = 16.dp),
                        onClick = {
                            mainViewModel.updateNote(
                                note = Note(
                                    id = note.id,
                                    title = title.value,
                                    subtitle = subtitle.value,
                                    firebaseId = note.firebaseId
                                )
                            ) {
                                openBottomSheet.value = false
                            }
                        },
                        enabled = subtitle.value.isNotEmpty() && title.value.isNotEmpty()
                    ) {
                        Text(text = Constants.Keys.UPDATE_NOTE)
                    }
                }
            }

        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        val paddingValues = it
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 4.dp)
                    .clickable {
                        coroutineScope.launch {
                            openBottomSheet.value = true
                        }
                    }
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = note.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                    Text(
                        text = note.subtitle,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                            .fillMaxWidth()
                    )
                }
            }
            Button(
                onClick = {
                    mainViewModel.deleteNote(note = note) {
                        navController.navigate(NavRoute.Main.route)
                    }
                },
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(
                    text = Constants.Keys.DELETE_NOTE,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewNoteScreen() {
    ContentNoteTheme {
        val context = LocalContext.current
        val mainViewModel: MainViewModel =
            viewModel(factory = MainViewModelFactory(context.applicationContext as Application))
        NoteScreen(
            navController = rememberNavController(), mainViewModel, "0"
        )
    }
}