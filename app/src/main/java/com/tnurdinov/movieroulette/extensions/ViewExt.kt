package com.tnurdinov.movieroulette.extensions

import android.view.View

inline fun View.onClick(noinline l: (v: android.view.View?) -> Unit) {
    setOnClickListener(l)
}