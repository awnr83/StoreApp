package com.moappdev.solutions.storeapp.tiendas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moappdev.solutions.storeapp.database.TiendaDao
import com.moappdev.solutions.storeapp.database.TiendaEntity
import com.moappdev.solutions.storeapp.repository.TiendaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TiendaViewModel(private val db:TiendaDao): ViewModel() {
    private val jobViewModel= Job()
    private val uiScope= CoroutineScope(Dispatchers.Main+jobViewModel)
    override fun onCleared() {
        super.onCleared()
        jobViewModel.cancel()
    }

    private val repo= TiendaRepository(db)
    val allTiendas=repo.tiendas

    private val _navigate=MutableLiveData<Boolean>()
    val navigate: LiveData<Boolean>
        get()=_navigate

    fun onNavigate(){
        _navigate.value=true
    }
    fun onNavigateC(){
        _navigate.value=false
    }
    fun onFavorito(tienda:TiendaEntity){
        uiScope.launch {
            tienda.favorita=!tienda.favorita
            repo.actualizarTienda(tienda)
        }
    }
    fun onEliminar(tienda:TiendaEntity){
        uiScope.launch {
            repo.eliminarTienda(tienda)
        }
    }
    init{
        _navigate.value=false
    }
}