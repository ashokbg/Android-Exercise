package com.ape.android.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.support.v4.widget.CircularProgressDrawable
import android.support.v7.content.res.AppCompatResources
import android.util.Log
import android.widget.ImageView
import com.ape.R
import com.ape.android.api.ApiConstants
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.target.Target
import java.io.File

object ImageLoader {
    var TAG = ImageLoader::class.java.simpleName

    fun load(imageView: ImageView, imgUrl: String?) {
        if (!imgUrl.isNullOrEmpty()) {
            GlideApp.with(imageView.context)
                .load(imgUrl)
                .attachRequestListener()
                .error(R.drawable.ic_launcher_foreground)
                .placeholder(progressDrawable(imageView.context))
                .into(imageView)
                .clearOnDetach()
        }
    }

    private fun <T : Any> GlideRequest<T>.attachRequestListener(
    ): GlideRequest<T> {
        return this.listener(object : com.bumptech.glide.request.RequestListener<T> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<T>?,
                isFirstResource: Boolean
            ): Boolean {
                Log.e(TAG, "onLoadFailed:$e.message")
                return false
            }

            override fun onResourceReady(
                resource: T?,
                model: Any?,
                target: Target<T>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                Log.i(TAG, "onResourceReady")
                return false
            }
        })
    }

    fun progressDrawable(mContext: Context): CircularProgressDrawable {
        val circularProgressDrawable = CircularProgressDrawable(mContext)
        return circularProgressDrawable.apply {
            strokeWidth = 5f
            centerRadius = 30f
            start()
        }
    }

}