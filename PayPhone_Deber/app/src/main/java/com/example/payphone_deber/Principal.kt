package com.example.payphone_deber

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView

class Principal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        val switch = findViewById<Switch>(R.id.sw_saldo)
        val saldo = findViewById<TextView>(R.id.tv_saldo)
        val tarjeta = findViewById<TextView>(R.id.tv_tarjeta)
        val imgTarjeta = findViewById<ImageView>(R.id.img_tarjeta)
        val imgSaldo = findViewById<ImageView>(R.id.img_saldo)
        val imgAgregarTarjeta = findViewById<ImageView>(R.id.img_agregarTarjeta)
        val imgRecargarSaldo = findViewById<ImageView>(R.id.img_recargarSaldo)

        switch.setOnClickListener{
            if (switch.isChecked){
                tarjeta.typeface = Typeface.DEFAULT_BOLD;
                saldo.typeface = Typeface.DEFAULT;
                imgTarjeta.visibility = VISIBLE;
                imgAgregarTarjeta.visibility = VISIBLE;
                imgSaldo.visibility = INVISIBLE;
                imgRecargarSaldo.visibility = INVISIBLE;
            } else {
                tarjeta.typeface = Typeface.DEFAULT;
                saldo.typeface = Typeface.DEFAULT_BOLD;
                imgTarjeta.visibility = INVISIBLE;
                imgAgregarTarjeta.visibility = INVISIBLE;
                imgSaldo.visibility = VISIBLE;
                imgRecargarSaldo.visibility = VISIBLE;
            }
        }

        val btnTiendas = findViewById<ImageView>(R.id.img_tiendas)
        btnTiendas.setOnClickListener{
            irActividad(Tiendas::class.java)
        }

        val btnQR = findViewById<ImageView>(R.id.img_qr)
        btnQR.setOnClickListener{
            irActividad(QR_scan::class.java)
        }

        val btnPersonas = findViewById<ImageView>(R.id.img_personas)
        btnPersonas.setOnClickListener{
            irActividad(Personas::class.java)
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