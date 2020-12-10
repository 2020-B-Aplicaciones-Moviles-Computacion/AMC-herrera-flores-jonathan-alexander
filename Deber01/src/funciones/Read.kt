package funciones

import entidades.Actor
import entidades.Reparto
import entidades.Serie
import buscarSerieID
import entidades.imprimirActores
import imprimirError
import imprimirExito
import entidades.imprimirSeries

fun leer(
    listaSeries: ArrayList<Serie>,
    listaActores: ArrayList<Actor>,
    listaReparto: ArrayList<Reparto>
){
    println(
        "¿Qué desea ver?: " +
                "\n1- Series" +
                "\n2- Actores" +
                "\n3- Reparto por Serie"
    )
    try {

        when (readLine()?.toInt() as Int) {
            1 -> {
                opcionR1(listaSeries)
            }
            2 -> {
                opcionR2(listaActores)
            }
            3 -> {
                opcionR3(listaSeries, listaReparto)
            }
            else -> {
                imprimirError(0)
            }
        }
    } catch (err: Exception) {
        imprimirError(1)
    }
}

fun imprimirSerieReparto(
    listaRepartos: ArrayList<Reparto>,
    idSerie: String?
) {
    listaRepartos.forEach { reparto ->
        if (reparto.serie.idSerie.equals(idSerie)) {
            println("Valor: $reparto")
        }

    }
}

fun opcionR1(
    listaSeries: ArrayList<Serie>
){
    println("Lista Series")
    imprimirSeries(listaSeries)
    imprimirExito(1)
}

fun opcionR2(
    listaActores: ArrayList<Actor>
){
    println("Lista Actores")
    imprimirActores(listaActores)
    imprimirExito(1)
}

fun opcionR3(
    listaSeries: ArrayList<Serie>,
    listaReparto: ArrayList<Reparto>
){
    val reparto: String
    val serieID: Serie?
    println("Ingrese el id de la serie de la que desea mostrar el reparto")
    try {
        reparto = readLine() as String
        serieID = buscarSerieID(listaSeries, reparto)
        if (serieID != null) {
            imprimirSerieReparto(listaReparto, serieID.idSerie)
            imprimirExito(1)
        } else {
            imprimirError(2)
        }
    } catch (err: Exception) {
        imprimirError(1)
    }
}