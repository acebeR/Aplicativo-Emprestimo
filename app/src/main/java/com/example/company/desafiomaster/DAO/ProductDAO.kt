package com.example.company.desafiomaster.DAO

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface ProductDAO {

    @Query("SELECT * FROM Product")
    fun all(): List<Product>

    @Query("DELETE FROM Product")
    fun removeAll()

    @Insert
    fun add(vararg product: Product)

}