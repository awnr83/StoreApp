package com.moappdev.solutions.storeapp.tiendas

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.moappdev.solutions.storeapp.R

@BindingAdapter("cargarImagen")
fun loadImage(img:ImageView, urlImg:String){
    Glide.with(img.context)
        .load(urlImg)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .apply(RequestOptions()
            .placeholder(R.drawable.load_animation)
            .error(R.drawable.ic_broken))
        .centerCrop()
        .circleCrop()
        .into(img)
}

