package com.example.proyectosegundob_moviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class Egresos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_egresos)

        crearBarra()

        findViewById<ImageView>(R.id.btn_homeEgresos)
            .setOnClickListener {
                finishAffinity()
                startActivity(
                    Intent(
                        this,
                        Dashboard::class.java
                    )
                )
            }

        llenarLista()

    }

    fun crearBarra() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val bar = TopBar()
        val argumentos = Bundle()
        argumentos.putString("titulo", "Expenses")
        bar.arguments = argumentos

        fragmentTransaction.replace(R.id.rl_topBarEgresos, bar)
        fragmentTransaction.commit()
    }

    fun llenarLista() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val lista = ListaGeneralFragmento()
        val argumentos = Bundle()
        argumentos.putString("ubicacion", "egresos")
        lista.arguments = argumentos

        fragmentTransaction.replace(R.id.rl_listaEgresos, lista)
        fragmentTransaction.commit()

    }
}