package br.com.fiap.ecotrack.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UsuarioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirUsuario(usuario: Usuario)

    @Query("SELECT * FROM usuarios WHERE email = :email")
    suspend fun buscarPorEmail(email: String): Usuario?
}
