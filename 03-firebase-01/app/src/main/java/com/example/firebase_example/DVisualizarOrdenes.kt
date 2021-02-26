package com.example.firebase_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.example.firebase_example.dto.FirestoreOrdenDto
import com.google.firebase.Timestamp
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.ArrayList

class DVisualizarOrdenes : AppCompatActivity() {

    var arregloOrdenes = arrayListOf<FirestoreOrdenDto>()
    var last: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d_visualizar_ordenes)

        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloOrdenes
        )

        val listView = findViewById<ListView>(R.id.lst_ordenes)
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu((listView))

        ordenes(adaptador)

        val btnMasOrdenes = findViewById<Button>(R.id.btn_masOrdenes)
        btnMasOrdenes
            .setOnClickListener{
                ordenes(adaptador)
            }
    }

    fun ordenes(adaptador: ArrayAdapter<FirestoreOrdenDto>) {
        val db = Firebase.firestore
        val referencia = db.collection("orden")

        if (last==null){
            referencia
                .orderBy("review")
                .limit(1)
                .get()
                .addOnSuccessListener {
                    for (orden in it) {
                        val ordenAct = FirestoreOrdenDto(
                            orden.get("fechaCreacion") as Timestamp,
                            orden.get("restaurante") as HashMap<*,*>,
                            orden.get("usuario") as HashMap<*,*>,
                            orden.get("tiposComida") as ArrayList<String>,
                            orden.get("review") as Long
                        )
                        arregloOrdenes.add(ordenAct)
                    }
                    last = it.last().get("review") as Long
                    adaptador.notifyDataSetChanged()
                }
                .addOnFailureListener {
                    Log.e("firebase-firestore", "Error: $it")
                }
        } else {
            referencia
                .orderBy("review")
                .limit(1)
                .startAfter(last)
                .get()
                .addOnSuccessListener {
                    if(!it.isEmpty){
                        for (orden in it) {
                            val ordenAct = FirestoreOrdenDto(
                                orden.get("fechaCreacion") as Timestamp,
                                orden.get("restaurante") as HashMap<*,*>,
                                orden.get("usuario") as HashMap<*,*>,
                                orden.get("tiposComida") as ArrayList<String>,
                                orden.get("review") as Long
                            )
                            arregloOrdenes.add(ordenAct)
                        }

                        last = it.last().get("review") as Long
                        adaptador.notifyDataSetChanged()
                    }
                }
                .addOnFailureListener {
                    Log.e("firebase-firestore", "Error: $it")
                }
        }
    }
}