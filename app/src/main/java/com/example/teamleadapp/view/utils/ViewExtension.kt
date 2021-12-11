package com.example.teamleadapp.view.utils

import android.view.View

fun View.setVisibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.show(show: Boolean = true) {
    if (show) visibility = View.VISIBLE
    else hide()
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}
