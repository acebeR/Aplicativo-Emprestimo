package com.example.company.desafiomaster.DAO

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity
class Pessoa : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var nome: String
    var telefone: String
    var cep : String

    constructor(nome: String, telefone: String, cep: String) {
        this.nome = nome
        this.telefone = telefone
        this.cep = cep
    }
}