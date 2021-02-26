package com.example.firebase_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CProducto : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c_producto)

        val botonCrear = findViewById<Button>(R.id.btn_crearProducto)

        botonCrear.setOnClickListener{
            crearProducto()
        }
    }

    fun crearProducto(){
        val editTextNombre = findViewById<EditText>(R.id.et_nombreProducto)
        val editTextPrecio = findViewById<EditText>(R.id.et_precioProducto)

        val nuevoProducto = hashMapOf<String,Any>(
            "nombre" to editTextNombre.text.toString(),
            "precio" to editTextPrecio.text.toString().toDouble()
        )
        Log.i("firebase-firestore","$nuevoProducto")

        val db = Firebase.firestore

        val referencia = db.collection("producto")
            .document()
            .set(nuevoProducto)

        referencia
            .addOnSuccessListener{

            }
            .addOnFailureListener{

            }
    }
}