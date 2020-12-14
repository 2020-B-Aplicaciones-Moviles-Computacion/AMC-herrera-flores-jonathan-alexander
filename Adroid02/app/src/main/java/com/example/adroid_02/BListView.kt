package com.example.adroid_02

import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class BListView : AppCompatActivity() {
    var idItemSeleccionado = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_list_view)

        val listaEntrenadores = BBaseDatosMemoria.arregloEntrenadores
        BBaseDatosMemoria.cargaInicialDatos()

        //El adaptador nos ayuda a mostrar los items de la list view de la manera que se espera
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, //un xml ya existente para dar el layout
            listaEntrenadores
        )

        val listView = findViewById<ListView>(R.id.lv_entrenador)
        listView.adapter = adaptador
/*
        listView
                .setOnItemLongClickListener { parent, view, position, id ->
                    Log.i("list-click","Hola ${position} ${id}")

                    val builder = AlertDialog.Builder(this)
                    /*builder.setMessage("Hola")
                            .setPositiveButton(
                                    "si",
                                    DialogInterface.OnClickListener { dialog, which ->
                                        Log.i("list-click","Si")
                                    }
                            ).setNegativeButton(
                                    "No",
                                    null
                            )*/
                    val seleccionUsuario = booleanArrayOf(
                            true,
                            false,
                            false
                    )
                    builder.setTitle("Título")

                    val opciones = resources.getStringArray(R.array.string_array_opciones_dialogo)
                    builder.setMultiChoiceItems(
                            opciones,
                            seleccionUsuario,
                            DialogInterface.OnMultiChoiceClickListener { dialog, which, isChecked ->
                                Log.i("list-click","Seleccione: ${which} ${isChecked}")
                            }
                    )

                    val dialogo = builder.create()
                    dialogo.show()
                    return@setOnItemLongClickListener true
                }*/
        adaptador.notifyDataSetChanged()

        registerForContextMenu((listView))

        val botonAnadirLV = findViewById <Button>(R.id.button_anadir_item_lv)

        botonAnadirLV
            .setOnClickListener{
                anadirListView(adaptador,BEntrenador("Marco", "Lider"),listaEntrenadores)
            }
    }

    private fun anadirListView(
        adaptador:ArrayAdapter<BEntrenador>,
        item:BEntrenador,
        arreglo: ArrayList<BEntrenador>
    ){
        arreglo.add(item)
        adaptador.notifyDataSetChanged()
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
        return when (item?.itemId){
            R.id.mi_editar -> {
                Log.i("intent_explicito","Editar" +
                "${BBaseDatosMemoria.arregloEntrenadores[idItemSeleccionado]}")
                return true
            }
            R.id.mi_eliminar -> {
                Log.i("intent_explicito","Eliminar" +
                        "${BBaseDatosMemoria.arregloEntrenadores[idItemSeleccionado]}")
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}