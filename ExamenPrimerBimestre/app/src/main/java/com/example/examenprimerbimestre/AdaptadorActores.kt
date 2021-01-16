package com.example.examenprimerbimestre

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class AdaptadorActores(private val context: Context,
                       private var dataSource: List<ActorEntity> = listOf(ActorEntity())) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getCount(): Int {
        return dataSource.size
    }


    override fun getItem(position: Int): Any {
        return dataSource[position]
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun update(list: List<ActorEntity>) {
        this.dataSource = list
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.actores_layout, parent, false)

        val nombre = rowView.findViewById(R.id.nombreActores_ly) as TextView
        val apellido = rowView.findViewById(R.id.apellidoActores_ly) as TextView
        val fechaNac = rowView.findViewById(R.id.fechaNacActores_ly) as TextView
        val genero = rowView.findViewById(R.id.generoActores_ly) as TextView
        val edad = rowView.findViewById(R.id.edadActores_ly) as TextView

        val entidad = getItem(position) as ActorEntity

        nombre.text = entidad.Nombre_actor
        apellido.text = entidad.Apellido_actor
        fechaNac.text = entidad.Fecha_nacimiento_actor
        genero.text= entidad.Genero_actor.toString()
        edad.text = entidad.Edad_actor.toString()

        return rowView
    }
}