package com.example.adroid_02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonCicloVida = findViewById<Button>(R.id.button_ir_ciclo_vida)
        buttonCicloVida.setOnClickListener{
            irActividad(ACicloVida::class.java)
        }

        val buttonListView = findViewById<Button>(R.id.button_ir_list_view)
        buttonListView.setOnClickListener{
            irActividad(BListView::class.java)
        }

        val buttonIntentExplicitoParametros = findViewById<Button>(R.id.button_ir_intent_explicito_con_parametros)
        buttonIntentExplicitoParametros.setOnClickListener{
            val parametros = arrayListOf<Pair<String,*>>(
                Pair("nombre","Jonathan"),
                Pair("apellido","Herrera"),
                Pair("edad",26)
            )
            irActividad(CIntentExplicitoParametros::class.java,parametros)
        }
    }

    fun irActividad(
        clase: Class<*>,
        parametros:ArrayList<Pair<String,*>>? = null
    ){
        val intentExplicito = Intent(
            this,
            clase
        )
        if(parametros!=null){
            parametros.forEach {
                var nombreVariable = it.first
                var valorVariable = if(it.second is Int) it.second as Int else it.second.toString()
                intentExplicito.putExtra(nombreVariable, valorVariable)
            }
        }
        startActivityForResult(intentExplicito,102)
    }

    override fun onActivityResult(
            requestCode: Int,
            resultCode: Int,
            data: Intent?
    ){
        super.onActivityResult(requestCode,resultCode,data)

        val CODIGO_ACTUALIZAR_DATOS = 102
        when(requestCode) {
            CODIGO_ACTUALIZAR_DATOS -> {
                if (resultCode == RESULT_OK){
                    Log.i("intent_explicito","Sí actualizo los datos")

                    if(data!=null){
                        val nombre = data.getStringExtra("nombre")
                        val edad = data.getIntExtra("edad",0)
                        Log.i("intent_explicito","Nombre: ${nombre} Edad: ${edad}")
                    } else {
                        //Aquí es el otro bloque sin parámetros de respuesta pero con datos
                    }

                } else {
                    Log.i("intent_explicito","Usuario no llenó los datos")
                }

            }
        }
    }

}