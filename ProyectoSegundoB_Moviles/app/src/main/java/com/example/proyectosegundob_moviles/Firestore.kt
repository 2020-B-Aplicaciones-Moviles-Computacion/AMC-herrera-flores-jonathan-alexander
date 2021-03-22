package com.example.proyectosegundob_moviles

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Firestore {
    companion object{
        var db = Firebase.firestore
    }
}