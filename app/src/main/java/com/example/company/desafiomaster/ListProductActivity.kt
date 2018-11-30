package com.example.company.desafiomaster

import android.arch.persistence.room.Room
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.company.desafiomaster.DAO.AppDatabase
import com.example.company.desafiomaster.DAO.Product
import com.example.company.desafiomaster.DAO.ProductDAO

class ListProductActivity : AppCompatActivity() {

    private lateinit var listView : ListView
    private lateinit var productDao: ProductDAO
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_product)

        listView = this.findViewById(R.id.recipe_list_view)

        database = Room.databaseBuilder(
                this,
                AppDatabase::class.java,
                "app-database")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()

        productDao = database.productDAO()

       // productDao.removeAll()


        productDao.add()
        // productDao.add(Product("nome1","data","data", ByteArray(R.drawable.abc_cab_background_internal_bg)))
        // productDao.add(Product("nome2","data","data", ByteArray(R.drawable.abc_cab_background_top_material)))
        // productDao.add(Product("nome3","data","data", ByteArray(R.drawable.abc_dialog_material_background)))

        var list = ArrayList<String>()

        for( i in productDao.all() ) {
            Log.d("Produto",i.nomeItem)
            list.add(i.nomeItem)
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        listView.adapter = adapter

    }
}
