package br.com.fiap.ecotrack.database

import androidx.room.*

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey
    val email: String,
    val nome: String,
    val senha: String,
    val pontosEco: Int = 0
)
