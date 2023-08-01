package com.example.contentnote.database.firebase

import androidx.lifecycle.LiveData
import com.example.contentnote.database.DatabaseRepository
import com.example.contentnote.model.Note
import com.example.contentnote.utils.LOGIN
import com.example.contentnote.utils.PASSWORD
import com.google.firebase.auth.FirebaseAuth

class AppFirebaseRepository : DatabaseRepository {
    private val mAuth = FirebaseAuth.getInstance()
    override val readAll: LiveData<List<Note>>
        get() = TODO("Not yet implemented")

    override suspend fun create(note: Note, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun update(note: Note, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(note: Note, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        mAuth.signOut()
    }

    override fun connectToDatabase(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        mAuth.signInWithEmailAndPassword(LOGIN, PASSWORD)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener {
                mAuth.createUserWithEmailAndPassword(LOGIN, PASSWORD)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { onFailure(it.message.toString()) }
            }
    }
}