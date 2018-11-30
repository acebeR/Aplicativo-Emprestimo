package com.example.company.desafiomaster.DAO

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.graphics.Bitmap
import java.io.Serializable

@Entity
class Product : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var nomeItem: String
    var dataEmprestimo: String
    var dataDevolucao : String
    var foto : ByteArray? = null
    var status: String? = "Não emprestado"

    constructor(nomeItem: String, dataEmprestimo: String, dataDevolucao: String, foto : ByteArray?, status: String) {
        this.nomeItem = nomeItem
        this.dataEmprestimo = dataEmprestimo
        this.dataDevolucao = dataDevolucao
        this.foto = foto
        this.status = status
    }

}