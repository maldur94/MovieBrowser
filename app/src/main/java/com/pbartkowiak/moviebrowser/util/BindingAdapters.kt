package com.pbartkowiak.moviebrowser.util

import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
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
    val progressBar = CircularProgressDrawable(context)
    progressBar.strokeWidth = 5f
    progressBar.centerRadius = 30f
    progressBar.setColorSchemeColors(context.getColor(R.color.white))
    progressBar.start()

    Glide.with(context)
        .load(url)
        .placeholder(progressBar)
        .error(R.mipmap.ic_no_image_foreground)
        .into(this)
}

@BindingAdapter("webUrl")
fun WebView.loadWeb(url: String) {
    loadUrl(url)
    webViewClient = WebViewClient()
}
