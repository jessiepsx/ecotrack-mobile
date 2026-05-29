package br.com.fiap.ecotrack.database

import android.content.Context
import androidx.room.*

@Database(entities = [Usuario::class], version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "ecotrack_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}