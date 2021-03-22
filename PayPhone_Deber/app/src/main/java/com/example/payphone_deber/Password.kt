package com.example.payphone_deber

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.Button
import android.widget.Toast

class Password : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        val btnVerificar = findViewById<Button>(R.id.btn_verificar)
        btnVerificar.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Éxito")
            builder.setMessage("El código ha sido enviado exitosamente")
            //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = none))

            builder.setPositiveButton("OK") { dialog, which ->
                irActividad(IngresarCodigo::class.java)
            }

            builder.show()
        }

        val btnCancelar = findViewById<Button>(R.id.btn_cancelar)
        btnCancelar.setOnClickListener{
            finish()
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