package com.raka.myapplication.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.raka.trendinggitwithdaggerhilt.data.model.local.ItemsLocal
import com.raka.myapplication.view.utils.Constants.Companion.DB_NAME

@Database(entities = [ItemsLocal::class],version = 2)
abstract class AppDatabase:RoomDatabase() {
    abstract fun parametersDao(): ParametersDao
    companion object{
        private var INSTANCE : AppDatabase? = null
        fun getInstance(context:Context):AppDatabase{
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java,
                        DB_NAME)
                        .build()
                }
            }
            return INSTANCE!!
        }
        fun destroyInstance(){
            INSTANCE = null
        }
    }
}