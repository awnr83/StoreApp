package com.moappdev.solutions.storeapp.tiendas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.moappdev.solutions.storeapp.database.TiendaEntity
import com.moappdev.solutions.storeapp.databinding.ItemTiendaBinding

class TiendaAdapter(val click: TiendaListener,
                    val favorito: FavoritoListener)
    : ListAdapter<TiendaEntity, TiendaAdapter.ViewHolder>(TiendaDiffUtil()) {

    class TiendaListener(val clickListener:(tienda:TiendaEntity)->Unit) {
        fun onClick(tienda: TiendaEntity)=clickListener(tienda)
    }
    class FavoritoListener(val clickListener:(tienda: TiendaEntity)->Unit){
        fun onClick(tienda: TiendaEntity)=clickListener(tienda)
    }

    class ViewHolder private constructor(val binding: ItemTiendaBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(tienda: TiendaEntity,
                 click: TiendaListener,
                 favorito: FavoritoListener){
            binding.click= click
            binding.favorito= favorito
            binding.tienda= tienda
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater= LayoutInflater.from(parent.context)
                val binding= ItemTiendaBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class TiendaDiffUtil: ItemCallback<TiendaEntity>() {
        override fun areItemsTheSame(oldItem: TiendaEntity, newItem: TiendaEntity): Boolean {
            return oldItem===newItem
        }

        override fun areContentsTheSame(oldItem: TiendaEntity, newItem: TiendaEntity): Boolean {
            return oldItem==newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position),click, favorito)
    }
}