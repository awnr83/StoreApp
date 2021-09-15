package com.moappdev.solutions.storeapp.repository

import androidx.lifecycle.LiveData
import com.moappdev.solutions.storeapp.database.TiendaDao
import com.moappdev.solutions.storeapp.database.TiendaEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TiendaRepository(private val db:TiendaDao) {

    var tiendas: LiveData<List<TiendaEntity>> = db.allTiendas()

    suspend fun agregarTienda(tienda: TiendaEntity){
        withContext(Dispatchers.IO){
            db.insertTienda(tienda)
        }
    }
    suspend fun actualizarTienda(tienda: TiendaEntity){
        withContext(Dispatchers.IO){
            db.update(tienda)
        }
    }
    suspend fun eliminarTienda(tienda:TiendaEntity){
        withContext(Dispatchers.IO){
            return@withContext db.delete(tienda)
        }
    }
}