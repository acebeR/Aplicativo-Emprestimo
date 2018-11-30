package com.example.company.desafiomaster

import android.content.Context

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

import android.widget.ImageView
import android.widget.TextView
import com.example.company.desafiomaster.DAO.Emprestimo

import java.io.ByteArrayInputStream

class GridViewAdapter (private val context: Context, private val activity: AppCompatActivity, private val emprestimoList: List<Emprestimo>) : BaseAdapter() {


    private class ViewHolder(row: View?) {
        var imgProduto: ImageView? = null
        var nomeProduto: TextView? = null
        var status: TextView? = null
        var nomePessoa: TextView? = null


        init {
            this.imgProduto = row?.findViewById<ImageView>(R.id.imgProduto)
            this.nomeProduto = row?.findViewById<TextView>(R.id.nomeProduto)
            this.status = row?.findViewById<TextView>(R.id.status)
            this.nomePessoa = row?.findViewById<TextView>(R.id.nomePessoa)

        }
    }



    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = LayoutInflater.from(context)

            view = inflater.inflate(R.layout.gridview, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var emprestimo = emprestimoList[position]
        viewHolder.imgProduto?.setImageBitmap(ByteArrayToBitmap(emprestimo.foto))
        viewHolder.status?.text = emprestimo.status
        viewHolder.nomeProduto?.text = emprestimo.nomeItem
        viewHolder.nomePessoa?.text = emprestimo.nomePessoa


        return view as View
    }

    override fun getItem(i: Int): Emprestimo {
        return emprestimoList[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return emprestimoList.size
    }

    fun ByteArrayToBitmap(byteArray: ByteArray?): Bitmap {
        val arrayInputStream = ByteArrayInputStream(byteArray)
        return BitmapFactory.decodeStream(arrayInputStream)
    }
}