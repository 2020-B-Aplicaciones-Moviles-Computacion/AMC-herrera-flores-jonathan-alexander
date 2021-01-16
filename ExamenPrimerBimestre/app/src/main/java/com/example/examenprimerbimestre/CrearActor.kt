package com.example.examenprimerbimestre

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CrearActor : AppCompatActivity() {
    private lateinit var ADao: ActorDao
    var id_serie = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_actor)
        id_serie = intent.getIntExtra("id_serie",0)

        var db: Database.SeriesDatabase = Database.SeriesDatabase.getInstance(this)

        ADao = db.actorDao
        val button = findViewById<Button>(R.id.btnCreacionActor)

        val nombre = findViewById<TextInputLayout>(R.id.tinNombre)
        val apellido = findViewById<TextInputLayout>(R.id.tinApellido)
        val fechaNac = findViewById<TextInputLayout>(R.id.tinFechaNacimiento)
        val genero = findViewById<TextInputLayout>(R.id.tinGenero)
        val edad = findViewById<TextInputLayout>(R.id.tinEdad)

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

            if (apellido.editText?.text.toString() == "") {
                apellido.boxBackgroundColor = Color.RED
                apellido.hint = "Ingrese un apellido"
                bandera = 0
            } else {
                apellido.boxBackgroundColor = Color.rgb(224, 224, 224)
                bandera++
            }

            when {
                fechaNac.editText?.text.toString() == "" -> {
                    fechaNac.boxBackgroundColor = Color.RED
                    fechaNac.hint = "Ingrese una Fecha de Nacimiento"
                    bandera = 0
                }
                fechaNac.editText?.text.toString().matches(Regex("""((((0[1-9]|1[0-9]|2[0-8])[\/](0[1-9]|1[012]))|((29|30|31)[\/](0[13578]|1[02]))|((29|30)[\/](0[4,6,9]|11)))[\/](19|[2-9][0-9])\d\d${'$'})|(^29[\/]02[\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96))""")) -> {
                    fechaNac.boxBackgroundColor = Color.rgb(224, 224, 224)
                    bandera++
                }
                else -> {
                    fechaNac.boxBackgroundColor = Color.RED
                    fechaNac.hint = "Ingrese una Fecha en el formato especificado"
                    bandera = 0
                }
            }

            when {
                genero.editText?.text.toString() == "M" -> {
                    genero.boxBackgroundColor = Color.rgb(224, 224, 224)
                    bandera++
                }
                genero.editText?.text.toString() == "F" -> {
                    genero.boxBackgroundColor = Color.rgb(224, 224, 224)
                    bandera++
                }
                genero.editText?.text.toString() == "O" -> {
                    genero.boxBackgroundColor = Color.rgb(224, 224, 224)
                    bandera++
                }
                else -> {
                    genero.boxBackgroundColor = Color.RED
                    genero.hint = "Sólo se permiten letras las letras M, F, O"
                    bandera = 0
                }
            }
            if (edad.editText?.text.toString() == "") {
                edad.boxBackgroundColor = Color.RED
                edad.hint = "Ingrese una edad"
                bandera = 0
            } else {
                edad.boxBackgroundColor = Color.rgb(224, 224, 224)
                bandera++
            }
            if (bandera == 5) {
                val actor = ActorEntity(
                    id_Serie = id_serie,
                    Nombre_actor = nombre.editText?.text.toString(),
                    Apellido_actor = apellido.editText?.text.toString(),
                    Fecha_nacimiento_actor = fechaNac.editText?.text.toString(),
                    Genero_actor = genero.editText?.text.toString()[0],
                    Edad_actor = Integer.parseInt(edad.editText?.text.toString())
                )
                GlobalScope.launch(Dispatchers.IO) {
                    ADao.insert_actor(actor)
                }
                this.finish()
            }
        }


    }
}