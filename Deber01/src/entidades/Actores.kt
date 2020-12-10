package entidades

import imprimirError
import java.io.*
import java.util.*
import kotlin.collections.ArrayList

fun cargarActores(): ArrayList<Actor> {

    val archivoActores: File?
    var frActores: FileReader? = null
    val brActores: BufferedReader?
    val listaActores = ArrayList<Actor>()


    try {
        archivoActores = File("actores.csv")
        frActores = FileReader(archivoActores)
        brActores = BufferedReader(frActores)

        var linea: String?
        while (brActores.readLine().also { linea = it } != null) {
            var auxLin = 0

            var idActor = 0
            var nombreActor = ""
            var generoActor = ' '
            var edadActor = 0.0
            var experienciaActor = 0.0


            val tokens = StringTokenizer(linea, ",")

            while (tokens.hasMoreTokens()) {
                when (auxLin) {
                    0 -> {
                        idActor = tokens.nextToken().toInt()
                    }
                    1 -> {
                        nombreActor = tokens.nextToken()
                    }
                    2 -> {
                        generoActor = tokens.nextToken().toCharArray()[0]
                    }
                    3 -> {
                        edadActor = tokens.nextToken().toDouble()
                    }
                    4 -> {
                        experienciaActor = tokens.nextToken().toDouble()
                    }
                }
                auxLin++
            }


            listaActores.add(
                Actor(
                    idActor,
                    nombreActor,
                    generoActor,
                    edadActor,
                    experienciaActor
                )
            )


        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        try {
            frActores?.close()
        } catch (e2: Exception) {
            e2.printStackTrace()
        }
    }
    return listaActores
}

fun registrarActor(): Actor? {
    var id = 0
    var nombre = ""
    var genero = ' '
    var edad = 0.0
    var experiencia = 0.0

    var flag = true

    try {
        println("Ingrese el id del actor")
        id = readLine()?.toInt() as Int
        println("Ingrese el nombre (Solo letras)")
        nombre = readLine() as String
        println("Ingrese el género ( M - F )")
        genero = readLine().toString()[0]
        println("Ingrese la edad (0.0)")
        edad = readLine()?.toDouble()!!
        println("Ingrese la experiencia (0.0)")
        experiencia = readLine()?.toDouble()!!

    } catch (eRead1: NumberFormatException) {
        imprimirError(1)
        flag = false
    }
    if (flag) {
        return Actor(id, nombre, genero, edad, experiencia)
    }
    return null
}

fun imprimirActores(
    listaActores: ArrayList<Actor>
) {
    listaActores.forEach { actor ->
        println("Valor: $actor")
    }
}

fun actualizarActor(
    actor: Actor?
): Actor? {
    var nombre = ""
    var genero = ' '
    var edad = 0.0
    var experiencia = 0.0

    var flag = true

    try {
        println("Ingrese el nuevo nombre (Solo letras)")
        nombre = readLine() as String
        println("Ingrese el nuevo género ( M - F )")
        genero = readLine().toString()[0]
        println("Ingrese la nueva edad (0.0)")
        edad = readLine()?.toDouble()!!
        println("Ingrese a nueva experiencia (0.0)")
        experiencia = readLine()?.toDouble()!!

    } catch (eRead1: NumberFormatException) {
        imprimirError(1)
        flag = false
    }
    if (flag) {
        if (actor != null) {
            return Actor(actor.idActor, nombre, genero, edad, experiencia)
        }
    }
    return null
}

fun guardarActores(
    listaActores: ArrayList<Actor>
) {
    var stringActor = ""
    listaActores.forEach { actor ->
        stringActor = stringActor + "" +
                "${actor.idActor}," +
                "${actor.nombreActor}," +
                "${actor.generoActor}," +
                "${actor.edadActor}," +
                "${actor.experienciaActor}\n"
    }
    var fWriter: FileWriter? = null
    try {
        fWriter =
            FileWriter("actores.csv")
        val bfwriter = BufferedWriter(fWriter)

        bfwriter.write(stringActor)
        bfwriter.close()
        println("Archivo de actores actualizado")
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        if (fWriter != null) {
            try {
                fWriter.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}

class Actor(
    val idActor: Int,
    val nombreActor: String,
    val generoActor: Char,
    val edadActor: Double,
    val experienciaActor: Double
) {
    override fun toString(): String {
        return "\n" +
                "\tId:\t$idActor\n" +
                "\tNombre completo:  \t$nombreActor\n" +
                "\tGenero:  \t$generoActor\n" +
                "\tEdad:\t$edadActor\n" +
                "\tExperiencia:  \t$experienciaActor\n"
    }
}