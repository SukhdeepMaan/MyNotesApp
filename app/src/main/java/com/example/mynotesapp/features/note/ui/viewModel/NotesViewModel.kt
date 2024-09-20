package com.example.mynotesapp.features.note.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotesapp.base.ResultState
import com.example.mynotesapp.data.local.entities.Notes
import com.example.mynotesapp.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    // StateFlow for all notes
    private val _allNotes = MutableStateFlow<ResultState<List<Notes>>>(ResultState.Loading)
    val allNotes: StateFlow<ResultState<List<Notes>>> = _allNotes

    // StateFlow for a single note by ID
    private val _noteById = MutableStateFlow<ResultState<Notes>>(ResultState.Loading)
    val noteById: StateFlow<ResultState<Notes>> = _noteById

    // Fetch all notes
    init {
        getAllNotes()
    }

    private fun getAllNotes() {
        viewModelScope.launch {
            noteRepository.getAllNotes()
                .catch { e -> _allNotes.value = ResultState.Failure(e) }
                .collect { resultState ->
                    _allNotes.value = resultState
                }
        }
    }


    fun getNoteById(id: String) {
        viewModelScope.launch {
            noteRepository.getNoteById(id)
                .catch { e -> _noteById.value = ResultState.Failure(e) }
                .collect { resultState ->
                    _noteById.value = resultState
                }
        }
    }

    fun insertNote(note: Notes) {
        viewModelScope.launch {
            val result = noteRepository.insertNote(note)

            when (result) {
                is ResultState.Loading -> TODO()
                is ResultState.Failure -> TODO()
                is ResultState.Success -> TODO()
            }
        }
    }

    fun deleteNote(note: Notes) {
        viewModelScope.launch {
            val result = noteRepository.deleteNote(note)
            when (result) {
                is ResultState.Loading -> TODO()
                is ResultState.Failure -> TODO()
                is ResultState.Success -> TODO()
            }
        }
    }
}