package com.ape.android.util

import android.support.v4.app.FragmentActivity
import android.widget.Toast

fun FragmentActivity.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}
