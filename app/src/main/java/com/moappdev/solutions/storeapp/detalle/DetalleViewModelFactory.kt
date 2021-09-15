package com.moappdev.solutions.storeapp.detalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moappdev.solutions.storeapp.database.TiendaDao
import com.moappdev.solutions.storeapp.database.TiendaEntity

class DetalleViewModelFactory(private val db: TiendaDao, private var tienda: TiendaEntity):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetalleViewModel::class.java))
            return DetalleViewModel(db, tienda) as T
        throw IllegalArgumentException("Error al crear el Detalle-ViewModel")
    }
}