package com.example.contentnote.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.contentnote.navigation.NavRoute
import com.example.contentnote.ui.theme.ContentNoteTheme

@ExperimentalMaterial3Api
@Composable
fun AddScreen(navController: NavHostController) {
    val title = remember { mutableStateOf("") }
    val subtitle = remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    Scaffold {
        val paddingValues = it
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Add a new note",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
            )
            OutlinedTextField(
                value = title.value,
                onValueChange = { newText ->
                    title.value = newText
                },
                label = { Text(text = "Note title") },
                keyboardActions = KeyboardActions(onAny = { focusManager.clearFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
            OutlinedTextField(
                value = subtitle.value,
                onValueChange = { newText ->
                    subtitle.value = newText
                },
                label = { Text(text = "Note subtitle") },
                keyboardActions = KeyboardActions(onAny = { focusManager.clearFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
            )
            Button(
                modifier = Modifier.padding(bottom = 8.dp),
                onClick = {
                    navController.navigate(route = NavRoute.Main.route)
                }
            )
            {
                Text(text = "Save a note")
            }
        }
    }
}


@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewAddScreen() {
    ContentNoteTheme {
        AddScreen(navController = rememberNavController())
    }
}