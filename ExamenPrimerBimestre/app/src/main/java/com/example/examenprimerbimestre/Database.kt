package com.example.examenprimerbimestre
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

class Database {
    @Database(entities = [SerieEntity::class, ActorEntity::class], version = 1, exportSchema = false)
    abstract class SeriesDatabase : RoomDatabase()  {

        abstract val serieDao: SerieDao
        abstract val actorDao: ActorDao

        companion object {

            @Volatile
            private var INSTANCE: SeriesDatabase? = null

            fun getInstance(context: Context): SeriesDatabase {
                synchronized(this) {
                    var instance = INSTANCE

                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            SeriesDatabase::class.java,
                            "sleep_history_database"
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                        INSTANCE = instance
                    }
                    return instance
                }
            }
        }
    }
}