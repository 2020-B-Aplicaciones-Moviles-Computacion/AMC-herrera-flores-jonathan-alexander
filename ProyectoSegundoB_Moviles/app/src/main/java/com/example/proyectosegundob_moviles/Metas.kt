package com.example.proyectosegundob_moviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class Metas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_metas)

        crearBarra()

        findViewById<ImageView>(R.id.btn_homeMetas)
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
        argumentos.putString("titulo", "Goals")
        bar.arguments = argumentos

        fragmentTransaction.replace(R.id.rl_topBarMetas, bar)
        fragmentTransaction.commit()
    }

    fun llenarLista(){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val lista = ListaGeneralFragmento()
        val argumentos = Bundle()
        argumentos.putString("ubicacion", "metas")
        lista.arguments = argumentos

        fragmentTransaction.replace(R.id.rl_listaMetas, lista)
        fragmentTransaction.commit()
    }
}