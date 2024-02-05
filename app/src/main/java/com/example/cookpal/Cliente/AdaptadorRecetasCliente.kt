package com.example.cookpal.Cliente

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cookpal.Administrator.Misfunciones
import com.example.cookpal.Administrator.ModeloReceta
import com.example.cookpal.databinding.ItemRecetaClienteBinding
import com.example.cookpal.databinding.ItemRecetasAdminBinding

class AdaptadorRecetasCliente : RecyclerView.Adapter<AdaptadorRecetasCliente.HolderRecetasCliente> {

    private lateinit var binding: ItemRecetaClienteBinding

    private var m_context: Context
    private var recetasArrayList: ArrayList<ModeloReceta>

    constructor(m_context: Context, recetasArrayList: ArrayList<ModeloReceta>) : super() {
        this.m_context = m_context
        this.recetasArrayList = recetasArrayList
    }


    //holder para iniciar vistas de item
    inner class HolderRecetasCliente (itemView: View) : RecyclerView.ViewHolder(itemView){

        val txt_titulo_receta_item = binding.txtTituloRecetaItem
        val ic_receta_item= binding.icRecetaItem
        //val txt_categoria_receta_admin=binding.txtCategoriaRecetaAdmin
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderRecetasCliente {
        binding= ItemRecetaClienteBinding.inflate(LayoutInflater.from(m_context),parent,false)
        return HolderRecetasCliente(binding.root)
    }

    override fun getItemCount(): Int {
        return recetasArrayList.size
    }

    override fun onBindViewHolder(holder: HolderRecetasCliente, position: Int) {
        val modelo=recetasArrayList[position]
        val recetaID = modelo.id
        val categoriaId=modelo.categoria
        val titulo=modelo.titulo
        val ingredientes=modelo.ingredientes
        val imgURL=modelo.url
        val tiempo=modelo.tiempo

        holder.txt_titulo_receta_item.text = titulo
        //Misfunciones.CargarCategoria(categoriaId,holder.txt_categoria_receta_admin)
        Misfunciones.cargarImgUrl(imgURL, binding.icRecetaItem)

        holder.itemView.setOnClickListener {
            val intent = Intent(m_context , DetalleReceta_cliente::class.java)
            intent.putExtra("idReceta",recetaID)
            m_context.startActivity(intent)
        }


        /*
                cargarImagenDesdeFirebase(imgURL, holder.ic_receta_item)
        */


    }
    /*private fun cargarImagenDesdeFirebase(imgURL: String, icRecetaItem: AppCompatImageView) {
        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL) // Opcional: Estrategia de almacenamiento en caché

        Glide.with(m_context)
            .load(imgURL)
            .apply(requestOptions)
            .into(icRecetaItem)

    }*/

}