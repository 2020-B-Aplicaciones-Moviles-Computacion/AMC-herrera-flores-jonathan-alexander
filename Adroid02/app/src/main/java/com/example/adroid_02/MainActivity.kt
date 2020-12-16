package com.example.adroid_02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    val CODIGO_ACTUALIZAR_DATOS = 102
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

            val liga = DLiga("Kanto","Pokemon")
            val entrenador = BEntrenador(
                "Ash",
                "pueblo paleta",
                liga
            )


            val parametros = arrayListOf<Pair<String,*>>(
                Pair("nombre","Jonathan"),
                Pair("apellido","Herrera"),
                Pair("edad",26),
                Pair("ash",entrenador)
            )
            irActividad(CIntentExplicitoParametros::class.java,parametros,CODIGO_ACTUALIZAR_DATOS)
        }

        val buttonIntentParametros = findViewById<Button>(R.id.button_ir_intent_respuesta)
        buttonIntentParametros.setOnClickListener{
            irActividad(FIntentConRespuesta::class.java)
        }

        EBaseDeDatos.TablaUsuario = ESqliteHelperUsuario(this)
        val usuarioEncontrado = EBaseDeDatos?.TablaUsuario?.consultarUsuarioPorId(1)
        Log.i("bdd","id:${usuarioEncontrado?.id} Nombre:${usuarioEncontrado?.nombre} " +
                "Descripcion:${usuarioEncontrado?.descripcion}")
        if(usuarioEncontrado?.id == 0){
            val resultado = EBaseDeDatos.TablaUsuario?.crearUsuario("Jonathan","Estudiante")
            if(resultado != null){
                if(resultado){
                    Log.i("bdd","Se creo correctamente")
                } else {
                    Log.i("bdd","Hubo errores")
                }
            }
        } else {
            val resultado = EBaseDeDatos.TablaUsuario?.actualizarUsuario("Jonathan", Date().time.toString(), 1)
            if (resultado != null){
                if(resultado){
                    Log.i("bdd","Se actualizó")
                } else {
                    Log.i("bdd", "Errores")
                }
            }
        }
    }

    fun irActividad(
        clase: Class<*>,
        parametros:ArrayList<Pair<String,*>>? = null,
        codigo: Int? = null
    ){
        val intentExplicito = Intent(
            this,
            clase
        )
        parametros?.forEach {
            var nombreVariable = it.first
            var valorVariable: Any? = it.second

            when (it.second) {
                is Int -> {
                    valorVariable as Int
                    intentExplicito.putExtra(nombreVariable, valorVariable)
                }
                is Parcelable -> {
                    valorVariable as Parcelable
                    intentExplicito.putExtra(nombreVariable, valorVariable)
                }
                else -> {
                    valorVariable as String
                    intentExplicito.putExtra(nombreVariable, valorVariable)
                }
            }


        }

        if(codigo != null){
            startActivityForResult(intentExplicito,codigo)
        } else {
            startActivity(intentExplicito)
        }
    }

    override fun onActivityResult(
            requestCode: Int,
            resultCode: Int,
            data: Intent?
    ){
        super.onActivityResult(requestCode,resultCode,data)

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