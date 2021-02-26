package com.example.firebase_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.firebase_example.dto.FirestoreRestauranteDto
import com.example.firebase_example.dto.FirestoreRestauranteOrdenDto
import com.example.firebase_example.dto.FirestoreUsuarioOrdenDto
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class COrdenes : AppCompatActivity() {

    var arregloRestaurantes = arrayListOf<FirestoreRestauranteDto>()
    var adaptadorRestaurantes: ArrayAdapter<FirestoreRestauranteDto>? = null

    var arregloTiposComida = arrayListOf<String>()

    var restauranteSeleccionado: FirestoreRestauranteDto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c_ordenes)
        if(adaptadorRestaurantes == null) {
            adaptadorRestaurantes = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                arregloRestaurantes
            )
            adaptadorRestaurantes?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            cargarRestaurantes()
        }
        val botonAnadirTipocomida = findViewById<Button>(R.id.btn_addTipoComida)
        botonAnadirTipocomida
            .setOnClickListener{
                agregarTipoComida()
            }
        
        val textViewTipoComida = findViewById<TextView>(R.id.txt_tipoComida)
        textViewTipoComida.setText("")

        val btnAnadirOrden = findViewById<Button>(R.id.btn_crearOrden)
        btnAnadirOrden
            .setOnClickListener{
                crearOrden()
            }

        //eliminarDocumentosMedianteConsulta()
        //eliminacion()
        //buscarOrdenes()
        //crearDatosGrupoCol()
    }

    fun eliminacion(){
        val db = Firebase.firestore
        val docref = db
            .collection("cities")
            .document("BJ")
            .collection("landmarks")
            .document("51pnmM7macnfznklZB1L")

        val eliminarCampo = hashMapOf<String, Any>(
            "name" to FieldValue.delete()
        )
        /*docref
            .update(eliminarCampo)
            .addOnSuccessListener {
                Log.i("firebase-delete","${it}")
            }
            .addOnFailureListener {
                Log.i("firebase-delete","Error: ${it}")
            }*/

        /*docref
            .delete()
            .addOnSuccessListener {
                Log.i("firebase-delete","${it}")
            }
            .addOnFailureListener {
                Log.i("firebase-delete","Error: ${it}")
            }*/

    }

    fun eliminarDocumentosMedianteConsulta(){
        //Buscar ordenes review >= 3 y eliminar esos documentos
        val db = Firebase.firestore
        db
            .collection("orden")
            .whereGreaterThanOrEqualTo("review",5)
            .get()
            .addOnSuccessListener{
                for (orden in it){
                    Log.i("firebase-consultas", "${orden.id} ${orden.data}")
                    val deleteRef = db
                        .collection("orden")
                        .document(orden.id)

                    deleteRef
                        .delete()
                        .addOnSuccessListener {
                            Log.i("firebase-delete","${it}")
                        }
                        .addOnFailureListener {
                            Log.i("firebase-delete","Error: ${it}")
                        }
                }

            }
            .addOnFailureListener{
                Log.i("firebase-consultas",": ${it}")
            }
    }

    fun buscarOrdenes(){
        val db = Firebase.firestore
        val referencia = db.collection("orden")

        referencia
            .limit(2)
            .get()
            .addOnSuccessListener{
                for (orden in it){
                    Log.i("firebase-consultas","${orden.id} ${orden.data}")
                }
                //siguientes dos ordenes
                referencia
                    .limit(2)
                    .startAfter(it.last())
                    .get()
                    .addOnSuccessListener{
                        for (orden in it){
                            Log.i("firebase-consultas","${orden.id} ${orden.data}")
                        }
                    }
                    .addOnFailureListener{
                        Log.i("firebase-consultas","Error: $it")
                    }

            }
            .addOnFailureListener{
                Log.i("firebase-firestore","Error: $it")
            }

        /*referencia
            .whereEqualTo("restaurante.uid","KFC")
            .whereArrayContains("tiposComida","Alas")
            .get()
            .addOnSuccessListener{
                for (orden in it){
                    Log.i("firebase-consultas", "${orden.id} ${orden.data}")
                }
            }
            .addOnFailureListener{
                Log.i("firebase-consultas","Error")
            }*/

        /*referencia
            .whereEqualTo("restaurante.uid","KFC")
            .whereGreaterThanOrEqualTo("review",3)
            .orderBy("review",Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener{
                for (orden in it){
                    Log.i("firebase-consultas", "${orden.id} ${orden.data}")
                }
            }
            .addOnFailureListener{
                Log.i("firebase-consultas","Error")
            }*/

        /*referencia
            .whereEqualTo("restaurante.uid","KFC")
            .whereArrayContainsAny("tiposComida",arrayListOf("japonesa","alas"))
            .get()
            .addOnSuccessListener{
                for (orden in it){
                    Log.i("firebase-consultas", "${orden.id} ${orden.data}")
                }
            }
            .addOnFailureListener{
                Log.i("firebase-consultas","Error")
            }*/

        /*referencia
            .whereIn("restaurante.uid",arrayListOf("KFC"))
            .whereGreaterThanOrEqualTo("review",1)
            .get()
            .addOnSuccessListener{
                for (orden in it){
                    Log.i("firebase-consultas", "${orden.id} ${orden.data}")
                }
            }
            .addOnFailureListener{
                Log.i("firebase-consultas","Error")
            }*/

        /*referencia
            .whereEqualTo("review",7)
            .whereEqualTo("restaurante.nombre","KFC")
            .get()
            .addOnSuccessListener{
                for (orden in it){
                    Log.i("firebase-consultas", "${orden.id} ${orden.data}")
                }
            }
            .addOnFailureListener{
                Log.i("firebase-consultas","Error")
            }*/

        /*referencia
            .document("BJ")
            .collection("landmarks")
            .whereEqualTo("type","park")
            .get()
            .addOnSuccessListener{
                for (doc in it){
                    Log.i("firebase-consultas", "${doc.id} ${doc.data}")
                }
            }
            .addOnFailureListener{
                Log.i("firebase-consultas","Error")
            }*/

        /*db.collectionGroup("landmarks").whereEqualTo("type", "park").get()
            .addOnSuccessListener {
                for (doc in it){
                    Log.i("firebase-consultas", "${doc.id} ${doc.data}")
                }
            }
            .addOnFailureListener{
                Log.i("firebase-consultas","Error: ${it}")
            }*/

    }

    fun cargarRestaurantes(){
        val spinnerRestaurantes = findViewById<Spinner>(R.id.spn_restaurantes)

        spinnerRestaurantes.adapter = adaptadorRestaurantes
        spinnerRestaurantes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                restauranteSeleccionado = arregloRestaurantes[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Log.i("firebase-firestore", "No seleccionó nada")
            }
        }

        val db = Firebase.firestore

        val referencia = db.collection("restaurantes")
            .get()

        referencia
            .addOnSuccessListener{
                for (document in it) {
                    //Log.i("firebase-firestore", "${document.id} => ${document.data}")
                    var restaurante = document.toObject(FirestoreRestauranteDto::class.java)
                    restaurante.uid = document.id
                    arregloRestaurantes.add(restaurante)
                    adaptadorRestaurantes?.notifyDataSetChanged()
                }
            }
            .addOnFailureListener{

            }
    }

    fun agregarTipoComida(){
        val etTipoComida = findViewById<EditText>(R.id.et_tipoComida)
        val texto = etTipoComida.text.toString()
        arregloTiposComida.add(texto)
        val textViewTipoComida = findViewById<TextView>(R.id.txt_tipoComida)
        val textoAnterior = textViewTipoComida.text.toString()
        textViewTipoComida.setText("${textoAnterior}, ${texto}")
        etTipoComida.setText("")
    }

    fun crearOrden(){

        if(restauranteSeleccionado!=null){
            var restaurante = FirestoreRestauranteOrdenDto(restauranteSeleccionado?.nombre.toString())
            val instanciaAuth = FirebaseAuth.getInstance()
            val usuario = FirestoreUsuarioOrdenDto(instanciaAuth.currentUser!!.uid)
            val editTextReview = findViewById<EditText>(R.id.et_review)

            val nuevaOrden = hashMapOf<String,Any?>(
                "restaurante" to restaurante,
                "usuario" to usuario,
                "review" to editTextReview.text.toString().toInt(),
                "tiposComida" to arregloTiposComida,
                "fechaCreacion" to Timestamp(Date())
            )

            val db = Firebase.firestore
            val referencia = db.collection("orden")
                .document()
                .set(nuevaOrden)

            referencia
                .addOnSuccessListener{

                }
                .addOnFailureListener{

                }
        }

    }

    fun crearDatosGrupoCol(){
        val db = Firebase.firestore
        val citiesRef = db.collection("cities")

        val ggbData = mapOf(
            "name" to "Golden Gate Bridge",
            "type" to "bridge"
        )
        citiesRef.document("SF").collection("landmarks").add(ggbData)

        val lohData = mapOf(
            "name" to "Legion of Honor",
            "type" to "museum"
        )
        citiesRef.document("SF").collection("landmarks").add(lohData)

        val gpData = mapOf(
            "name" to "Griffth Park",
            "type" to "park"
        )
        citiesRef.document("LA").collection("landmarks").add(gpData)

        val tgData = mapOf(
            "name" to "The Getty",
            "type" to "museum"
        )
        citiesRef.document("LA").collection("landmarks").add(tgData)

        val lmData = mapOf(
            "name" to "Lincoln Memorial",
            "type" to "memorial"
        )
        citiesRef.document("DC").collection("landmarks").add(lmData)

        val nasaData = mapOf(
            "name" to "National Air and Space Museum",
            "type" to "museum"
        )
        citiesRef.document("DC").collection("landmarks").add(nasaData)

        val upData = mapOf(
            "name" to "Ueno Park",
            "type" to "park"
        )
        citiesRef.document("TOK").collection("landmarks").add(upData)

        val nmData = mapOf(
            "name" to "National Musuem of Nature and Science",
            "type" to "museum"
        )
        citiesRef.document("TOK").collection("landmarks").add(nmData)

        val jpData = mapOf(
            "name" to "Jingshan Park",
            "type" to "park"
        )
        citiesRef.document("BJ").collection("landmarks").add(jpData)

        val baoData = mapOf(
            "name" to "Beijing Ancient Observatory",
            "type" to "musuem"
        )
        citiesRef.document("BJ").collection("landmarks").add(baoData)
    }
}