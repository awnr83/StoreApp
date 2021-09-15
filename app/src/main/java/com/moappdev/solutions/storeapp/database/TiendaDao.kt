package com.moappdev.solutions.storeapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TiendaDao {
    @Query("select * from tienda order by favorita desc")
    fun allTiendas():LiveData<List<TiendaEntity>>

    @Insert
    fun insertTienda(tienda: TiendaEntity)

    @Update
    fun update(tienda: TiendaEntity)

    @Delete
    fun delete(tienda: TiendaEntity)
}