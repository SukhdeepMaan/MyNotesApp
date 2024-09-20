package com.example.mynotesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mynotesapp.data.local.entities.Notes
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert
    suspend fun insertNote(notes: Notes)

    @Delete
    suspend fun deleteNote(notes: Notes)

    @Query("SELECT * FROM Note")
    fun getAllNotes() : Flow<List<Notes>>

    @Query("SELECT * FROM Note WHERE noteId = :id ")
    fun getNoteById(id : String) : Flow<Notes>

}