package com.luboganev.testground.demos.ipcMessenger.service


import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.luboganev.testground.R

object Sandwich {

    fun serve(context: Context, title: String?, text: String?) {
        val result = Toast(context)

        val inflate = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val sandwichView = inflate.inflate(R.layout.view_sandwich, null)
        val titleTextView = sandwichView.findViewById(R.id.title) as TextView
        val textTextView = sandwichView.findViewById(R.id.text) as TextView
        titleTextView.text = title
        textTextView.text = text

        result.view = sandwichView
        result.setGravity(Gravity.CENTER, 0, 0)
        result.duration = Toast.LENGTH_LONG
        result.show()
    }
}
