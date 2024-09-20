package com.example.mynotesapp.features.note.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mynotesapp.base.ResultState
import com.example.mynotesapp.data.local.entities.Notes
import com.example.mynotesapp.features.note.ui.viewModel.NotesViewModel

@Composable
fun AllNotesScreen(noteViewModel: NotesViewModel) {
    // Collect the current state of all notes from the ViewModel
    val allNotesState by noteViewModel.allNotes.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Handle different UI states
        when (allNotesState) {
            is ResultState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.fillMaxSize())
            }

            is ResultState.Success -> {
                val notes = (allNotesState as ResultState.Success<List<Notes>>).data
                if (notes.isEmpty()) {
                    Text(
                        text = "No notes available",
                        modifier = Modifier.fillMaxSize(),
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center
                    )
                } else {
                    // Display the list of notes using LazyColumn
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(notes) { note ->
                            NoteItem(note)
                        }
                    }
                }
            }

            is ResultState.Failure -> {
                Text(
                    text = "Failed to load notes: ${(allNotesState as ResultState.Failure).error.message}",
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center
                )
            }
        }

        FloatingActionButton(
            onClick = { showDialog = true },
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Note")
        }

        if (showDialog) {
            InsertNoteDialog(
                onDismiss = { showDialog = false },
                onNoteAdded = { note ->
                    noteViewModel.insertNote(note)
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun NoteItem(note: Notes) {
    // Display each note title
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Text(
            text = note.title,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )
    }
}


@Composable
fun InsertNoteDialog(
    onDismiss: () -> Unit,
    onNoteAdded: (Notes) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var noteText by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Add New Note") },
        text = {
            Column {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(text = "Title") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = noteText,
                    onValueChange = { noteText = it },
                    label = { Text(text = "Note") },
                    modifier = Modifier.height(150.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotEmpty() && noteText.isNotEmpty()) {
                        onNoteAdded(Notes(title = title, note = noteText))
                    }
                }
            ) {
                Text("Add Note")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
