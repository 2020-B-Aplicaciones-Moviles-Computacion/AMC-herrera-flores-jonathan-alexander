package com.example.proyectosegundob_moviles

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.proyectosegundob_moviles.AuthUsuario.Companion.usuario
import com.example.proyectosegundob_moviles.Firestore.Companion.db
import com.example.proyectosegundob_moviles.dto.UsuarioDTO

class MenuProfile : AppCompatActivity() {
    lateinit var email: EditText
    lateinit var nombre: EditText
    lateinit var pais: EditText
    lateinit var id: EditText
    lateinit var fechaNac: EditText
    lateinit var fechaReg: EditText
    lateinit var btnEdit: ImageView
    lateinit var btnUpdate: Button
    lateinit var btnCancel: Button
    var color: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_profile)

        email = findViewById<EditText>(R.id.et_emailProf)
        nombre = findViewById<EditText>(R.id.et_nombreProf)
        pais = findViewById<EditText>(R.id.et_paisProf)
        id = findViewById<EditText>(R.id.et_idProf)
        fechaNac = findViewById<EditText>(R.id.et_fechaNacProf)
        fechaReg = findViewById<EditText>(R.id.et_fechaRegProf)

        if(usuario!=null) {
            llenarDatos()
        }

        btnUpdate = findViewById<Button>(R.id.btn_updateProf)
        btnCancel = findViewById<Button>(R.id.btn_cancelProf)
        btnEdit = findViewById<ImageView>(R.id.btn_editProf)

        color = email.currentTextColor

        btnEdit.setOnClickListener {
            permitirEdicion()
        }

        btnUpdate.setOnClickListener {
            val fechaNac = findViewById<EditText>(R.id.et_fechaNacProf)
            if(
                fechaNac.text.toString()
                    .matches(Regex("""((((0[1-9]|1[0-9]|2[0-8])[\/](0[1-9]|1[012]))|((29|30|31)[\/](0[13578]|1[02]))|((29|30)[\/](0[4,6,9]|11)))[\/](19|[2-9][0-9])\d\d${'$'})|(^29[\/]02[\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96))"""))
                || fechaNac.text.toString() == ""
            ) {
                actualizarDatos()
            } else {
                fechaNac.text = "".toEditable()
                fechaNac.hint = "Use dd/mm/yyyy"
                fechaNac.setHintTextColor(Color.parseColor("#BF4C5D"))
            }
        }

        btnCancel.setOnClickListener{
            cancelarEdicion()
        }

        findViewById<ImageView>(R.id.btn_homeProf)
            .setOnClickListener {
                finishAffinity()
                startActivity(
                    Intent(
                        this,
                        Dashboard::class.java
                    )
                )
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
    }

    fun cancelarEdicion(){
        btnEdit.visibility = View.VISIBLE
        btnUpdate.visibility = View.INVISIBLE
        btnCancel.visibility = View.INVISIBLE

        nombre.inputType = InputType.TYPE_NULL
        pais.inputType = InputType.TYPE_NULL
        id.inputType = InputType.TYPE_NULL
        fechaNac.inputType = InputType.TYPE_NULL

        email.setTextColor(color)
        fechaReg.setTextColor(color)
        nombre.setTextColor(color)
        pais.setTextColor(color)
        id.setTextColor(color)
        fechaNac.setTextColor(color)

        llenarDatos()
    }

    fun permitirEdicion() {
        nombre.inputType = InputType.TYPE_CLASS_TEXT
        pais.inputType = InputType.TYPE_CLASS_TEXT
        id.inputType = InputType.TYPE_CLASS_NUMBER
        fechaNac.inputType = InputType.TYPE_CLASS_DATETIME

        email.setTextColor(Color.parseColor("#8B94A7"))
        fechaReg.setTextColor(Color.parseColor("#8B94A7"))
        nombre.setTextColor(Color.parseColor("#244164"))
        pais.setTextColor(Color.parseColor("#244164"))
        id.setTextColor(Color.parseColor("#244164"))
        fechaNac.setTextColor(Color.parseColor("#244164"))

        btnEdit.visibility = View.INVISIBLE
        btnUpdate.visibility = View.VISIBLE
        btnCancel.visibility = View.VISIBLE
    }

    fun actualizarDatos() {

        if (usuario != null) {
            usuario?.nombre = nombre.text.toString()
            usuario?.pais = pais.text.toString()
            usuario?.identificacion = id.text.toString()
            usuario?.fechaNacimiento = fechaNac.text.toString()
            usuario?.fechaRegistro = fechaReg.text.toString()

            val usuarioActual = hashMapOf<String, Any>(
                "nombre" to usuario?.nombre.toString(),
                "identificacion" to usuario?.identificacion.toString(),
                "pais" to usuario?.pais.toString(),
                "fechaNacimiento" to usuario?.fechaNacimiento.toString(),
                "fechaRegistro" to usuario?.fechaRegistro.toString()
            )

            db
                .collection("Usuarios")
                .document(usuario!!.email).update(
                    usuarioActual
                )
                .addOnSuccessListener {
                    Log.i("firebase-firestore", "Se actualizó el usuario con éxito")
                    cancelarEdicion()
                }
                .addOnFailureListener {
                    Log.i("firebase-firestore", "Falló: $it")
                }

        }
    }

    fun llenarDatos() {
        if (usuario?.pais == null) {

            db.collection("Usuarios").document(usuario!!.email).get()
                .addOnSuccessListener {
                    val usuarioObtenido = it.toObject(UsuarioDTO::class.java)
                    usuario?.nombre = usuarioObtenido?.nombre
                    usuario?.pais = usuarioObtenido?.pais
                    usuario?.identificacion = usuarioObtenido?.identificacion
                    usuario?.fechaNacimiento = usuarioObtenido?.fechaNacimiento
                    usuario?.fechaRegistro = usuarioObtenido?.fechaRegistro!!

                    email.text = usuario?.email?.toEditable()
                    nombre.text = usuario?.nombre?.toEditable()
                    pais.text = usuario?.pais?.toEditable()
                    id.text = usuario?.identificacion?.toEditable()
                    fechaNac.text = usuario?.fechaNacimiento?.toEditable()
                    fechaReg.text = usuario?.fechaRegistro?.toEditable()
                }
        } else {
            email.text = usuario?.email?.toEditable()
            nombre.text = usuario?.nombre?.toEditable()
            pais.text = usuario?.pais?.toEditable()
            id.text = usuario?.identificacion?.toEditable()
            fechaNac.text = usuario?.fechaNacimiento?.toEditable()
            fechaReg.text = usuario?.fechaRegistro?.toEditable()
        }


    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}