package com.example.proyectosegundob_moviles

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.proyectosegundob_moviles.AuthUsuario.Companion.usuario
import com.example.proyectosegundob_moviles.Firestore.Companion.db
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LlenarDatos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_llenar_datos)

        findViewById<Button>(R.id.btn_confirmInfo)
            .setOnClickListener {
                val fechaNac = findViewById<EditText>(R.id.et_fechaNac)
                if (
                    fechaNac.text.toString()
                        .matches(Regex("""((((0[1-9]|1[0-9]|2[0-8])[\/](0[1-9]|1[012]))|((29|30|31)[\/](0[13578]|1[02]))|((29|30)[\/](0[4,6,9]|11)))[\/](19|[2-9][0-9])\d\d${'$'})|(^29[\/]02[\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96))"""))
                    || fechaNac.text.toString() == ""
                ) {
                    guardarUsuario()

                    startActivity(
                        Intent(
                            this,
                            Dashboard::class.java
                        )
                    )
                } else {
                    fechaNac.text = "".toEditable()
                    fechaNac.hint = "Use dd/mm/yyyy"
                    fechaNac.setHintTextColor(Color.parseColor("#BF4C5D"))
                }

            }
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    fun guardarUsuario() {
        usuario?.identificacion = findViewById<EditText>(R.id.et_id).text.toString()
        usuario?.pais = findViewById<EditText>(R.id.et_pais).text.toString()
        usuario?.fechaNacimiento = findViewById<EditText>(R.id.et_fechaNac).text.toString()

        val nuevoUsuario = hashMapOf<String, Any>(
            "nombre" to usuario?.nombre.toString(),
            "identificacion" to usuario?.identificacion.toString(),
            "pais" to usuario?.pais.toString(),
            "fechaNacimiento" to usuario?.fechaNacimiento.toString(),
            "fechaRegistro" to usuario?.fechaRegistro.toString()
        )
        val identificadorUsuario = usuario?.email.toString()

        db.collection("Usuarios")
            .document(identificadorUsuario)
            .set(nuevoUsuario)
            .addOnSuccessListener {
                Log.i("firebase-firestore", "Se creó el usuario con éxito")
            }
            .addOnFailureListener {
                Log.i("firebase-firestore", "Falló: $it")
            }
    }
}