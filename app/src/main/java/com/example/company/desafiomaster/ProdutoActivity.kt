package com.example.company.desafiomaster

import android.app.Activity
import android.arch.persistence.room.Room
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.company.desafiomaster.DAO.*
import kotlinx.android.synthetic.main.activity_produto.*
import java.io.ByteArrayOutputStream

class ProdutoActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var productDao: ProductDAO
    private val CAMERA_REQUEST_CODE = 12345
    private val REQUEST_GALLERY_CAMERA = 54654

    private lateinit var nomeItem: EditText
    private lateinit var dataEmprestimo: EditText
    private lateinit var dataDevolucao: EditText
    private lateinit var btn: Button
    private lateinit var capture: TextView
    private lateinit var database: AppDatabase

    private var emprestimo : Emprestimo? = null
    private var imageByteArray : ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produto)

        nomeItem = findViewById(R.id.nomeItem) as EditText
        var msg_nomeItem = findViewById(R.id.msgNomeItem) as TextView

        nomeItem.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(nomeItem.text.length > 0){
                    msg_nomeItem.text = ""
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(nomeItem.text.length == 0){
                    msg_nomeItem.text = "Nome obrigatório!"
                }

            }
        })
        if(nomeItem.text.length > 0){
            msg_nomeItem.text = ""
        }

        dataEmprestimo = findViewById(R.id.dataEmprestimo) as EditText
        var msg_dataEmprestimo = findViewById(R.id.msgEmprestimo) as TextView

        dataEmprestimo.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(dataEmprestimo.text.length > 0){
                    msg_dataEmprestimo.text = ""
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(dataEmprestimo.text.length == 0){
                    msg_dataEmprestimo.text = "Data Emprestimo obrigatório!"
                }
               // if(dataEmprestimo.text.length == 2){
                //     dataEmprestimo.text = dataEmprestimo.text.insert(2,"/")
                // }
                //if(dataEmprestimo.text.length == 5){
                //   dataEmprestimo.text = dataEmprestimo.text.insert(5,"/")
                //}
                //if(dataEmprestimo.text.length > 10){
                //    msg_dataEmprestimo.text = "Data Emprestimo Errada!"
                //}
            }
        })

        dataDevolucao = findViewById(R.id.dataDevolucao) as EditText
        var msg_dataDevolucao = findViewById(R.id.msgEmprestimo) as TextView

        dataDevolucao.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(dataDevolucao.text.length > 0){
                    msg_dataDevolucao.text = ""
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(dataDevolucao.text.length == 0){
                    msg_dataDevolucao.text = "Data Devoluçao obrigatório!"
                }
                //if(dataDevolucao.text.length == 2){
                //    dataDevolucao.text = dataDevolucao.text.insert(3,"/")
                //}
                //if(dataDevolucao.text.length == 5){
                //    dataDevolucao.text = dataDevolucao.text.insert(6,"/")
                //}
                //if(dataDevolucao.text.length > 10){
                //   msg_dataDevolucao.text = "Data Emprestimo Errada!"
                // }
            }
        })

        btn = findViewById(R.id.btnConfirmar) as Button
        capture = findViewById(R.id.capture) as TextView
        database = Room.databaseBuilder(
                this,
                AppDatabase::class.java,
                "app-database")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()

        capture.setOnClickListener(this)
        btn.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v) {
            capture -> {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(
                                this,
                                arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_GALLERY_CAMERA)
                    } else {
                        openCamera()
                    }
                } else {
                    openCamera()
                }
            }
            btn -> {

                var produto = Product(nomeItem.text.toString(),dataEmprestimo.text.toString(),dataDevolucao.text.toString(),imageByteArray, "Emprestado")

                productDao = database!!.productDAO()

                productDao.add(produto)

                emprestimo = intent.getSerializableExtra("emprestimo") as Emprestimo

                emprestimo?.nomeItem = produto.nomeItem
                emprestimo?.dataDevolucao = produto.dataDevolucao
                emprestimo?.dataEmprestimo = produto.dataEmprestimo
                emprestimo?.foto  = produto.foto
                emprestimo?.status = "Emprestado"

                var intent = Intent(this, DetalheActivity::class.java)

                intent.putExtra("emprestimo",emprestimo)

                this.startActivity(intent)
            }
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null)
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_GALLERY_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                Toast.makeText(this@ProdutoActivity, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {

                    val extras = data?.getExtras()
                    val imageBitmap = extras?.get("data") as Bitmap
                    image.setImageBitmap(imageBitmap)

                    imageByteArray = bitmapToByteArray(imageBitmap)

                }
            }
        }
    }

    fun bitmapToByteArray(bitmap: Bitmap) : ByteArray? {
        val stream = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)

        return stream.toByteArray()
    }
}
