package com.moappdev.solutions.storeapp.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName="Tienda")
data class TiendaEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var nombre: String,
    var telefono: String="",
    var sitioWeb: String="",
    var imgWeb: String="",
    var favorita: Boolean=false
): Parcelable