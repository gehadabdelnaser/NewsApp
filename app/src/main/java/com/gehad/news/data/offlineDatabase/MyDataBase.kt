package com.gehad.news.data.offlineDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gehad.news.data.SourcesItem
import com.gehad.news.data.offlineDatabase.does.NewsDao

@Database(entities = arrayOf(SourcesItem::class),exportSchema = false,version = 1)

abstract class MyDataBase:RoomDatabase() {

    abstract fun newsDoe():NewsDao
    companion object{
        var myDataBaseInstance :MyDataBase ?= null

        fun getInstance(context: Context): MyDataBase?{

            if(myDataBaseInstance ==null){
                myDataBaseInstance = Room.databaseBuilder(context,
                    MyDataBase::class.java,"NewsDataBase")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }

            return myDataBaseInstance
        }
    }
}