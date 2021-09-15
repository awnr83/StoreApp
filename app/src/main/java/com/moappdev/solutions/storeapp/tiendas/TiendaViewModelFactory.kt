package com.moappdev.solutions.storeapp.tiendas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moappdev.solutions.storeapp.database.TiendaDao

class TiendaViewModelFactory(private val db: TiendaDao):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TiendaViewModel::class.java))
            return TiendaViewModel(db) as T
        throw IllegalArgumentException("Error al crear el Tienda-ViewModel")
    }
}