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

sealed class NavRoute(val route: String) {
    object Start : NavRoute("start_screen")
    object Main : NavRoute("main_screen")
    object Add : NavRoute("add_screen")
    object Note : NavRoute("note_screen")
}
@ExperimentalMaterial3Api
@Composable
fun NotesNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoute.Start.route) {
        composable(NavRoute.Start.route) { StartScreen(navController) }
        composable(NavRoute.Main.route) { MainScreen(navController) }
        composable(NavRoute.Add.route) { AddScreen(navController) }
        composable(NavRoute.Note.route) { NoteScreen(navController) }
    }
}