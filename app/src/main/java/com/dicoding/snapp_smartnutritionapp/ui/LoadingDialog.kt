package com.dicoding.snapp_smartnutritionapp.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.dicoding.snapp_smartnutritionapp.R

object LoadingDialog {
        private var dialog: AlertDialog? = null

        fun show(context: Context) {
            val builder = AlertDialog.Builder(context)
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.loading_dialog, null)
            builder.setView(view)
            builder.setCancelable(false)
            dialog = builder.create()
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.show()
        }

        fun hide() {
            dialog?.dismiss()
        }
}