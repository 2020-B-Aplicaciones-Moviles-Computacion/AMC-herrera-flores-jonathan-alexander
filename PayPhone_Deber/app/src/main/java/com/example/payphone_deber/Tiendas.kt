package com.example.payphone_deber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView

class Tiendas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiendas)
        findViewById<ImageView>(R.id.img_volver).setOnClickListener {
            finish()
        }

        findViewById<ImageView>(R.id.img_cancelaBusqueda).setOnClickListener {
            finish()
        }

        findViewById<ImageView>(R.id.img_cercana).setOnClickListener {
            findViewById<ImageView>(R.id.img_res).visibility = VISIBLE
            findViewById<ImageView>(R.id.img_nada).visibility = INVISIBLE
        }

        findViewById<EditText>(R.id.et_buscar).setOnEditorActionListener { textView, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                findViewById<ImageView>(R.id.img_res).visibility = VISIBLE
                findViewById<ImageView>(R.id.img_nada).visibility = INVISIBLE
                true
            } else {
                findViewById<ImageView>(R.id.img_res).visibility = INVISIBLE
                findViewById<ImageView>(R.id.img_nada).visibility = VISIBLE
                false
            }
        }
    }
}