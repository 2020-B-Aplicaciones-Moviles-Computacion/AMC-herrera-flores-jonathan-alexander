package com.example.payphone_deber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TextView

class Contactos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contactos)

        findViewById<ImageView>(R.id.img_volverContactos)
            .setOnClickListener {
                finish()
            }

        val nombres = arrayOf(
            "Alexander Cabezas",
            "Alfredo Casas",
            "César Espinoza",
            "Claudio Bastidas",
            "Ernesto Rivera",
            "Gustavo Fernández",
            "Mario Bastidas",
            "Martha Cárdenas"
        )

        val etBuscar = findViewById<EditText>(R.id.et_buscarPersona)
        etBuscar.setOnEditorActionListener { textView, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if(etBuscar.text.toString() in nombres){
                    findViewById<TableLayout>(R.id.tb_nombres).visibility = View.INVISIBLE
                    findViewById<TableLayout>(R.id.tb_res).visibility = View.VISIBLE
                    findViewById<TextView>(R.id.tr_nombre).text= etBuscar.text
                } else if(etBuscar.text.toString() != ""){
                    findViewById<TableLayout>(R.id.tb_nombres).visibility = View.INVISIBLE
                    findViewById<TableLayout>(R.id.tb_res).visibility = View.INVISIBLE
                } else {
                    findViewById<TableLayout>(R.id.tb_nombres).visibility = View.VISIBLE
                    findViewById<TableLayout>(R.id.tb_res).visibility = View.INVISIBLE
                }
                true
            } else {
                findViewById<TableLayout>(R.id.tb_nombres).visibility = View.VISIBLE
                findViewById<TableLayout>(R.id.tb_res).visibility = View.INVISIBLE
                false
            }
        }
    }
}