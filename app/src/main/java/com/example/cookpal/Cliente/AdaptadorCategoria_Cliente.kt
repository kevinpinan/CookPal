package com.example.cookpal.Cliente

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookpal.Administrator.ModeloCategoria
import com.example.cookpal.databinding.ItemCategoriaClienteBinding

class AdaptadorCategoria_Cliente : RecyclerView.Adapter<AdaptadorCategoria_Cliente.viewHolder>{

        private lateinit var binding : ItemCategoriaClienteBinding

        private val context: Context
        private val categoriaArrayList :ArrayList<ModeloCategoria>

    constructor(context: Context, categoriaArrayList: ArrayList<ModeloCategoria>) {
        this.context = context
        this.categoriaArrayList = categoriaArrayList
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        binding=ItemCategoriaClienteBinding.inflate(LayoutInflater.from(context),parent,false)
        return viewHolder(binding.root)


    }

    override fun getItemCount(): Int {
        return categoriaArrayList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val modelo = categoriaArrayList[position]
        val id = modelo.id
        val categoria = modelo.categoria
        val uid = modelo.uid
        val tiempo = modelo.tiempo

        holder.categoriaTv.text = categoria
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ListaRecetaCliente::class.java)
            intent.putExtra("idCategoria",id)
            intent.putExtra("tituloCategoria",categoria)
            context.startActivity(intent)
        }


    }

    inner public class viewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

        var categoriaTv: TextView = binding.itemNombreCateCliente


    }
}