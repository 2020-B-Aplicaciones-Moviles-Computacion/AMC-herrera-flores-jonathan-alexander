package com.example.firebase_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment

class FFragmento : AppCompatActivity() {
    lateinit var fragmentoActual: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_f_fragmento)

        fragmentoActual = PrimerFragmento()
        crearFragmentoUno()

        val botonPrimer = findViewById<Button>(R.id.btn_crearPrimerFrag)
        botonPrimer
            .setOnClickListener{
                crearFragmentoUno()
            }

        val botonSegundo = findViewById<Button>(R.id.btn_crearSegundoFrag)
        botonSegundo
            .setOnClickListener{
                crearFragmentoDos()
            }
    }

    fun crearFragmentoUno(){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val primerFragmento = PrimerFragmento()
        val argumentos = Bundle()
        argumentos.putString("nombre","Adrián Eguez")
        argumentos.putInt("edad",31)
        primerFragmento.arguments = argumentos

        //añadir Fragmentos
        fragmentTransaction.replace(R.id.relative_layout_fragmentos,primerFragmento)
        fragmentoActual = primerFragmento
        fragmentTransaction.commit()
    }

    fun crearFragmentoDos(){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val segundoFragmento = SegundoFragmento()
        val argumentos = Bundle()
        argumentos.putString("nombre","Adrián Eguez")
        argumentos.putInt("edad",31)
        segundoFragmento.arguments = argumentos

        //añadir Fragmentos
        fragmentTransaction.replace(R.id.relative_layout_fragmentos,segundoFragmento)
        fragmentoActual = segundoFragmento
        fragmentTransaction.commit()
    }
}