package com.example.adroid_02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.result.Result

class HActividadHTTP : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_h_actividad_h_t_t_p)

        metodoGetKlaxon()
        metodoPostKlaxon()
        metodoDelKlaxon()
        metodoPutKlaxon()

    }

    fun metodoGetKlaxon() {
        "https://jsonplaceholder.typicode.com/posts/1"
            .httpGet()
            .responseString { req, res, result ->
                when (result) {
                    is Result.Failure -> {
                        val error = result.getException()
                        Log.i("http-klaxon", "Error: ${error}")
                    }
                    is Result.Success -> {
                        val postString = result.get()
                        val post = Klaxon()
                            .parse<IPostHttp>(postString)
                        Log.i("http-klaxon", "${post?.title}")
                    }
                }
            }
    }

    fun metodoPostKlaxon() {
        val parametros: List<Pair<String, String>> = listOf(
            "title" to "titulo moviles",
            "body" to "descripcion moviles",
            "userId" to "1"
        )

        "https://jsonplaceholder.typicode.com/posts"
            .httpPost(parametros)
            .responseString { req, res, result ->
                when (result) {
                    is Result.Failure -> {
                        val error = result.getException()
                        Log.i("http-klaxon", "Error: ${error}")
                    }
                    is Result.Success -> {
                        val postString = result.get()
                        val post = Klaxon()
                            .parse<IPostHttp>(postString)
                        Log.i("http-klaxon", "${post?.title}")
                    }
                }
            }
    }

    fun metodoDelKlaxon() {
        val parametros: List<Pair<String, String>> = listOf(
            "title" to "titulo moviles",
            "body" to "descripcion moviles",
            "userId" to "1",
            "id" to "1"
        )
        "https://jsonplaceholder.typicode.com/posts/1"
            .httpDelete(parametros)
            .responseString { req, res, result ->
                when (result) {
                    is Result.Failure -> {
                        val error = result.getException()
                        Log.i("http-klaxon", "Error: ${error}")
                    }
                    is Result.Success -> {
                        val postString = result.get()
                        Log.i("http-klaxon", "${postString}")
                    }
                }
            }
    }

    fun metodoPutKlaxon() {
        val parametros: List<Pair<String, String>> = listOf(
            "title" to "titulo moviles",
            "body" to "descripcion moviles",
            "userId" to "1",
            "id" to "1"
        )
        "https://jsonplaceholder.typicode.com/posts/1"
            .httpPut(parametros)
            .responseString { req, res, result ->
                when (result) {
                    is Result.Failure -> {
                        val error = result.getException()
                        Log.i("http-klaxon", "Error: ${error}")
                    }
                    is Result.Success -> {
                        val postString = result.get()
                        val post = Klaxon()
                            .parse<IPostHttp>(postString)
                        Log.i("http-klaxon", "${post?.title}")
                    }
                }
            }
    }

}