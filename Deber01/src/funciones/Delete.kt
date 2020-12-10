package funciones

import entidades.Actor
import entidades.Reparto
import entidades.Serie
import buscarActorID
import buscarSerieID
import entidades.imprimirActores
import imprimirError

fun borrar(
    listaSeries: ArrayList<Serie>,
    listaActores: ArrayList<Actor>,
    listaReparto: ArrayList<Reparto>
){
    println(
        "Desea Eliminar: " +
                "\n1- Series" +
                "\n2- Actores"
    )
    try {
        when (readLine()?.toInt() as Int) {
            1 -> {
                opcionD1(listaSeries, listaReparto)
            }
            2 -> {
                opcionD2(listaActores, listaReparto)
            }
            else -> imprimirError(0)
        }
    } catch (err: Exception) {
        imprimirError(1)
    }
}

fun opcionD1(
    listaSeries: ArrayList<Serie>,
    listaReparto: ArrayList<Reparto>
){
    println("Ingrese el id de la serie a eliminar:\n")
    try {
        val ingreso = readLine() as String
        val serieID: Serie? = buscarSerieID(listaSeries, ingreso)
        val confirmacion: String?
        if (serieID != null) {
            println("Informacion de la serie a eliminar:")
            println(serieID)
            try {
                println(
                    "¿Está seguro de eliminar la serie?\n" +
                            "Ingrese 1 si está seguro\n" +
                            "Ingrese 0 si no desea eliminarla"
                )
                confirmacion = readLine()
                if (confirmacion.equals("1")) {
                    listaReparto.removeIf { reparto ->
                        reparto.serie.idSerie.equals(ingreso)
                    }
                    listaSeries.removeIf { serie ->
                        (serie.idSerie.equals(ingreso))
                    }
                } else {
                    imprimirError(3)
                }
            } catch (err: Exception) {
                imprimirError(1)
            }
        } else {
            imprimirError(2)
        }
    } catch (err: Exception) {
        imprimirError(1)
    }
}

fun opcionD2(
    listaActores: ArrayList<Actor>,
    listaReparto: ArrayList<Reparto>
){
    println("Ingrese el id del actor que desea eliminar\n")
    imprimirActores(listaActores)
    try {
        val entrada = readLine()?.toInt() as Int
        val actorID: Actor? = buscarActorID(listaActores, entrada)
        val seguro: String?
        if (actorID != null) {
            println("Información actual del actor:")
            println(actorID)
            try {
                println(
                    "¿Está seguro de eliminar el actor?\n" +
                            "Ingrese 1 si está seguro\n" +
                            "Ingrese 0 si no desea eliminarlo"
                )
                seguro = readLine()
                if (seguro.equals("1")) {
                    listaReparto.removeIf { reparto ->
                        reparto.actor.idActor == entrada
                    }
                    listaActores.removeIf { actor ->
                        (actor.idActor == entrada)
                    }
                } else {
                    imprimirError(3)
                }
            } catch (err: Exception) {
                imprimirError(1)
            }
        } else {
            imprimirError(2)
        }
    } catch (err: Exception) {
        imprimirError(1)
    }
}