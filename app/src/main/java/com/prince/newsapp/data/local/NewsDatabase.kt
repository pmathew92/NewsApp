package com.prince.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.prince.newsapp.model.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

}