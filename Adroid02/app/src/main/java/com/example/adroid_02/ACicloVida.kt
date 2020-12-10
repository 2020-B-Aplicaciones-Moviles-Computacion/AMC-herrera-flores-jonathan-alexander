package com.example.adroid_02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class ACicloVida : AppCompatActivity() {
    var total = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a_ciclo_vida)
        val botonSumar = findViewById<Button>(R.id.button_ciclo_vida)
        val textoTotal = findViewById<TextView>(R.id.textView_cicloVida)

        botonSumar
            .setOnClickListener{
                total = total + 1
                textoTotal.text = total.toString()
            }
        Log.i("ciclo-vida","onCreate")
    }

    override fun onSaveInstanceState(outState: Bundle){
        Log.i("ciclo-vida","onSaveInstanceState")
        if(outState != null){
            outState.run{
                //Aqui guardamos
                //Se puede guardar cualquier primitivo
                putInt("totalGuardado",total)
            }
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.i("ciclo-vida","onRestoresInstanceState")
        val totalRecuperado:Int? = savedInstanceState.getInt("totalGuardado")
        if (totalRecuperado != null){
            this.total = totalRecuperado

            val textoTotal = findViewById<TextView>(R.id.textView_cicloVida)
            textoTotal.text = total.toString()
        }
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onStart(){
        super.onStart()
        Log.i("ciclo-vida","onStart")
    }

    override fun onRestart(){
        super.onRestart()
        Log.i("ciclo-vida","onRestart")
    }

    override fun onResume(){
        super.onResume()
        Log.i("ciclo-vida","onResume")
    }

    override fun onPause(){
        super.onPause()
        Log.i("ciclo-vida","onPause")
    }

    override fun onStop(){
        super.onStop()
        Log.i("ciclo-vida","onStop")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.i("ciclo-vida","onDestroy")
    }
}