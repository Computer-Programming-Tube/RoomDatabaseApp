package com.android.roomdatabaseapp

import androidx.lifecycle.LiveData
import com.android.roomdatabaseapp.data.Word
import com.android.roomdatabaseapp.data.WordDao

class WordRepository(val wordDao: WordDao){

    val allWords: LiveData<List<Word>> = wordDao.getWordList()

    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}