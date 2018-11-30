package com.example.company.desafiomaster

import android.annotation.TargetApi
import android.arch.persistence.room.Room
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.GridView
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import com.example.company.desafiomaster.DAO.*
import com.example.company.desafiomaster.R.id.drawer_layout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_produto.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var gridview : GridView
    private lateinit var database: AppDatabase
    private lateinit var emprestimoDao: EmprestimoDAO


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)



        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        database = Room.databaseBuilder(
                this,
                AppDatabase::class.java,
                "app-database")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()


        emprestimoDao = database.emprestimoDAO()


        for(emprestimo: Emprestimo in emprestimoDao.all()) {
            Log.d("DataStr", emprestimo.dataDevolucao)
            Log.d("Data", convertStringToDate(emprestimo.dataDevolucao!!).toString())
            Log.d("Data Atual", getCurrentDate().toString())
            if(getCurrentDate() > convertStringToDate(emprestimo.dataDevolucao!!)) {
                Log.d("Teste1","Teste")
                emprestimo.status = "Liberado"
                emprestimoDao.update(emprestimo)
            } else {

            }

        }


        gridview = findViewById(R.id.gridProduto)

        gridview.adapter = GridViewAdapter(this, AppCompatActivity(), emprestimoDao.all())



    }

    fun getCurrentDate(): Date {
        return Calendar.getInstance().time
    }

    fun convertStringToDate(dataStr: String): Date {

        val sdf = SimpleDateFormat("dd/MM/yyyy"); // here set the pattern as you date in string was containing like date/month/year
        val date = sdf.parse(dataStr)
        return date
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

            var intent : Intent

            when (item.itemId) {
                R.id.perfil -> {
                    intent = Intent(this, PerfilActivity::class.java)
                    startActivity(intent)
                }
                R.id.addPessoa -> {
                    intent = Intent(this, QuemEmprestaActivity::class.java)
                    startActivity(intent)
                }

                R.id.list_Emprestimo -> {
                    intent = Intent(this, ListPessoaActivity::class.java)
                    startActivity(intent)
                }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
