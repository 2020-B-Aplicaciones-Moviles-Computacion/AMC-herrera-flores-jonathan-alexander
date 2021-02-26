package com.example.firebase_example.dto

import com.google.firebase.Timestamp
import java.util.*

data class FirestoreOrdenDto(
    var fecha: Timestamp?,
    var restaurante : HashMap<*,*>?,
    var usuario: HashMap<*,*>?,
    var tiposComida: ArrayList<String>?,
    var review: Long?
    ){

    override fun toString():String{
        return "Restaurante: ${restaurante?.get("uid")}\nReview: ${review}"
    }
}
