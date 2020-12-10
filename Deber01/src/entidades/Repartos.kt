package entidades

import buscarActorID
import buscarSerieID
import java.io.*
import java.util.*
import kotlin.collections.ArrayList

fun cargarRepartos(
    listaSeries: ArrayList<Serie>,
    listaActores: ArrayList<Actor>
): ArrayList<Reparto> {
    val archivoRepartos: File?
    var frRepartos: FileReader? = null
    val brRepartos: BufferedReader?
    val listaRepartos = ArrayList<Reparto>()


    try {
        archivoRepartos = File("repartos.csv")
        frRepartos = FileReader(archivoRepartos)
        brRepartos = BufferedReader(frRepartos)
        var linea: String?
        while (brRepartos.readLine().also { linea = it } != null) {
            var lineaAux = 0
            var fechaAux = 0

            var diaReparto = 0
            var anioReparto = 0
            var mesReparto = 0

            var cargaSerie: Serie? = null
            var cargaActor: Actor? = null
            var cargaFechaIngreso = Date(2000, 1, 1)
            var cargaComentario = ""

            val tokens = StringTokenizer(linea, ",")

            while (tokens.hasMoreTokens()) {
                when (lineaAux) {
                    0 -> {
                        cargaSerie = buscarSerieID(listaSeries, tokens.nextToken())
                    }
                    1 -> {
                        cargaActor = buscarActorID(listaActores, tokens.nextToken().toInt())
                    }
                    2 -> {
                        val tokFecha = StringTokenizer(tokens.nextToken(), "/")
                        while (tokFecha.hasMoreTokens()) {
                            when (fechaAux) {
                                0 -> {
                                    anioReparto = tokFecha.nextToken().toInt()
                                }
                                1 -> {
                                    mesReparto = tokFecha.nextToken().toInt()
                                }
                                2 -> {
                                    diaReparto = tokFecha.nextToken().toInt()
                                }
                            }
                            fechaAux++
                        }
                        cargaFechaIngreso = Date(anioReparto, mesReparto, diaReparto)
                    }
                    3 -> {
                        cargaComentario = tokens.nextToken()

                    }
                }
                lineaAux++
            }
            if (cargaSerie != null) {
                if (cargaActor != null) {
                    listaRepartos.add(
                        Reparto(
                            cargaSerie,
                            cargaActor,
                            cargaFechaIngreso,
                            cargaComentario
                        )
                    )
                }
            }

        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        try {
            frRepartos?.close()
        } catch (e2: Exception) {
            e2.printStackTrace()
        }
    }
    return listaRepartos
}

fun registrarRepartos(
    serie: Serie,
    listaActores: ArrayList<Actor>
): ArrayList<Reparto> {
    var comentario: String
    val comentarioIngreso: String
    try {
        println(
            "Ingrese algún comentario sobre el reparto, o puede dejar en blanco:"
        )
        comentarioIngreso = readLine() as String
        comentario = comentarioIngreso
    } catch (e: NumberFormatException) {
        comentario = ""
    }
    val listaReparto = ArrayList<Reparto>()
    listaActores.forEach{actor->
        listaReparto.add(
            Reparto(
                serie,
                actor,
                Date(),
                comentario
            )
        )
    }
    return listaReparto
}

fun guardarRepartos(
    listaRepartos: ArrayList<Reparto>
) {
    var stringSeries = ""
    listaRepartos.forEach { reparto ->
        stringSeries = stringSeries + "" +
                "${reparto.serie.idSerie}," +
                "${reparto.actor.idActor}," +
                "${reparto.fechaIngreso.year}/${reparto.fechaIngreso.month}/${reparto.fechaIngreso.date}," +
                "${reparto.comentario}\n"
    }
    //---------------------------------------------------------------------------------
    var flwriter: FileWriter? = null
    try {
        flwriter = FileWriter("repartos.csv")
        val bfwriter = BufferedWriter(flwriter)

        bfwriter.write(stringSeries)
        bfwriter.close()
        println("Archivo de repartos actualizado")
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        if (flwriter != null) {
            try {
                flwriter.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}

class Reparto(
    val serie: Serie,
    val actor: Actor,
    val fechaIngreso: Date,
    val comentario: String

) {
    override fun toString(): String {
        return "\n" +
                "\tentidades.Serie:    ${serie.nombreSerie} ${serie.idSerie}\n" +
                "\tentidades.Actor:       ${actor.nombreActor} ${actor.idActor}\n" +
                "\tFecha de ingreso: ( ${fechaIngreso.year} / ${fechaIngreso.month} / ${fechaIngreso.date} )\n" +
                "\tComentario: $comentario"
    }
}