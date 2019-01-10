package com.ape.android.util

import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.ape.R


fun EditText.getStr(): String = text.toString()

fun View.makeVisible(isVisible: Boolean) {
    if (isVisible)
        this.visibility = View.VISIBLE
    else
        this.visibility = View.GONE
}

fun RecyclerView.addDivider(lManager: LinearLayoutManager, divSize: Int = R.drawable.divider_2dp) {
    val divDec = DividerItemDecoration(context, lManager.orientation)
    divDec.setDrawable(ResourcesCompat.getDrawable(context.resources, divSize, null)!!)
    this.addItemDecoration(divDec)
}

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}


