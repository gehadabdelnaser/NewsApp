package com.gehad.news.data.offlineDatabase.does

import androidx.room.*
import com.gehad.news.data.SourcesItem

@Dao
interface NewsDao {

    @Insert
    fun insertNews(note: SourcesItem)
    @Insert
    fun insertAllNews(note: List<SourcesItem>)
    @Delete
    fun deleteNews(note: SourcesItem)

    @Query("DELETE FROM SourcesItem")
    fun deleteAll()

    @Update
    fun updateNews(note: SourcesItem)

    @Query("select * from SourcesItem")
    fun getAllNews():List<SourcesItem>

}