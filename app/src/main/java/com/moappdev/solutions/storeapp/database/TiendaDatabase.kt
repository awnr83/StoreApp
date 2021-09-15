package com.moappdev.solutions.storeapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TiendaEntity::class], version = 1)
abstract class TiendaDatabase:RoomDatabase() {
    abstract val tiendaDao: TiendaDao

    companion object{
        @Volatile
        private var INSTANCE: TiendaDatabase?=null

        fun getInstance(context: Context):TiendaDatabase{
            synchronized(this){
                var instance= INSTANCE
                if(instance==null){
                    instance= Room.databaseBuilder(
                        context,
                        TiendaDatabase::class.java,
                        "Tiendas")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE=instance
                }
                return instance
            }
        }
    }
}