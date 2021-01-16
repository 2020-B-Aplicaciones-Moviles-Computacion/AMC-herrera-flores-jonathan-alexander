package com.example.examenprimerbimestre

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import androidx.lifecycle.Observer
import com.example.examenpokentrpp1.activEntren.AdaptadorSeries
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class EditarSerie : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_serie)
        val id_actual = intent.getIntExtra("id_actual",0)
        var db:Database.SeriesDatabase  = Database.SeriesDatabase.getInstance(this)
        val nombre = findViewById<TextInputLayout>(R.id.tinModificarNombreSerie)
        val clas = findViewById<TextInputLayout>(R.id.tinModificarClasificacionSerie)
        val aire = findViewById<TextInputLayout>(R.id.tinModificarAlAire)
        var aireB: Boolean = false

        var SDao = db.serieDao
        val actual = SDao.get_id_serie(id_actual)

        actual?.observe(this, Observer{ words ->
            // Update the cached copy of the words in the adapter.
            words?.let {
                nombre.hint = it.Nombre_serie
                clas.hint = it.Clasificacion_serie.toString()
                if(it.Al_aire_serie){
                    aire.hint = "S"
                } else {
                    aire.hint = "N"
                }
            }
        });

        val btnModificarSerie = findViewById<Button>(R.id.btnModificarSerie)
        btnModificarSerie.setOnClickListener {
            var nombreT= ""
            var clasT = ""
            var bandera = 0

            if (nombre.editText?.text.toString() == "") {
                nombreT = nombre.hint.toString()
                nombre.boxBackgroundColor = Color.rgb(224, 224, 224)
                bandera ++
            } else {
                nombre.boxBackgroundColor = Color.rgb(224, 224, 224)
                nombreT = nombre.editText?.text.toString()
                bandera++
            }

            when {
                aire.editText?.text.toString() == "S" || aire.editText?.text.toString() == "N" -> {
                    aireB = aire.editText?.text.toString() == "S"
                    bandera++
                    aire.boxBackgroundColor = Color.rgb(224, 224, 224)
                }
                aire.editText?.text.toString() == "" -> {
                    aireB = aire.hint.toString() == "S"
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
                clas.editText?.text.toString() == "" -> {
                    clas.boxBackgroundColor = Color.rgb(224, 224, 224)
                    clasT = clas.hint.toString()
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
                    id_Serie = id_actual,
                    Nombre_serie = nombreT,
                    Al_aire_serie = aireB,
                    Clasificacion_serie = clasT[0]
                )
                GlobalScope.launch(Dispatchers.IO) {
                    SDao.update_serie(serie)
                }
                this.finish()
            }
        }
    }
}