package com.example.company.desafiomaster

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.company.desafiomaster.DAO.Emprestimo
import com.example.company.desafiomaster.DAO.Pessoa

class PessoaListViewAdapter (private val context: Context,private val activity: AppCompatActivity,private val pessoasList: List<Pessoa>) : BaseAdapter() {

    private class ViewHolder(row: View?) {
        var tv: TextView? = null
        var ck: ImageButton? = null

        init {
            this.tv = row?.findViewById<TextView>(R.id.tv)
            this.ck = row?.findViewById<ImageButton>(R.id.ck)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = LayoutInflater.from(context)
            //val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.layout_listcheck, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var pessoaDTO = pessoasList[position]
        viewHolder.tv?.text = pessoaDTO.nome

        viewHolder.ck!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var intent = Intent(context, ProdutoActivity::class.java)
                var emprestimo = Emprestimo(pessoaDTO, null)

                intent.putExtra("emprestimo",emprestimo)
                context.startActivity(intent)
            }
        })

        return view as View
    }

    override fun getItem(i: Int): Pessoa {
        return pessoasList[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return pessoasList.size
    }
}