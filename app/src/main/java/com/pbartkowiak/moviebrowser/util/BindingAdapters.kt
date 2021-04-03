package com.pbartkowiak.moviebrowser.util

import android.webkit.WebView
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pbartkowiak.moviebrowser.R
import com.pbartkowiak.moviebrowser.core.ItemCallback

@BindingAdapter("modelList")
fun <I> CompoundRecyclerView<I>.updateResults(list: ArrayList<I>?) {
    list?.run { updateResults(this) }
}

@BindingAdapter("callback")
fun <I> CompoundRecyclerView<I>.setRecyclerViewCallback(callback: ItemCallback<I>?) {
    callback?.run { setRecyclerViewCallback(this) }
}

@BindingAdapter("imageSrc")
fun ImageView.loadImage(url: String) {
    Glide.with(context)
        .setDefaultRequestOptions(RequestOptions().useAnimationPool(true))
        .load(url)
        .error(R.mipmap.ic_image_not_found)
        .into(this)
}

@BindingAdapter("webUrl")
fun WebView.loadWeb(url: String) {
    this.loadUrl(url)
}
