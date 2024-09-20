package com.example.mynotesapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Note")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    var noteId : Int = 0,
    val title : String,
    val note : String
)
