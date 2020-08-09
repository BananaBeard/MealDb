package com.kovalenko.mealdb.view

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

@BindingAdapter("imageUrl", "placeholder")
fun loadImage(view: ImageView, url: String?, placeholder: Drawable) {
    view.scaleType = ImageView.ScaleType.CENTER
    Picasso.get()
        .load(url)
        .placeholder(placeholder)
        .into(view, object : Callback {
            override fun onSuccess() {
                view.scaleType = ImageView.ScaleType.CENTER_CROP
            }

            override fun onError(e: Exception?) {
            }
        })
}
