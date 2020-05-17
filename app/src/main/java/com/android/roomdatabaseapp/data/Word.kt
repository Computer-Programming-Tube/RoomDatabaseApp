package com.android.roomdatabaseapp.data

import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
class Word(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "word")
    val word:String
)