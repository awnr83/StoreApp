package com.moappdev.solutions.storeapp.detalle

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moappdev.solutions.storeapp.database.TiendaDao
import com.moappdev.solutions.storeapp.database.TiendaEntity
import com.moappdev.solutions.storeapp.repository.TiendaRepository
import kotlinx.coroutines.*

class DetalleViewModel(private val db:TiendaDao,private var _tienda: TiendaEntity): ViewModel(){
    private val jobViewModel= Job()
    private val uiScope= CoroutineScope(Dispatchers.Main+jobViewModel)
    override fun onCleared() {
        jobViewModel.cancel()
        super.onCleared()
    }

    var repo= TiendaRepository(db)

    private val _store=MutableLiveData<TiendaEntity>()
    val store: LiveData<TiendaEntity>
        get()=_store

    private val _navigate=MutableLiveData<Boolean>()
    val navigate: LiveData<Boolean>
        get()=_navigate
    fun onNavigateC(){
        _navigate.value=false
    }

    private val _datos=MutableLiveData<Boolean>()
    val datos: LiveData<Boolean>
        get()=_datos
    fun faltanDatos(){
        _navigate.value=true
    }

    fun guardar(){
        uiScope.launch {
            if(_store.value!!.id!=0L)
                repo.actualizarTienda(_store.value!!)
            else
                repo.agregarTienda(_store.value!!)
            _navigate.value = true
        }
    }

    fun onDialog(){

    }

    init {
        _store.value =_tienda
        _navigate.value =false
    }
}