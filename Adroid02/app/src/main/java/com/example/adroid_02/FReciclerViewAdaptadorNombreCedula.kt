package com.example.adroid_02

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class FReciclerViewAdaptadorNombreCedula(
    private val listaEntrenador: List<BEntrenador>,
    private val contexto: GReciclerView,
    private val recyclerView: androidx.recyclerview.widget.RecyclerView
) : androidx.recyclerview.widget.RecyclerView.Adapter<
        FReciclerViewAdaptadorNombreCedula.MyViewHolder
        >() {
    inner class MyViewHolder(view: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val nombreTextView: TextView
        val cedulaTextView: TextView
        val likesTextView: TextView
        val accionButton: Button
        var numeroLikes = 0

        init {
            nombreTextView = view.findViewById(R.id.textViewNombre)
            cedulaTextView = view.findViewById(R.id.textViewCedula)
            accionButton = view.findViewById(R.id.button_dar_like)
            likesTextView = view.findViewById(R.id.textViewLikes)
            accionButton.setOnClickListener {
                this.anadirLike()
            }
        }

        fun anadirLike(){
            this.numeroLikes = this.numeroLikes +1
            likesTextView.text = this.numeroLikes.toString()
            contexto.aumentarTotalLikes()
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FReciclerViewAdaptadorNombreCedula.MyViewHolder {
        val intemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recicler_view_vista,
                parent,
                false
            )
        return MyViewHolder(intemView)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val entrenador = listaEntrenador[position]
        holder.nombreTextView.text = entrenador.nombre
        holder.cedulaTextView.text = entrenador.descripcion
        holder.accionButton.text = "Like ${entrenador.nombre}"
        holder.likesTextView.text = "0"
    }

    override fun getItemCount(): Int {
        return listaEntrenador.size
    }

}