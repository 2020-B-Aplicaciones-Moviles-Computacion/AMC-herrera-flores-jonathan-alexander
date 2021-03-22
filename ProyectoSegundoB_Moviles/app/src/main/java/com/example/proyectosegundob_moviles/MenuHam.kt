package com.example.proyectosegundob_moviles

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectosegundob_moviles.AuthUsuario.Companion.usuario
import com.firebase.ui.auth.AuthUI

class MenuHam : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_ham)
        val origen = intent.getStringExtra("origen")

        if(origen == "Contact Us"){
            findViewById<Button>(R.id.btn_contact).visibility = View.INVISIBLE
        }

        findViewById<Button>(R.id.btn_cerrarSesion)
            .setOnClickListener {
                AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener {
                        usuario = null
                        finishAffinity()
                        startActivity(
                            Intent(
                                this,
                                MainActivity::class.java
                            )
                        )
                    }
            }

        findViewById<Button>(R.id.btn_contact)
            .setOnClickListener {
                startActivity(
                    Intent(
                        this,
                        ContactInformation::class.java
                    )
                )
            }

        findViewById<ImageView>(R.id.btn_homeHam)
            .setOnClickListener {
                finishAffinity()
                startActivity(
                    Intent(
                        this,
                        Dashboard::class.java
                    )
                )
            }
    }
}