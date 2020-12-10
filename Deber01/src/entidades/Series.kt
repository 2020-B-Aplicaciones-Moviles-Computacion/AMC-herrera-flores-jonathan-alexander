package entidades

import imprimirError
import java.io.*
import java.util.*
import kotlin.collections.ArrayList

fun cargarSeries(): ArrayList<Serie> {
    val archivoSeries: File?
    var frSeries: FileReader? = null
    val brSeries: BufferedReader?
    val listaSeries: ArrayList<Serie> = ArrayList()


    try {
        archivoSeries = File("series.csv")
        frSeries = FileReader(archivoSeries)
        brSeries = BufferedReader(frSeries)

        var linea: String?
        while (brSeries.readLine().also { linea = it } != null) {
            var auxLin = 0
            var auxFecha = 0

            var diaSerie = 0
            var anioSerie = 0
            var mesSerie = 0

            var cargaID = ""
            var cargaNombre = ""
            var cargaClasificacion = ' '
            var cargaFecha = Date(2000, 1, 1)
            var cargaAire = true

            val tokens = StringTokenizer(linea, ",")

            while (tokens.hasMoreTokens()) {
                when (auxLin) {
                    0 -> {
                        cargaID = tokens.nextToken()
                    }
                    1 -> {
                        cargaNombre = tokens.nextToken()
                    }
                    2 -> {
                        cargaClasificacion = tokens.nextToken().toCharArray()[0]
                    }
                    3 -> {
                        val tokFecha = StringTokenizer(tokens.nextToken(), "/")
                        while (tokFecha.hasMoreTokens()) {
                            when (auxFecha) {
                                0 -> {

                                    anioSerie = tokFecha.nextToken().toInt()
                                }
                                1 -> {
                                    mesSerie = tokFecha.nextToken().toInt()
                                }
                                2 -> {
                                    diaSerie = tokFecha.nextToken().toInt()
                                }
                            }
                            auxFecha++
                        }
                        cargaFecha = Date(anioSerie, mesSerie, diaSerie)
                    }
                    4 -> {
                        val a = tokens.nextToken()
                        if (a == "true") {
                            cargaAire = true
                        } else if (a == "false") {
                            cargaAire = false
                        }

                    }
                }
                auxLin++
            }

            listaSeries.add(
                Serie(
                    cargaID,
                    cargaNombre,
                    cargaClasificacion,
                    cargaFecha,
                    cargaAire
                )
            )


        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        try {
            frSeries?.close()
        } catch (e2: Exception) {
            e2.printStackTrace()
        }
    }
    return listaSeries
}

fun registrarSerie(): Serie? {
    var idSerie: String? = ""
    var nombreSerie: String? = ""
    var clasificacionSerie: Char? = null
    val fechaInicioSerie: String?

    var flag = true
    var fechaAux = 0

    var diaSerie = 0
    var mesSerie = 0
    var anioSerie = 0
    try {
        println("Ingrese el id de la serie")
        idSerie = readLine() as String
        println("Ingrese el nombre de la serie (Solo letras)")
        nombreSerie = readLine() as String
        println("Ingrese la clasificación de la serie ( A - B - C )")
        clasificacionSerie = readLine()?.toCharArray()?.get(0)
        println("Ingrese la fecha de inicio de la serie aaaa/mm/dd")
        fechaInicioSerie = readLine()
        println("Por defecto la serie registrada estará como \"Al aire\"")


        val tokFecha = StringTokenizer(fechaInicioSerie, "/")
        while (tokFecha.hasMoreTokens()) {
            when (fechaAux) {
                0 -> {
                    anioSerie = tokFecha.nextToken().toInt()
                }
                1 -> {
                    mesSerie = tokFecha.nextToken().toInt()
                }
                2 -> {
                    diaSerie = tokFecha.nextToken().toInt()
                }
            }
            fechaAux++
        }


    } catch (eRead1: NumberFormatException) {
        imprimirError(1)
        flag = false
    }
    if (flag) {
        return Serie(
            idSerie,
            nombreSerie,
            clasificacionSerie,
            Date(anioSerie, mesSerie, diaSerie),
            true
        )
    }
    return null
}

fun imprimirSeries(
    listaSeries: ArrayList<Serie>
) {
    listaSeries.forEach { serie ->
        println("Valor: $serie")
    }
}

fun actualizarSeries(
    serie: Serie?
): Serie? {
    var nombreSerie: String? = ""
    var clasificacionSerie: Char? = null
    val fechaInicioSerie: String?

    val alAireSerie: String?
    var alAire = false

    var flag = true
    var fechaAux = 0

    var diaSerie = 0
    var mesSerie = 0
    var anioSerie = 0
    try {
        println("Ingrese el nuevo nombre de la serie (Solo letras)")
        nombreSerie = readLine() as String
        println("Ingrese la clasificación de la serie ( A - B - C )")
        clasificacionSerie = readLine()?.toCharArray()?.get(0)
        println("Ingrese la fecha de inicio de la serie aaaa/mm/dd")
        fechaInicioSerie = readLine()

        val tokFecha = StringTokenizer(fechaInicioSerie, "/")
        while (tokFecha.hasMoreTokens()) {
            when (fechaAux) {
                0 -> {
                    anioSerie = tokFecha.nextToken().toInt()
                }
                1 -> {
                    mesSerie = tokFecha.nextToken().toInt()
                }
                2 -> {
                    diaSerie = tokFecha.nextToken().toInt()
                }
            }
            fechaAux++
        }

        println(
            "¿Desea cambiar el estado de la serie?\n" +
                    "Estado actual: ${serie?.alAireSerie}\n" +
                    "Ingrese 1 si desea Activarlo\n" +
                    "Ingrese 0 si desea Desactivarlo"
        )
        alAireSerie = readLine()
        if (alAireSerie.equals("1")) {
            alAire = true
        }

    } catch (eRead1: NumberFormatException) {
        imprimirError(1)
        flag = false
    }
    if (flag) {
        return Serie(
            serie?.idSerie,
            nombreSerie,
            clasificacionSerie,
            Date(anioSerie, mesSerie, diaSerie),
            alAire
        )
    }
    return null
}

fun guardarSeries(
    listaSeries: ArrayList<Serie>
) {
    var stringSeries = ""
    listaSeries.forEach { serie ->
        stringSeries = stringSeries + "" +
                "${serie.idSerie}," +
                "${serie.nombreSerie}," +
                "${serie.clasificacionSerie}," +
                "${serie.fechaInicioSerie?.year}/${serie.fechaInicioSerie?.month}/${serie.fechaInicioSerie?.date}," +
                "${serie.alAireSerie}\n"
    }
    var flwriter: FileWriter? = null
    try {
        flwriter =
            FileWriter("series.csv")
        val bfwriter = BufferedWriter(flwriter)

        bfwriter.write(stringSeries)
        bfwriter.close()
        println("Archivo de series actualizado")
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

class Serie(
    val idSerie: String?,
    val nombreSerie: String?,
    val clasificacionSerie: Char?,
    val fechaInicioSerie: Date?,
    val alAireSerie: Boolean?

) {

    override fun toString(): String {
        return "\n\tId: \t\t$idSerie\n" +
                "\tNombre: \t$nombreSerie\n" +
                "\tClasificación: \t$clasificacionSerie\n" +
                "\tFecha de Inicio:  ( ${fechaInicioSerie?.year} / ${fechaInicioSerie?.month} / ${fechaInicioSerie?.date} )\n" +
                "\tTransmitiendo: \t$alAireSerie\n"
    }
}