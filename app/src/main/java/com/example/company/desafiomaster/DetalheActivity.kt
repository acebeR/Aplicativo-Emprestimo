package com.example.company.desafiomaster

import android.arch.persistence.room.Room
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.company.desafiomaster.DAO.Emprestimo
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.company.desafiomaster.DAO.AppDatabase
import com.example.company.desafiomaster.DAO.EmprestimoDAO
import com.example.company.desafiomaster.DAO.Product
import kotlinx.android.synthetic.main.activity_detalhe.*
import java.io.ByteArrayInputStream


class DetalheActivity : AppCompatActivity() {

    private lateinit var btnVoltar: Button
    private lateinit var database: AppDatabase
    private lateinit var emprestimoDao: EmprestimoDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe)


        var nomeProduto = findViewById(R.id.nomeProduto) as TextView
        var imagem = findViewById(R.id.imgProduto) as ImageView
        var dataDevolucao = findViewById(R.id.dataDevolucao) as TextView
        var dataEmprestimo = findViewById(R.id.dataEmprestimo) as TextView

        var nomePessoa = findViewById(R.id.nomePessoa) as TextView
        var telefone = findViewById(R.id.telefone) as TextView
        var cep = findViewById(R.id.cep) as TextView
        btnVoltar = findViewById(R.id.btnVoltar) as Button

        btnVoltar.setOnClickListener(){
            var intent = Intent(this,  MainActivity::class.java)
            this.startActivity(intent)
        }

        database = Room.databaseBuilder(
                this,
                AppDatabase::class.java,
                "app-database")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()

        var emprestimo: Emprestimo? = intent.getSerializableExtra("emprestimo") as Emprestimo?

        emprestimoDao = database.emprestimoDAO()

        emprestimoDao.add(emprestimo!!)

        if(emprestimo != null) {

            nomeProduto.text = "Nome: " + emprestimo!!.nomeItem
            imagem.setImageBitmap(ByteArrayToBitmap(emprestimo!!.foto))
            dataDevolucao.text =  "Datab Devoluçao: " +emprestimo!!.dataDevolucao
            dataEmprestimo.text =  "Data Emprestimo: " +emprestimo!!.dataEmprestimo

            nomePessoa.text =  "Nome: " +emprestimo!!.nomePessoa
            telefone.text =  "Telefone: " +emprestimo!!.telefone
            cep.text =  "CEP: " +emprestimo!!.cep

        }


    }

    fun ByteArrayToBitmap(byteArray: ByteArray?): Bitmap {
        val arrayInputStream = ByteArrayInputStream(byteArray)
        return BitmapFactory.decodeStream(arrayInputStream)
    }


}
