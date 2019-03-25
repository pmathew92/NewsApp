package com.prince.newsapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.prince.newsapp.model.Article
import io.reactivex.Observable

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<Article>)

    @Query("SELECT * FROM Article")
    fun getNews(): Observable<List<Article>>

    @Query("DELETE FROM Article")
    fun deleteAll()
}