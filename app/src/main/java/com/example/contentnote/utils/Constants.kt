package com.example.contentnote.utils

import com.example.contentnote.database.DatabaseRepository

const val TYPE_DATABASE = "type_database"
const val TYPE_ROOM = "type_room"
const val TYPE_FIREBASE = "type_firebase"
const val FIREBASE_ID = "firebaseId"

lateinit var REPOSITORY: DatabaseRepository
lateinit var LOGIN: String
lateinit var PASSWORD: String
lateinit var DB_TYPE: String


object Constants {
    object Keys {
        const val NOTE_DATABASE = "notes_database"
        const val NOTES_TABLE = "notes_table"
        const val ADD_NEW_NOTE = "Add a new note"
        const val NOTE_TITLE = "Note title"
        const val TITLE = "title"
        const val NOTE_SUBTITLE = "Note subtitle"
        const val SUBTITLE = "subtitle"
        const val SAVE_NOTE = "Save a note"
        const val WHAT_WILL_WE_USE = "What will we use?"
        const val ROOM_DATABASE = "Room database"
        const val FIREBASE_DATABASE = "Firebase database"
        const val ID = "Id"
        const val NONE = "None"
        const val EDIT_NOTE = "Edit note"
        const val EMPTY = ""
        const val UPDATE_NOTE = "Update note"
        const val DELETE_NOTE = "Delete note"
        const val SIGN_IN = "Sign in"
        const val LOGIN = "Login"
        const val LOG_IN = "Log in"
        const val PASSWORD = "Password"
    }

    object Screens {
        const val START_SCREEN = "start_screen"
        const val MAIN_SCREEN = "main_screen"
        const val ADD_SCREEN = "add_screen"
        const val NOTE_SCREEN = "note_screen"
    }
}