package com.example.adroid_02

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class ESqliteHelperUsuario(
    contexto: Context? = null
): SQLiteOpenHelper(
    contexto,
    "moviles",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptCrearTablaUsuario =
            """
                CREATE TABLE USUARIO (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre VARCHAR(50),
                    descripcion varchar(50)
                )
            """.trimIndent()
        Log.i("bdd", "Creando la tabla de usuario")
        db?.execSQL(scriptCrearTablaUsuario)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {


    }

    fun consultarUsuarioPorId(id: Int): EUsuarioBDD{
        val scriptConsultarUsuario = "SELECT * FROM USUARIO WHERE id = ${id}"
        val dbReadable = readableDatabase

        val resultado = dbReadable.rawQuery(
            scriptConsultarUsuario,
            null
        )

        val existeUsuario = resultado.moveToFirst()

        val usuarioEncontrado = EUsuarioBDD(0,"","")

        if(existeUsuario){
            Log.i("bdd","hola3")
            do{
                val id = resultado.getInt(0)
                val nombre = resultado.getString(1)
                val descripcion = resultado.getString(2)
                if(id!=null){
                    usuarioEncontrado.id = id
                }

            } while (resultado.moveToNext())
        }
        resultado.close()
        dbReadable.close()
        return usuarioEncontrado
    }

    fun crearUsuario(
        nombre: String,
        descripcion: String
    ): Boolean{
        val conexionEscritura = writableDatabase
        val valoresAGuardar = ContentValues()

        valoresAGuardar.put("nombre",nombre)
        valoresAGuardar.put("descripcion",descripcion)

        val resultadoEscritura:Long = conexionEscritura
            .insert(
                "USUARIO",
                null,
                valoresAGuardar
            )

        conexionEscritura.close()
        return resultadoEscritura.toInt() != -1
    }

    fun actualizarUsuario(
        nombre: String,
        descripcion: String,
        idActualizar: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre",nombre)
        valoresAActualizar.put("descripcion",descripcion)
        val resultadoActualizacion = conexionEscritura
            .update(
                "USUARIO",
                valoresAActualizar,
                "id=?",
                arrayOf(idActualizar.toString())
            )
        conexionEscritura.close()
        return resultadoActualizacion.toInt() != -1
    }

    fun eliminarUsuario(
        id:Int
    ): Boolean{
        val conexionEscritura = writableDatabase
        val resultadoEliminacion = conexionEscritura
            .delete(
                "USUARIO",
                "id=?",
                arrayOf(
                    id.toString()
                )
            )
        conexionEscritura.close()
        return resultadoEliminacion.toInt() != -1
    }
}