package com.example.examenprimerbimestre

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EditarActor : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_actor)
        val id_actual = intent.getIntExtra("id_actual",0)
        val id_serie = intent.getIntExtra("id_serie",0)
        var db:Database.SeriesDatabase  = Database.SeriesDatabase.getInstance(this)

        val ADao = db.actorDao

        val nombre = findViewById<TextInputLayout>(R.id.tinModificarNombre)
        val apellido = findViewById<TextInputLayout>(R.id.tinModificarApellido)
        val fechaNac = findViewById<TextInputLayout>(R.id.tinModificarFechaNac)
        val genero = findViewById<TextInputLayout>(R.id.tinModificarGenero)
        val edad = findViewById<TextInputLayout>(R.id.tinModificarEdad)

        val actual = ADao.get_id_actor(id_actual)

        actual?.observe(this, Observer{ words ->
            // Update the cached copy of the words in the adapter.
            words?.let {
                nombre.hint = it.Nombre_actor
                apellido.hint = it.Apellido_actor
                fechaNac.hint = it.Fecha_nacimiento_actor
                genero.hint = it.Genero_actor.toString()
                edad.hint = it.Edad_actor.toString()
            }
        });

        val btnModificarActor = findViewById<Button>(R.id.btnModificarActor)
        btnModificarActor.setOnClickListener {
            var nombreT = ""
            var apellidoT = ""
            var fechaNacT = ""
            var generoT = ""
            var edadT = ""
            var bandera = 0

            if (nombre.editText?.text.toString() == "") {
                nombreT = nombre.hint.toString()
                nombre.boxBackgroundColor = Color.rgb(224, 224, 224)
                bandera++
            } else {
                nombre.boxBackgroundColor = Color.rgb(224, 224, 224)
                nombreT = nombre.editText?.text.toString()
                bandera++
            }

            if (apellido.editText?.text.toString() == "") {
                apellido.boxBackgroundColor = Color.rgb(224, 224, 224)
                apellidoT = apellido.hint.toString()
                bandera++
            } else {
                apellido.boxBackgroundColor = Color.rgb(224, 224, 224)
                apellidoT = apellido.editText?.text.toString()
                bandera++
            }

            when {
                fechaNac.editText?.text.toString() == "" -> {
                    fechaNac.boxBackgroundColor = Color.rgb(224, 224, 224)
                    fechaNacT = fechaNac.hint.toString()
                    bandera++
                }
                fechaNac.editText?.text.toString().matches(Regex("""((((0[1-9]|1[0-9]|2[0-8])[\/](0[1-9]|1[012]))|((29|30|31)[\/](0[13578]|1[02]))|((29|30)[\/](0[4,6,9]|11)))[\/](19|[2-9][0-9])\d\d${'$'})|(^29[\/]02[\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96))""")) -> {
                    fechaNac.boxBackgroundColor = Color.rgb(224, 224, 224)
                    fechaNacT = fechaNac.editText?.text.toString()
                    bandera++
                }
                else -> {
                    fechaNac.boxBackgroundColor = Color.RED
                    fechaNac.hint = "Ingrese una Fecha en el formato especificado"
                    bandera = 0
                }
            }

            if (genero.editText?.text.toString() == "") {
                generoT = genero.hint.toString()
                bandera++
            } else {
                genero.boxBackgroundColor = Color.rgb(224, 224, 224)
                generoT = genero.editText?.text.toString()
                bandera++
            }

            if (edad.editText?.text.toString() == "") {
                edadT = edad.hint.toString()
                bandera++
            } else {
                edad.boxBackgroundColor = Color.rgb(224, 224, 224)
                edadT = edad.editText?.text.toString()
                bandera++
            }

            if (bandera == 5) {
                val actor = ActorEntity(
                    id_Serie = id_serie,
                    Nombre_actor = nombreT,
                    Apellido_actor = apellidoT,
                    Fecha_nacimiento_actor = fechaNacT,
                    Genero_actor = generoT[0],
                    Edad_actor = Integer.parseInt(edadT)
                )
                GlobalScope.launch(Dispatchers.IO) {
                    ADao.update_actor(actor)
                }
                this.finish()
            }
        }
    }
}