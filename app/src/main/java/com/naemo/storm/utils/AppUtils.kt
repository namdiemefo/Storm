package com.naemo.storm.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import com.google.android.material.snackbar.Snackbar
import com.naemo.storm.R

class AppUtils() {

    private var mContext: Context? = null
    lateinit var dialog: Dialog

    constructor(context: Context): this() {
        this.mContext = context
    }

    fun showSnackBar(context: Context, view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .show()
    }

    fun showDialog(context: Context) {
        dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.custom_dialog)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    fun cancelDialog() {
        /*if (dialog.isShowing) {
            dialog.dismiss()
        }*/
        dialog.dismiss()
    }
}