package com.example.contentnote.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.contentnote.screens.AddScreen
import com.example.contentnote.screens.MainScreen
import com.example.contentnote.screens.NoteScreen
import com.example.contentnote.screens.StartScreen
import com.example.contentnote.ui.theme.MainViewModel
import com.example.contentnote.utils.Constants
import com.example.contentnote.utils.Constants.Screens.ADD_SCREEN
import com.example.contentnote.utils.Constants.Screens.MAIN_SCREEN
import com.example.contentnote.utils.Constants.Screens.NOTE_SCREEN
import com.example.contentnote.utils.Constants.Screens.START_SCREEN

sealed class NavRoute(val route: String) {
    object Start : NavRoute(START_SCREEN)
    object Main : NavRoute(MAIN_SCREEN)
    object Add : NavRoute(ADD_SCREEN)
    object Note : NavRoute(NOTE_SCREEN)
}
@ExperimentalMaterial3Api
@Composable
fun NotesNavHost(mainViewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoute.Start.route) {
        composable(NavRoute.Start.route) { StartScreen(navController = navController, mainViewModel = mainViewModel) }
        composable(NavRoute.Main.route) { MainScreen(navController = navController, mainViewModel = mainViewModel) }
        composable(NavRoute.Add.route) { AddScreen(navController = navController, mainViewModel = mainViewModel) }
        composable(NavRoute.Note.route + "/{${Constants.Keys.ID}}") {backStackEntry ->
            NoteScreen(navController = navController, mainViewModel = mainViewModel, noteId = backStackEntry.arguments?.getString(Constants.Keys.ID)) }
    }
}