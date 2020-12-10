package com.example.adroid_02

class BEntrenador(var nombre: String, var descripcion: String) {

    override fun toString(): String{
        return "Nombre: ${this.nombre}, Descripción: ${this.descripcion}"
    }

}