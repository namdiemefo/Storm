package com.naemo.storm.utils

import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar

class AppUtils() {


    fun showSnackBar(context: Context, view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            .show()
    }

}