package com.example.examenprimerbimestre

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "Actores_table",
    foreignKeys = [ForeignKey(entity = SerieEntity::class,
        parentColumns = arrayOf("id_Serie"),
        childColumns = arrayOf("id_Actor"),
        onDelete = ForeignKey.CASCADE)]
)
data class ActorEntity(
    @PrimaryKey(autoGenerate = true)
    var id_Actor: Int = 0,

    @ColumnInfo(name = "id_Serie")
    val id_Serie: Int = 0,

    @ColumnInfo(name = "Nombre_actor")
    val Nombre_actor: String = "",

    @ColumnInfo(name = "Apellido_actor")
    var Apellido_actor: String = "",

    @ColumnInfo(name = "Fecha_nacimiento_actor")
    var Fecha_nacimiento_actor: String = "",

    @ColumnInfo(name = "Genero_actor")
    var Genero_actor: Char = '0',

    @ColumnInfo(name = "Edad_actor")
    var Edad_actor: Int = 0
)

@Dao
interface ActorDao {

    @Insert
    fun insert_actor(actor: ActorEntity)

    @Update
    fun update_actor(actor: ActorEntity)

    @Query("SELECT * from Actores_table WHERE id_Actor = :key")
    fun get_id_actor(key: Int): LiveData<ActorEntity>?

    @Query("SELECT * from Actores_table WHERE Nombre_actor = :nombre AND Apellido_actor = :apellido")
    fun get_nombre_actor(nombre: String,apellido: String): LiveData<ActorEntity>?

    @Query("DELETE FROM Actores_table WHERE Nombre_actor = :nombre AND Apellido_actor = :apellido")
    fun delete_actor(nombre: String, apellido: String)

    @Query("DELETE FROM Actores_table")
    fun clear_actor()

    @Query("SELECT * FROM Actores_table WHERE id_Serie= :id ORDER BY id_Actor DESC")
    fun AllActores(id: Int): LiveData<List<ActorEntity>>
}