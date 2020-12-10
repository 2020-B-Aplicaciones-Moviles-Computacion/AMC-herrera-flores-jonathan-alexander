package com.example.adroid_02

class BBaseDatosMemoria {
    companion object {
        val arregloEntrenadores = arrayListOf<BEntrenador>()

        fun cargaInicialDatos(){
            arregloEntrenadores.add(BEntrenador("Pedro","rapido"))
            arregloEntrenadores.add(BEntrenador("Manuel","fuerte"))
            arregloEntrenadores.add(BEntrenador("Mario","lento"))
            arregloEntrenadores.add(BEntrenador("Raul","pequeño"))
            arregloEntrenadores.add(BEntrenador("Santiago","grande"))
            arregloEntrenadores.add(BEntrenador("Juan","alto"))
        }
    }
}