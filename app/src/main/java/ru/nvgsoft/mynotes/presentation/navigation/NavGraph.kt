package ru.nvgsoft.mynotes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.nvgsoft.mynotes.presentation.screen.creation.CreateNoteScreen
import ru.nvgsoft.mynotes.presentation.screen.editing.EditNoteScreen
import ru.nvgsoft.mynotes.presentation.screen.notes.NotesScreen

@Composable
fun NavGraph(){

    val navController = rememberNavController()

    NavHost (
        navController =  navController,
        startDestination = Screen.Notes.route
    ){
        composable(Screen.Notes.route) {
            NotesScreen(
                onNoteClick = {
                    navController.navigate(Screen.EditNote.route)
                },
                onAddNoteClick = {
                    navController.navigate(Screen.CreateNote.route)
                }
            )
        }
        composable(Screen.EditNote.route) {
            EditNoteScreen(
                noteId = 5,
                onFinished = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.CreateNote.route) {
            CreateNoteScreen(
                onFinished = {
                    navController.popBackStack()
                }
            )
        }
    }
}

sealed class Screen(val route: String) {

    data object Notes: Screen("notes")

    data object CreateNote: Screen("create_note")

    data object EditNote: Screen("edit_note")
}