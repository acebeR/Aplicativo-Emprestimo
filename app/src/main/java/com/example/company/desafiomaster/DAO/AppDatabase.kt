package com.example.company.desafiomaster.DAO

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [Product::class, Pessoa::class , Emprestimo::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDAO(): ProductDAO
    abstract fun pessoaDAO(): PessoaDAO
    abstract fun emprestimoDAO(): EmprestimoDAO

}