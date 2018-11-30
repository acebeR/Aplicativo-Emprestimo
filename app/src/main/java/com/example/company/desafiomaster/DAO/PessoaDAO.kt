package com.example.company.desafiomaster.DAO

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface PessoaDAO {
    @Query("SELECT * FROM Pessoa")
    fun all(): List<Pessoa>

    @Query("DELETE FROM Pessoa")
    fun removeAll()

    @Insert
    fun add(vararg pessoa: Pessoa)

}