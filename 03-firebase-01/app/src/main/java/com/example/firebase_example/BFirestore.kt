package com.example.firebase_example

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BFirestore : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_firestore)

        /*val db = Firebase.firestore

        db.collection("usuario")
            .get()
            .addOnSuccessListener{result->
                for (document in result){
                    Log.i("firebase-firestore","${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener {exception ->
                Log.w("firebase-firestore","Error",exception)
            }*/

        val btnProducto = findViewById<Button>(R.id.btn_producto)

        btnProducto
            .setOnClickListener{
                irActividad(CProducto::class.java)
            }

        val btnRestaurante = findViewById<Button>(R.id.btn_restaurante)

        btnRestaurante
            .setOnClickListener{
                irActividad(CRestaurante::class.java)
            }

        val btnOrdenes = findViewById<Button>(R.id.btn_ordenes)

        btnOrdenes
            .setOnClickListener{
                irActividad(COrdenes::class.java)
            }

        val btnVerOrdenes = findViewById<Button>(R.id.btn_verOrdenes)
        btnVerOrdenes
            .setOnClickListener{
                irActividad(DVisualizarOrdenes::class.java)
            }

    }

    fun irActividad(
        clase: Class<*>,
        parametros:ArrayList<Pair<String,*>>? = null,
        codigo: Int? = null
    ) {
        val intentExplicito = Intent(
            this,
            clase
        )
        parametros?.forEach {
            val nombreVariable = it.first
            val valorVariable: Any? = it.second

            when (it.second) {
                is String -> {
                    valorVariable as String
                    intentExplicito.putExtra(nombreVariable, valorVariable)
                }
                is Parcelable -> {
                    valorVariable as Parcelable
                    intentExplicito.putExtra(nombreVariable, valorVariable)
                }
                is Int -> {
                    valorVariable as Int
                    intentExplicito.putExtra(nombreVariable, valorVariable)
                }
                else -> {
                    valorVariable as String
                    intentExplicito.putExtra(nombreVariable, valorVariable)
                }
            }


        }

        if (codigo != null) {
            startActivityForResult(intentExplicito, codigo)
        } else {
            startActivity(intentExplicito)
        }
    }
}