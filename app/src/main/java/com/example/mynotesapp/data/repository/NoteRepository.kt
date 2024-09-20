package com.example.mynotesapp.data.repository

import com.example.mynotesapp.base.ResultState
import com.example.mynotesapp.data.local.dao.NoteDao
import com.example.mynotesapp.data.local.entities.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDao) {

    fun getAllNotes(): Flow<ResultState<List<Notes>>> = flow {
        emit(ResultState.Loading)
            try {
                noteDao.getAllNotes().collect { notes ->
                    emit(ResultState.Success(notes))
                }
            } catch (e: Exception) {
                emit(ResultState.Failure(e))
            }
        }.flowOn(Dispatchers.IO)

    fun getNoteById(id: String): Flow<ResultState<Notes>> = flow {
            emit(ResultState.Loading)
            try {
                noteDao.getNoteById(id).collect { note ->
                    emit(ResultState.Success(note))
                }
            } catch (e: Exception) {
                emit(ResultState.Failure(e))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun insertNote(note: Notes): ResultState<Unit> {
        return try {
            noteDao.insertNote(note)
            ResultState.Success(Unit)
        } catch (e: Exception) {
            ResultState.Failure(e)
        }
    }

    // delete a note
    suspend fun deleteNote(note: Notes): ResultState<Unit> {
        return try {
            noteDao.deleteNote(note)
            ResultState.Success(Unit)
        } catch (e: Exception) {
            ResultState.Failure(e)
        }
    }
}