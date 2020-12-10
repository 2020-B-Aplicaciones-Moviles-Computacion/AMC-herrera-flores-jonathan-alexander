package funciones

import entidades.Actor
import entidades.Reparto
import entidades.Serie
import buscarActorID
import buscarSerieID
import imprimirError
import imprimirExito
import entidades.registrarActor
import entidades.registrarRepartos
import entidades.registrarSerie
import java.util.*
import kotlin.collections.ArrayList

fun crear(
    listaSeries: ArrayList<Serie>,
    listaActores: ArrayList<Actor>,
    listaReparto: ArrayList<Reparto>
){
    println(
        "Desea Ingresar: " +
                "\n1- Series" +
                "\n2- Actores" +
                "\n3- entidades.Reparto"
    )
    try {
        when (readLine()?.toInt() as Int) {
            1 -> {
                opcionC1(listaSeries)
            }
            2 -> {
                opcionC2(listaActores)
            }
            3 -> {
                opcionC3(listaSeries, listaActores, listaReparto)
            }
            else -> {
                imprimirError(0)
            }
        }
    } catch (err: Exception) {
        imprimirError(1)
    }

}

fun opcionC1(
    listaSeries: ArrayList<Serie>
){
    val serieAux: Serie? = registrarSerie()
    if (serieAux != null) {
        listaSeries.add(serieAux)
        imprimirExito(0)
    }
}

fun opcionC2(
    listaActores: ArrayList<Actor>
){
    val actorAux: Actor? = registrarActor()
    if (actorAux != null) {
        listaActores.add(actorAux)
        imprimirExito(0)
    }
}

fun opcionC3(
    listaSeries: ArrayList<Serie>,
    listaActores: ArrayList<Actor>,
    listaReparto: ArrayList<Reparto>
){
    val reparto:String
    val serieID: Serie?
    var actorID: Actor?
    val actores: String
    println("Ingrese el id de la serie a la que desea registrar el reparto")
    try {
        reparto = readLine() as String
        serieID = buscarSerieID(listaSeries, reparto)
        println("Ingrese el id del o los actores que desea registrar en el reparto, separados por comas")
        try {
            actores = readLine() as String
            val tokActores = StringTokenizer(actores, ",")
            val listaActoresAux = ArrayList<Actor>()
            while(tokActores.hasMoreTokens()){
                actorID = buscarActorID(listaActores, Integer.parseInt(tokActores.nextElement().toString()))
                if (actorID != null) {
                    listaActoresAux.add(actorID)
                } else {
                    imprimirError(2)
                }
            }
            if (serieID != null) {
                listaReparto.add(registrarRepartos(serieID, listaActoresAux))
                imprimirExito(0)
            } else {
                imprimirError(2)
            }
        } catch (err: Exception){
            imprimirError(1)
        }
    } catch (err: Exception) {
        imprimirError(1)
    }
}