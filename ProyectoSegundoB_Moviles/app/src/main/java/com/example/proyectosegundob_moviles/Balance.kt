package com.example.proyectosegundob_moviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.proyectosegundob_moviles.AuthUsuario.Companion.usuario
import com.example.proyectosegundob_moviles.Firestore.Companion.db
import com.example.proyectosegundob_moviles.dto.IngresoEgresoDTO

class Balance : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_balance)

        crearBarra()

        findViewById<ImageView>(R.id.btn_homeIE)
            .setOnClickListener {
                finishAffinity()
                startActivity(
                    Intent(
                        this,
                        Dashboard::class.java
                    )
                )
            }

        findViewById<ConstraintLayout>(R.id.btn_ingresos)
            .setOnClickListener {
                startActivity(
                    Intent(
                        this,
                        Ingresos::class.java
                    )
                )
            }

        findViewById<ConstraintLayout>(R.id.btn_egresos)
            .setOnClickListener {
                startActivity(
                    Intent(
                        this,
                        Egresos::class.java
                    )
                )
            }

        val tvIngresos = findViewById<TextView>(R.id.tv_ingresosCompleto)
        var ingTotal = 0.0
        db.collection("Usuarios").document(usuario!!.email).collection("ingresos").get()
            .addOnSuccessListener {
                for (document in it){
                    val ing = document.toObject(IngresoEgresoDTO::class.java)
                    if (ing.estado=="Active") {
                        ingTotal += ing.cantidad!!.toDouble()
                    }
                }
                tvIngresos.text = "$$ingTotal"
            }
            .addOnFailureListener{
                Log.i("firestore","Falló recuperación: $it")
            }

        val tvEgresos = findViewById<TextView>(R.id.tv_egresosCompleto)
        var egTotal = 0.0
        db.collection("Usuarios").document(usuario!!.email).collection("egresos").get()
            .addOnSuccessListener {
                for (document in it){
                    val eg = document.toObject(IngresoEgresoDTO::class.java)
                    if (eg.estado=="Active") {
                        egTotal += eg.cantidad!!.toDouble()
                    }
                }
                tvEgresos.text = "$$egTotal"
            }
            .addOnFailureListener{
                Log.i("firestore","Falló recuperación: $it")
            }
    }

    override fun onRestart() {
        super.onRestart()
        finish()
        startActivity(
            Intent(
                this,
                Dashboard::class.java
            )
        )
        overridePendingTransition(R.anim.no_animation, R.anim.no_animation)
    }

    fun crearBarra() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val bar = TopBar()
        val argumentos = Bundle()
        argumentos.putString("titulo", "Balance")
        bar.arguments = argumentos

        fragmentTransaction.replace(R.id.rl_topBarIE, bar)
        fragmentTransaction.commit()
    }
}