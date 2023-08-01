package com.example.contentnote.screens

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
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
import com.example.contentnote.navigation.NavRoute
import com.example.contentnote.ui.theme.ContentNoteTheme
import com.example.contentnote.ui.theme.MainViewModel
import com.example.contentnote.ui.theme.MainViewModelFactory
import com.example.contentnote.utils.Constants
import com.example.contentnote.utils.Constants.Keys.FIREBASE_DATABASE
import com.example.contentnote.utils.Constants.Keys.ROOM_DATABASE
import com.example.contentnote.utils.Constants.Keys.WHAT_WILL_WE_USE
import com.example.contentnote.utils.DB_TYPE
import com.example.contentnote.utils.LOGIN
import com.example.contentnote.utils.PASSWORD
import com.example.contentnote.utils.TYPE_FIREBASE
import com.example.contentnote.utils.TYPE_ROOM
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun StartScreen(navController: NavHostController, mainViewModel: MainViewModel) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val openBottomSheet = rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val isEmailError = remember { mutableStateOf(false) }

    if (openBottomSheet.value) {
        ModalBottomSheet(
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
            onDismissRequest = { openBottomSheet.value = false }
        ) {
            Surface {
                val login = remember { mutableStateOf("") }
                val password = remember { mutableStateOf("") }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = Constants.Keys.SIGN_IN,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    OutlinedTextField(
                        value = login.value,
                        onValueChange = { newText ->
                            login.value = newText
                        },
                        label = { Text(text = Constants.Keys.LOGIN) },
                        keyboardActions = KeyboardActions(onAny = {
                            focusManager.clearFocus()
                            isEmailError.value = login.value.isEmpty()
                        }),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        isError = isEmailError.value,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = password.value,
                        onValueChange = { newText ->
                            password.value = newText
                        },
                        label = { Text(text = Constants.Keys.PASSWORD) },
                        keyboardActions = KeyboardActions(onAny = {
                            focusManager.clearFocus()
                        }),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Button(
                        modifier = Modifier.padding(top = 16.dp),
                        onClick = {
                            LOGIN = login.value
                            PASSWORD = password.value
                            mainViewModel.initDatabase(TYPE_FIREBASE) {
                                DB_TYPE.value = TYPE_FIREBASE
                                openBottomSheet.value = false
                                navController.navigate(NavRoute.Main.route)
                            }
                        },
                        enabled = password.value.isNotEmpty() && login.value.isNotEmpty()
                    ) {
                        Text(text = Constants.Keys.LOG_IN)
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
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = WHAT_WILL_WE_USE,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Button(
                onClick = {
                    mainViewModel.initDatabase(TYPE_ROOM) {
                        DB_TYPE.value = TYPE_ROOM
                        navController.navigate(route = NavRoute.Main.route)
                    }
                },
                modifier = Modifier
                    .width(200.dp)

            ) {
                Text(text = ROOM_DATABASE)
            }
            Button(
                onClick = {
                    coroutineScope.launch { openBottomSheet.value = true }
                },
                modifier = Modifier
                    .width(200.dp)
            ) {
                Text(text = FIREBASE_DATABASE)
            }
        }
    }

}

@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewStartScreen() {
    ContentNoteTheme {
        val context = LocalContext.current
        val mainViewModel: MainViewModel =
            viewModel(factory = MainViewModelFactory(context.applicationContext as Application))
        StartScreen(navController = rememberNavController(), mainViewModel)
    }
}