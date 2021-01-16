package com.example.examenprimerbimestre

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import androidx.core.view.get
import androidx.lifecycle.Observer
import com.example.examenpokentrpp1.activEntren.AdaptadorSeries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    var idItemSeleccionado = 0
    lateinit var adaptador : AdaptadorSeries
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var db:Database.SeriesDatabase  = Database.SeriesDatabase.getInstance(this)
        val list = findViewById<ListView>(R.id.lst_series)
        adaptador = AdaptadorSeries(this)
        list.adapter = adaptador
        var SDao = db.serieDao
        val lista = SDao.AllSeries()

        lista.observe(this, Observer{ words ->
            // Update the cached copy of the words in the adapter.
            words?.let {
                adaptador.update(it)
            }
        });

        registerForContextMenu((list))
        val btnCrearSerie = findViewById<Button>(R.id.btnCrearSerie)

        btnCrearSerie.setOnClickListener {
            irActividad(CrearSerie::class.java)
        }

        list.setOnItemClickListener{parent, view, position, id ->

            //Toast.makeText(this@MainActivity, "You have Clicked " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show()

            val actual = adaptador.getItem(position) as SerieEntity
            irActividad(Actores::class.java,arrayListOf(Pair("id_actual",actual.id_Serie)))
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu,menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val db:Database.SeriesDatabase  = Database.SeriesDatabase.getInstance(this)
        val SDao = db.serieDao
        val list = findViewById<ListView>(R.id.lst_series)

        return when (item?.itemId){
            R.id.mi_editar -> {
                val actual = adaptador.getItem(idItemSeleccionado) as SerieEntity
                irActividad(EditarSerie::class.java,arrayListOf(Pair("id_actual",actual.id_Serie)))
                return true
            }
            R.id.mi_eliminar -> {
                val actual = adaptador.getItem(idItemSeleccionado) as SerieEntity
                GlobalScope.launch (Dispatchers.IO) {
                    SDao.delete_serie(actual.Nombre_serie)
                }
                return true
            }
            else -> super.onContextItemSelected(item)
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