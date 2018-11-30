package com.example.company.desafiomaster.DAO

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

@Dao
interface EmprestimoDAO {
    @Query("SELECT * FROM Emprestimo")
    fun all(): List<Emprestimo>

    @Query("DELETE FROM Emprestimo")
    fun removeAll()

    @Update
    fun update(vararg emprestimo: Emprestimo)

    @Insert
    fun add(vararg emprestimo: Emprestimo)
}