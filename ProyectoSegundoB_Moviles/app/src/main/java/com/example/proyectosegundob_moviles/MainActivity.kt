package com.example.proyectosegundob_moviles

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

const val RC_SIGN_IN = 1

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.AnonymousBuilder().build()
        )

        val instanciaAuth = FirebaseAuth.getInstance()
        if (instanciaAuth.currentUser != null) {
            setearUsuarioFirebase()
            irActividad((Dashboard::class.java))
            overridePendingTransition(R.anim.no_animation,R.anim.no_animation)
        } else {
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .setLogo(R.drawable.logo)
                    .setTheme(R.style.Theme_ProyectoSegundoB_Moviles)
                    .build(),
                RC_SIGN_IN
            )
        }

    }

    fun setearUsuarioFirebase() {
        val instanciaAuth = FirebaseAuth.getInstance()
        val usuarioLocal = instanciaAuth.currentUser
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        if (usuarioLocal != null) {
            if (usuarioLocal.email != null) {
                val usuarioFirebase = UsuarioLocal(
                    usuarioLocal.uid,
                    usuarioLocal.email!!,
                    usuarioLocal.displayName,
                    null,
                    null,
                    null,
                    sdf.format(Calendar.getInstance().time)
                )

                AuthUsuario.usuario = usuarioFirebase
                Log.i("debug","${AuthUsuario.usuario!!.email}")
            }
        }
    }

    fun irActividad(
        clase: Class<*>,
        parametros: ArrayList<Pair<String, *>>? = null,
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

    override fun onActivityResult(
        requestCode: Int, resultCode: Int, data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            RC_SIGN_IN -> {
                if (resultCode == Activity.RESULT_OK) {
                    setearUsuarioFirebase()
                    val usuario = IdpResponse.fromResultIntent(data)
                    val instanciaAuth = FirebaseAuth.getInstance()
                    val usuarioLocal = instanciaAuth.currentUser
                    if(usuarioLocal?.isAnonymous!!){
                        usuarioLocal?.delete()
                        irActividad(Dashboard::class.java)
                        overridePendingTransition(R.anim.no_animation,R.anim.no_animation)
                    } else if (usuario?.isNewUser == true) {
                        if (usuario.email != null) {
                            irActividad(LlenarDatos::class.java)
                            overridePendingTransition(R.anim.no_animation,R.anim.no_animation)
                        }
                    } else {
                        setearUsuarioFirebase()
                        irActividad((Dashboard::class.java))
                        overridePendingTransition(R.anim.no_animation,R.anim.no_animation)
                    }
                } else {
                    Log.i("firebase-login", "$resultCode")
                    finish()
                    overridePendingTransition(R.anim.no_animation,R.anim.no_animation)
                }
            }
        }
    }
}