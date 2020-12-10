package funciones

import entidades.Actor
import entidades.Reparto
import entidades.Serie
import entidades.actualizarActor
import entidades.actualizarSeries
import buscarActorID
import buscarSerieID
import imprimirError
import imprimirExito

fun actualizar(
    listaSeries: ArrayList<Serie>,
    listaActores: ArrayList<Actor>,
    listaReparto: ArrayList<Reparto>
){
    println(
        "Desea actualizar: " +
                "\n1- Series" +
                "\n2- Actores"
    )
    try {

        when (readLine()?.toInt() as Int) {
            1 -> {
                opcionU1(listaSeries, listaReparto)
            }
            2 -> {
                opcionU2(listaActores, listaReparto)
            }
            else -> {
                imprimirError(0)
            }
        }
    } catch (err: Exception) {
        imprimirError(1)
    }
}

fun opcionU1(
    listaSeries: ArrayList<Serie>,
    listaReparto: ArrayList<Reparto>
){
    println("Ingrese el id de la serie a actualizar:\n")

    try {

        val entrada = readLine() as String
        val serieID: Serie? = buscarSerieID(listaSeries, entrada)
        val updateSerie: Serie?
        if (serieID != null) {
            println("Información actual de la serie:")
            println(serieID)
            updateSerie = actualizarSeries(serieID)

            listaSeries.removeIf { serie ->
                (serie.idSerie.equals(entrada))
            }
            if (updateSerie != null) {
                var repartoAux: Reparto? = null
                listaSeries.add(updateSerie)
                listaReparto.forEach { reparto ->
                    if (reparto.serie.idSerie.equals(updateSerie.idSerie)) {
                        repartoAux = reparto
                    }
                }
                listaReparto.removeIf { reparto ->
                    reparto.serie.idSerie.equals(updateSerie.idSerie)
                }
                if (repartoAux != null) {
                    listaReparto.add(
                        Reparto(
                            updateSerie,
                            repartoAux!!.actor,
                            repartoAux!!.fechaIngreso,
                            repartoAux!!.comentario
                        )
                    )
                    println(
                        "Nueva información:\n" +
                                "+${updateSerie}"
                    )
                    imprimirExito(2)
                }
            }
        } else {
            imprimirError(2)
        }
    } catch (err: Exception) {
        imprimirError(1)
    }
}

fun opcionU2(
    listaActores: ArrayList<Actor>,
    listaReparto: ArrayList<Reparto>
){
    println("Ingrese el id del actor que desea actualizar\n")
    try {

        val actor = readLine()?.toInt() as Int
        val actorID: Actor? = buscarActorID(listaActores, actor)
        val updateActor: Actor?
        if (actorID != null) {
            println("Información actual del actor:")
            println(actorID)
            updateActor = actualizarActor(actorID)
            listaActores.removeIf { actor1 ->
                (actor1.idActor == actor)
            }
            if (updateActor != null) {
                var repartoAux: Reparto? = null
                listaActores.add(updateActor)

                listaReparto.forEach { reparto ->
                    if (reparto.actor.idActor == updateActor.idActor) {
                        repartoAux = reparto
                    }
                }
                listaReparto.removeIf { reparto ->
                    reparto.actor.idActor == updateActor.idActor
                }
                listaReparto.add(
                    Reparto(
                        repartoAux!!.serie,
                        updateActor,
                        repartoAux!!.fechaIngreso,
                        repartoAux!!.comentario
                    )
                )
                //---------------------------------
                println(
                    "Nueva información:\n" +
                            "+${updateActor}"
                )
            }
        } else {
            imprimirError(2)
        }
    } catch (err: Exception) {
        imprimirError(1)
    }
}