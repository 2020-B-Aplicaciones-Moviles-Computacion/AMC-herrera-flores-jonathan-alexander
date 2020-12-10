import entidades.*
import funciones.crear
import funciones.leer
import funciones.actualizar
import funciones.borrar
import kotlin.collections.ArrayList
import kotlin.system.exitProcess

fun main() {

    //Creacion de listas y carga inicial
    val listaSeries: ArrayList<Serie> = cargarSeries()
    val listaActores: ArrayList<Actor> = cargarActores()
    val listaReparto: ArrayList<Reparto> =
        cargarRepartos(listaSeries, listaActores)

    var seleccion:Int


    println("Bienvenid@ al sistema de registro de series")
    do {
        println("Seleccione una opción: ")
        println(
            "1- Ingresar una nueva serie, actor o reparto (Create)\n" +
                    "2- Mostrar series, actores o reparto (Read)\n" +
                    "3- Actualizar una serie o actor (Update)\n" +
                    "4- Eliminar una serie o actor (Delete)\n" +
                    "Ingrese 5 si desea salir del programa.\n"
        )
        try {
            seleccion = readLine()?.toInt() as Int
            when (seleccion) {
                1 -> {
                    crear(listaSeries, listaActores, listaReparto)
                }
                2 -> {
                    leer(listaSeries, listaActores, listaReparto)
                }
                3 -> {
                    actualizar(listaSeries, listaActores, listaReparto)
                }
                4 -> {
                    borrar(listaSeries, listaActores, listaReparto)
                }
                5 -> {
                    guardarSeries(listaSeries)
                    guardarActores(listaActores)
                    guardarRepartos(listaReparto)
                    exitProcess(0)
                }
                else -> imprimirError(0)
            }

        } catch (e2: Exception) {
            imprimirError(1)
        }

    } while (true)
}

fun imprimirExito(opcion: Int = 0) {
    when (opcion) {
        0 -> {
            println(
                "****************\n" +
                        "Registro exitoso\n" +
                        "****************"
            )
        }
        1 -> {
            println(
                "****************\n" +
                        "Consulta exitosa\n" +
                        "****************"
            )
        }
        2 -> {
            println(
                "*********************\n" +
                        "Actualización exitosa\n" +
                        "*********************"
            )
        }
        else -> {
            println(
                "*****************\n" +
                        "Error desconocido\n" +
                        "*****************"
            )
        }
    }
    println("Presione cualquier tecla para continuar")
    readLine()
}

fun imprimirError(opcion: Int = 0) {
    when (opcion) {
        0 -> {
            println(
                "********************\n" +
                        "Opción no disponible\n" +
                        "********************"
            )
        }
        1 -> {
            println(
                "*****************************************************************************\n" +
                        "La opcion ingresada no es correcta, revise si ingresó un caracter no numérico\n" +
                        "*****************************************************************************"
            )
        }
        2 -> {
            println(
                "**********************\n" +
                        "Elemento no encontrado\n" +
                        "**********************"
            )
        }
        3 -> {
            println(
                "*******************\n" +
                        "Operación cancelada\n" +
                        "*******************"
            )
        }
        else -> {
            println(
                "*****************\n" +
                        "Error desconocido\n" +
                        "*****************"
            )
        }
    }
    println("Presione cualquier tecla para continuar")
    readLine()
}

fun buscarSerieID(
    listaSeries: ArrayList<Serie>,
    idSerie: String
): Serie? {
    var serieRespuesta: Serie? = null
    listaSeries.forEach { serie ->
        if (serie.idSerie.equals(idSerie)) {
            serieRespuesta = serie
        }
    }
    return serieRespuesta
}

fun buscarActorID(
    listaActores: ArrayList<Actor>,
    idActor: Int
): Actor? {
    var actorRespuesta: Actor? = null
    listaActores.forEach { actor ->
        if (actor.idActor == idActor) {
            actorRespuesta = actor
        }
    }
    return actorRespuesta
}