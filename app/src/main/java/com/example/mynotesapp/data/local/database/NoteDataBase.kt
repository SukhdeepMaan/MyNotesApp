package com.example.mynotesapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mynotesapp.data.local.dao.NoteDao
import com.example.mynotesapp.data.local.entities.Notes

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class NoteDataBase : RoomDatabase() {
    abstract fun notesDao(): NoteDao
}