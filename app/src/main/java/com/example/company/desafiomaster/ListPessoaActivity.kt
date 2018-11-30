package com.example.company.desafiomaster

import android.arch.persistence.room.Room
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.company.desafiomaster.DAO.AppDatabase
import com.example.company.desafiomaster.DAO.PessoaDAO

class ListPessoaActivity : AppCompatActivity() {

    private lateinit var listPessoa: ListView
    private lateinit var pessoaDAO: PessoaDAO
    private lateinit var adapter: PessoaListViewAdapter
    private lateinit var database: AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_pessoa)

        listPessoa = findViewById(R.id.listPessoa) as ListView

        database = Room.databaseBuilder(
                this,
                AppDatabase::class.java,
                "app-database")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()

        pessoaDAO = database.pessoaDAO()

        adapter = PessoaListViewAdapter(this, AppCompatActivity(), pessoaDAO.all())

        listPessoa.adapter = adapter
    }
}
