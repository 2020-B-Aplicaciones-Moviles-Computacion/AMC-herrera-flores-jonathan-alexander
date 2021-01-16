package com.example.examenpokentrpp1.activEntren


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.examenprimerbimestre.R
import com.example.examenprimerbimestre.SerieEntity


class AdaptadorSeries(private val context: Context,
                    private var dataSource: List<SerieEntity> = listOf(SerieEntity())) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getCount(): Int {
        return dataSource.size
    }


    override fun getItem(position: Int): Any {
        return dataSource[position]
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun update(list: List<SerieEntity>){
        this.dataSource = list
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.series_layout, parent, false)

        val nombre = rowView.findViewById(R.id.nombre) as TextView
        val clasificacion = rowView.findViewById(R.id.clas) as TextView
        val aire = rowView.findViewById(R.id.aire) as TextView

        val entidad = getItem(position) as SerieEntity

        nombre.text = entidad.Nombre_serie
        clasificacion.text = entidad.Clasificacion_serie.toString()
        if(entidad.Al_aire_serie){
            aire.text = "Al aire"
        } else {
            aire.text = "No está al aire"
        }

        return rowView
    }
}