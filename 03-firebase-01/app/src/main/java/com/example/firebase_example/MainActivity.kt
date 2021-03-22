package com.example.firebase_example

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.firebase_example.dto.FirestoreUsuarioDto
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

const val RC_SIGN_IN = 102
const val textoNoLogeado = "Dale clic al botón ingresar"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val botonIngresar = findViewById<Button>(R.id.btn_ingresar)
        botonIngresar
            .setOnClickListener {
                solicitarIngresar()
            }

        val botonSalir = findViewById<Button>(R.id.btn_salir)
        botonSalir
            .setOnClickListener {
                solicitarSalir()
            }
        val texto = findViewById<TextView>(R.id.textView)
        val instanciaAuth = FirebaseAuth.getInstance()
        if (instanciaAuth.currentUser != null) {
            texto.text = "Bienvenido ${instanciaAuth.currentUser?.email}"
            setearUsuarioFirebase()
            mostrarBotonesOcultos()
        } else {
            texto.text = textoNoLogeado
        }

        val botonFirestore = findViewById<Button>(R.id.btn_firestore)

        botonFirestore
            .setOnClickListener {
                irActividad(
                    BFirestore::class.java
                )
            }

        val btnObtenerImagen = findViewById<Button>(R.id.btn_imagen)
        btnObtenerImagen
            .setOnClickListener{
                irActividad(EImagenes::class.java)
            }

        val btnMapa = findViewById<Button>(R.id.btn_irMapa)
        btnMapa
            .setOnClickListener{
                irActividad(ContenedorMaps::class.java)
            }

        val btnFragmento = findViewById<Button>(R.id.btn_irFragmento)
        btnFragmento
            .setOnClickListener{
                irActividad(FFragmento::class.java)
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

    fun solicitarIngresar() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTosAndPrivacyPolicyUrls(
                    "https://example.com/terms.html",
                    "https://example.com/privacy.html"
                )
                .setLogo(R.drawable.logo)
                .build(),
            RC_SIGN_IN
        )
    }

    fun solicitarSalir() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                val texto = findViewById<TextView>(R.id.textView)
                texto.text = textoNoLogeado

                BAuthUsuario.usuario = null
                mostrarBotonesOcultos()

                val textoRoles = findViewById<TextView>(R.id.txt_roles)
                textoRoles.text = ""

                Log.i("firebase-login", "Salió de la app")
            }

    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int, data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            RC_SIGN_IN -> {
                if (resultCode == Activity.RESULT_OK) {
                    val usuario = IdpResponse.fromResultIntent(data)

                    if(usuario?.isNewUser == true){
                        if(usuario?.email != null){
                            val db = Firebase.firestore

                            val rolesUsuario = arrayListOf("usuario")
                            val nuevoUsuario = hashMapOf<String, Any>(
                                "roles" to rolesUsuario
                            )
                            val identificadorUsuario = usuario.email.toString()

                            db.collection("usuario")
                                .document(identificadorUsuario)
                                .set(nuevoUsuario)
                                .addOnSuccessListener {
                                    //BAuthUsuario.usuario?.roles = rolesUsuario

                                    Log.i("firebase-firestore","Se creó")
                                }
                                .addOnFailureListener{
                                    Log.i("firebase-firestore","Falló")
                                }
                        }
                    }
                    val texto = findViewById<TextView>(R.id.textView)

                    texto.text = "Bienvenido ${usuario?.email}"

                    //mostrarRolesEnPantalla()
                    setearUsuarioFirebase()
                    mostrarBotonesOcultos()

                } else {
                    Log.i("firebse-login", "El usuario cancelo")
                }
            }
        }
    }

    fun setearUsuarioFirebase() {
        val instanciaAuth = FirebaseAuth.getInstance()
        val usuarioLocal = instanciaAuth.currentUser

        if (usuarioLocal != null) {
            if (usuarioLocal.email != null) {
                val usuarioFirebase = BUsuarioFirebase(
                    usuarioLocal.uid,
                    usuarioLocal.email!!,
                    null
                )
                BAuthUsuario.usuario = usuarioFirebase
                cargarRolesUsuario(usuarioFirebase.email)
            }
        }
    }

    fun cargarRolesUsuario(uid: String){
        val db = Firebase.firestore
        val referencia = db.collection("usuario")
            .document(uid)

        referencia
            .get()
            .addOnSuccessListener {
                Log.i("firebase-firestore","Datos ${it.data}")
                val firestoreUsuario = it.toObject(FirestoreUsuarioDto::class.java)
                BAuthUsuario.usuario?.roles = firestoreUsuario?.roles
                mostrarRolesEnPantalla()
            }
            .addOnFailureListener {
                Log.i("firebase-firestore", "Falló cargar usuario")
            }
    }

    fun mostrarRolesEnPantalla(){
        var cadenaTextoRoles = ""
        BAuthUsuario.usuario?.roles?.forEach{
            cadenaTextoRoles = cadenaTextoRoles + " " + it
        }
        val textoRoles = findViewById<TextView>(R.id.txt_roles)
        textoRoles.text = cadenaTextoRoles
    }

    fun mostrarBotonesOcultos() {
        val botonEscondidoFirestore = findViewById<Button>(R.id.btn_firestore)
        if(BAuthUsuario.usuario != null){
            botonEscondidoFirestore.visibility = View.VISIBLE
        } else {
            botonEscondidoFirestore.visibility = View.INVISIBLE
        }
    }
}