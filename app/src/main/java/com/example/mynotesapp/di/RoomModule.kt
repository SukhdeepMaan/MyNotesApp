package com.example.mynotesapp.di

import android.content.Context
import androidx.room.Room
import com.example.mynotesapp.data.local.dao.NoteDao
import com.example.mynotesapp.data.local.database.NoteDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun getDatabase(@ApplicationContext context: Context): NoteDataBase {
        return Room.databaseBuilder(
            context,
            NoteDataBase::class.java,
            "Note_Database")
            .build()
    }

    @Provides
    fun getDao (noteDataBase: NoteDataBase) : NoteDao = noteDataBase.notesDao()

}