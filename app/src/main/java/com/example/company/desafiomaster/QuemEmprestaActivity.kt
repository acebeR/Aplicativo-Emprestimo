package com.example.company.desafiomaster

import android.arch.persistence.room.Room
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.company.desafiomaster.DAO.AppDatabase
import com.example.company.desafiomaster.DAO.Pessoa
import com.example.company.desafiomaster.DAO.PessoaDAO
import org.json.JSONObject

class QuemEmprestaActivity : AppCompatActivity() {

    private lateinit var pessoaDAO: PessoaDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quem_empresta)

        var nome = findViewById(R.id.nome) as EditText
        var msg_nome = findViewById(R.id.msgNome) as TextView

        nome.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(nome.text.length > 0){
                    msg_nome.text = ""
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(nome.text.length == 0){
                    msg_nome.text = "Nome Obrigatório!"
                }
            }
        })

        var telefone = findViewById(R.id.telefone) as EditText
        var msg_telefone = findViewById(R.id.msgTelefone) as TextView

        telefone.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(telefone.text.length > 0){
                    msg_telefone.text = ""
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(telefone.text.length == 0){
                    msg_telefone.text = "Telefone Obrigatório!"
                }
                if(telefone.text.length < 8){
                    msg_telefone.text = "Telefone Incorreto!"
                }
            }
        })

        var cep = findViewById(R.id.cep) as EditText
        var msg_cep = findViewById(R.id.msgCep) as TextView

        var btnConfirmar = findViewById(R.id.btnConfirmar) as Button

        val database = Room.databaseBuilder(
                this,
                AppDatabase::class.java,
                "app-database")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()

        pessoaDAO = database.pessoaDAO()

        cep.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(cep.text.length > 0){
                    msg_cep.text = ""
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(cep.text.length == 0){
                    msg_cep.text = "Cep obrigatório!"
                }
                if(cep.text.length < 8){
                    msg_cep.text = "Cep Inválido!"
                }else{
                    if(cep.text.length == 8){
                        apiHit(cep.text.toString())
                    }
            }
            }
        })


        btnConfirmar.setOnClickListener {
            if(nome.text.isEmpty()) {

                Toast.makeText(this,"Informe Nome", Toast.LENGTH_LONG).show()

            } else {
                var pessoa = Pessoa(nome.text.toString(),telefone.text.toString(),cep.text.toString())

                pessoaDAO.add(pessoa)

                Toast.makeText(this,"Salvo com Sucesso!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun apiHit(cep : String) {

        var bairro = findViewById(R.id.bairro) as TextView
        var cidade = findViewById(R.id.cidade) as TextView
        var logradouro = findViewById(R.id.logradouro) as TextView
        var estado = findViewById(R.id.estado) as TextView
        var msg = findViewById(R.id.msg) as TextView

        if(cep.length == 8) {

            val url = "http://api.postmon.com.br/v1/cep/" + cep
            val queue : RequestQueue = Volley.newRequestQueue(this)
            val request =  JsonObjectRequest(
                    Request.Method.GET ,
                    url,
                    null ,
                    {
                        response: JSONObject? ->
                        if(response != null) {

                            bairro.text = Editable.Factory.getInstance().newEditable(response.getString("bairro"))
                            cidade.text = Editable.Factory.getInstance().newEditable(response.getString("cidade"))
                            logradouro.text = Editable.Factory.getInstance().newEditable(response.getString("logradouro"))
                            estado.text = Editable.Factory.getInstance().newEditable(response.getString("estado"))
                        }
                    } ,
                    {
                        error: VolleyError? ->
                        msg.text =  error.toString()
                    }
            )
            queue.add(request)
        }
    }
}
