package com.gehad.base

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("android:imageUrl")
fun changeImage(image:ImageView ,url:String?){
    Glide.with(image).load(url).into(image)
}

