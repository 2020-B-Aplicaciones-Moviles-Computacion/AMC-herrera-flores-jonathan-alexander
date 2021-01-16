package com.example.examenprimerbimestre

import androidx.lifecycle.LiveData
import androidx.room.*


@Entity(tableName = "Series_table")
data class SerieEntity(
    @PrimaryKey(autoGenerate = true)
    var id_Serie: Int = 0,

    @ColumnInfo(name = "Nombre_serie")
    val Nombre_serie: String = "",

    @ColumnInfo(name = "Clasificacion_serie")
    var Clasificacion_serie: Char = '0',

    @ColumnInfo(name = "Al_aire_serie")
    var Al_aire_serie: Boolean = true
)

@Dao
interface SerieDao {

    @Insert
    fun insert_serie(serie: SerieEntity)

    @Update
    fun update_serie(serie: SerieEntity)

    @Query("SELECT * from Series_table WHERE id_Serie = :key")
    fun get_id_serie(key: Int): LiveData<SerieEntity>?

    @Query("DELETE FROM Series_table")
    fun clear_serie()

    @Query("DELETE FROM Series_table WHERE Nombre_serie = :nombre")
    fun delete_serie(nombre: String)

    @Query("SELECT * FROM Series_table ORDER BY id_Serie DESC")
    fun AllSeries(): LiveData<List<SerieEntity>>
}