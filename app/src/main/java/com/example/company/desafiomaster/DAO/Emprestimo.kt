package com.example.company.desafiomaster.DAO

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity
class Emprestimo : Serializable {

    @PrimaryKey(autoGenerate = true)
    var idEmprestimo: Int = 0
    var nomePessoa: String? = null
    var telefone: String? = null
    var cep : String? = null
    var nomeItem: String? = null
    var dataEmprestimo: String? = null
    var dataDevolucao : String? = null
    var foto : ByteArray? = null
    var status: String? = "Não emprestado"

    @Ignore
    constructor(pessoa: Pessoa?, product: Product?) {
        this.nomePessoa = pessoa?.nome
        this.telefone = pessoa?.telefone
        this.cep = pessoa?.cep
        this.nomeItem = product?.nomeItem
        this.dataEmprestimo = product?.dataEmprestimo
        this.dataDevolucao = product?.dataDevolucao
        this.foto = product?.foto
        this.status = product?.status
    }

    constructor(nomePessoa: String?, telefone: String? ,cep: String?,nomeItem: String?,dataEmprestimo: String?,dataDevolucao: String?,foto: ByteArray?,status: String?) {
        this.nomePessoa = nomePessoa
        this.telefone = telefone
        this.cep = cep
        this.nomeItem = nomeItem
        this.dataEmprestimo = dataEmprestimo
        this.dataDevolucao = dataDevolucao
        this.foto = foto
        this.status = status
    }
}