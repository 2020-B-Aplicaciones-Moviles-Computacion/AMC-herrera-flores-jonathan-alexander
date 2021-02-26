package com.example.firebase_example

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import java.io.*
import java.util.*


private const val RC_TAKE_PICTURE = 1

class EImagenes : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_e_imagenes)

        val btnArchivoImagen = findViewById<Button>(R.id.btn_imagenArchivo)
        btnArchivoImagen
            .setOnClickListener {
                getArchivo()
            }

        val btnCamaraImagen = findViewById<Button>(R.id.btn_imagenCamara)
        btnCamaraImagen
            .setOnClickListener {
                getCamara()
            }

        val btnSubir = findViewById<Button>(R.id.btn_subirImagen)
        btnSubir
            .setOnClickListener {
                subirImagen()
            }

        val btnBajar = findViewById<Button>(R.id.btn_descargaImagen)
        btnBajar
            .setOnClickListener {
                bajarImagen()
            }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val imgView = findViewById<ImageView>(R.id.img_Foto)
        if (resultCode == Activity.RESULT_OK) {

            if (data?.dataString != null) {
                val dataString: String = data.dataString!!

                if (dataString != null) {
                    Picasso.get()
                        .load(dataString)
                        .into(imgView)
                }
            } else {
                val photo = data?.extras?.get("data")
                photo as Bitmap

                val wrapper = ContextWrapper(this)
                var file = wrapper.getDir("Download", Context.MODE_PRIVATE)

                file = File(file, "${UUID.randomUUID()}.jpg")
                try {
                    val stream: OutputStream = FileOutputStream(file)
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                    stream.flush()
                    stream.close()
                } catch (e: IOException){
                    e.printStackTrace()
                }

                imgView.setImageURI(Uri.parse(file.absolutePath))
            }
        }
    }

    fun getArchivo() {
        val contentSelectionIntent = Intent(Intent.ACTION_GET_CONTENT)
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE)
        contentSelectionIntent.type = "image/*"
        startActivityForResult(contentSelectionIntent, RC_TAKE_PICTURE)
    }

    fun getCamara(){
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent!!.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, RC_TAKE_PICTURE)
        }
    }

    fun subirImagen(){
        val storage = Firebase.storage

        val storageRef = storage.reference
        val imgRef = storageRef.child("${UUID.randomUUID()}.jpg")
        val imgView = findViewById<ImageView>(R.id.img_Foto)

        val bitmap = (imgView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = imgRef.putBytes(data)
        uploadTask.addOnFailureListener {
            Log.i("Firebase-Storage", "Error: ${it}")
        }.addOnSuccessListener { taskSnapshot ->
            Log.i("Firebase-Storage", "Sanpshot: ${taskSnapshot}")
        }
    }

    fun bajarImagen(){
        val storage = Firebase.storage
        val path = findViewById<TextView>(R.id.tv_path).text

        val imgView = findViewById<ImageView>(R.id.img_Foto)
        val storageRef = storage.reference
        val imgRef = storageRef.child("${path}")

        val mb1: Long = 1024 * 1024
        imgRef.getBytes(mb1).addOnSuccessListener {

            val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)

            val wrapper = ContextWrapper(this)
            var file = wrapper.getDir("Download", Context.MODE_PRIVATE)

            file = File(file, "${UUID.randomUUID()}.jpg")
            try {
                val stream: OutputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                stream.flush()
                stream.close()
            } catch (e: IOException){
                e.printStackTrace()
            }

            imgView.setImageURI(Uri.parse(file.absolutePath))

        }.addOnFailureListener {
            Log.i("Firebase-Storage", "Error: ${it}")
        }
    }
}