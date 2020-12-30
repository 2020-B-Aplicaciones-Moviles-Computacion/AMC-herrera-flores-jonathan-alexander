package com.example.adroid_02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GReciclerView : AppCompatActivity() {
    var totalLikes = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_g_recicler_view)
        val listaEntrenador = arrayListOf<BEntrenador>()
        val ligaPokemon = DLiga("Kanto","LIga Kanto")
        listaEntrenador
            .add(
                BEntrenador(
                    "Jonathan",
                    "1718123748",
                    ligaPokemon
                )
            )
        listaEntrenador
            .add(BEntrenador(
                "Alexander",
                "1892039945",
                ligaPokemon
            ))

        val recyclerViewEntrenador = findViewById<RecyclerView>(
            R.id.rv_entrenadores
        )
        this.iniciarRecyclerView(
            listaEntrenador,
            this,
            recyclerViewEntrenador
        )

    }
    fun iniciarRecyclerView(
        lista: List<BEntrenador>,
        actividad: GReciclerView,
        recyclerView: androidx.recyclerview.widget.RecyclerView
    ){
        val adaptador = FReciclerViewAdaptadorNombreCedula(
            lista,
            actividad,
            recyclerView
        )

        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(actividad)
    }

    fun aumentarTotalLikes(){
        totalLikes += 1
        val textView = findViewById<TextView>(R.id.tv_total_likes)
        textView.text = totalLikes.toString()
    }
}