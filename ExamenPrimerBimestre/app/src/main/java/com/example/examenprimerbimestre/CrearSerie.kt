package com.example.examenprimerbimestre

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.*


class CrearSerie : AppCompatActivity() {
    private lateinit var SDao: SerieDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_serie)

        var db: Database.SeriesDatabase = Database.SeriesDatabase.getInstance(this)

        SDao = db.serieDao
        val button = findViewById<Button>(R.id.btnCreacionSerie)
        val nombre = findViewById<TextInputLayout>(R.id.tinNombreSerie)
        val clas = findViewById<TextInputLayout>(R.id.tinClasificacion)
        val aire = findViewById<TextInputLayout>(R.id.tinAlAire)
        var aireB: Boolean = false

        button.setOnClickListener {
            var bandera = 0

            if (nombre.editText?.text.toString() == "") {
                nombre.boxBackgroundColor = Color.RED
                nombre.hint = "Ingrese un nombre"
                bandera = 0
            } else {
                nombre.boxBackgroundColor = Color.rgb(224, 224, 224)
                bandera++
            }

            when {
                aire.editText?.text.toString() == "S" || aire.editText?.text.toString() == "N" -> {
                    aireB = aire.editText?.text.toString() == "S"
                    bandera++
                    aire.boxBackgroundColor = Color.rgb(224, 224, 224)
                }
                else -> {
                    aire.boxBackgroundColor = Color.RED
                    aire.hint = "Sólo se permite S o N"
                    bandera = 0
                }
            }

            when {
                clas.editText?.text.toString() == "A" -> {
                    clas.boxBackgroundColor = Color.rgb(224, 224, 224)
                    bandera++
                }
                clas.editText?.text.toString() == "B" -> {
                    clas.boxBackgroundColor = Color.rgb(224, 224, 224)
                    bandera++
                }
                clas.editText?.text.toString() == "C" -> {
                    clas.boxBackgroundColor = Color.rgb(224, 224, 224)
                    bandera++
                }
                clas.editText?.text.toString() == "D" -> {
                    clas.boxBackgroundColor = Color.rgb(224, 224, 224)
                    bandera++
                }
                clas.editText?.text.toString() == "E" -> {
                    clas.boxBackgroundColor = Color.rgb(224, 224, 224)
                    bandera++
                }
                clas.editText?.text.toString() == "F" -> {
                    clas.boxBackgroundColor = Color.rgb(224, 224, 224)
                    bandera++
                }
                else -> {
                    clas.boxBackgroundColor = Color.RED
                    clas.hint = "Sólo se permiten letras de la A a la F"
                    bandera = 0
                }
            }
            if (bandera == 3) {
                val serie = SerieEntity(
                    Nombre_serie = nombre.editText?.text.toString(),
                    Al_aire_serie = aireB,
                    Clasificacion_serie = clas.editText?.text.toString()[0]
                )
                GlobalScope.launch(Dispatchers.IO) {
                    SDao.insert_serie(serie)
                }
                this.finish()
            }
        }


    }

}