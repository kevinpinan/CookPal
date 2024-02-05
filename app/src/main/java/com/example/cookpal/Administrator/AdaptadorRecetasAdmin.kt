package com.example.cookpal.Administrator

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cookpal.databinding.ItemRecetasAdminBinding

class AdaptadorRecetasAdmin : RecyclerView.Adapter<AdaptadorRecetasAdmin.HolderRecetasAdmin>{

    private lateinit var binding:ItemRecetasAdminBinding

    private var m_context:Context
    private var recetasArrayList: ArrayList<ModeloReceta>

    constructor(m_context: Context, recetasArrayList: ArrayList<ModeloReceta>) : super() {
        this.m_context = m_context
        this.recetasArrayList = recetasArrayList
    }


    //holder para iniciar vistas de item
    inner class HolderRecetasAdmin(itemView: View) : RecyclerView.ViewHolder(itemView){
        val Ib_opcionEditar = binding.IbOpcionEditar
        val txt_titulo_receta_item = binding.txtTituloRecetaItem
        val ic_receta_item = binding.icRecetaItem
        //val txt_categoria_receta_admin = binding.txtCategoriaRecetaAdmin
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderRecetasAdmin {
        binding = ItemRecetasAdminBinding.inflate(LayoutInflater.from(m_context), parent, false)
        return HolderRecetasAdmin(binding.root)
    }

    override fun getItemCount(): Int {
        return recetasArrayList.size
    }

    override fun onBindViewHolder(holder: HolderRecetasAdmin, position: Int) {
        val modelo = recetasArrayList[position]
        val recetaID = modelo.id
        val categoriaId = modelo.categoria
        val titulo = modelo.titulo
        val ingredientes = modelo.ingredientes
        val imgURL = modelo.url
        val tiempo = modelo.tiempo

        holder.txt_titulo_receta_item.text = titulo
       // Misfunciones.CargarCategoria(categoriaId, holder.txt_categoria_receta_admin)
        Misfunciones.cargarImgUrl(imgURL, binding.icRecetaItem)

        holder.itemView.setOnClickListener {
            val intent = Intent(m_context, DetalleReceta_Admin::class.java)
            intent.putExtra("idReceta", recetaID)
            m_context.startActivity(intent)
        }
        holder.Ib_opcionEditar.setOnClickListener {
            verOpciones(modelo, holder)
        }


    }
    private fun verOpciones(modelo: ModeloReceta,holder: AdaptadorRecetasAdmin.HolderRecetasAdmin) {
        val idReceta = modelo.id
        val urlReceta = modelo.url
        val tituloReceta = modelo.titulo

        val opciones = arrayOf("Actualizar", "Eliminar")

        //aletr dialog
        val builder = AlertDialog.Builder(m_context)
        builder.setTitle("Seleccione una opcion....")
            .setItems(opciones) { dialog, position->
                if (position == 0) {
                    //actualizar
                    val intent = Intent(m_context, ActualizarReceta::class.java)
                    intent.putExtra("idReceta", idReceta)
                    m_context.startActivity(intent)

                } else if (position == 1) {
                    val opcionEliminar = arrayOf("Si", "Cancelar")
                    val builder = AlertDialog.Builder(m_context)
                        .setItems(opcionEliminar) { dialog, position->
                            if (position == 0) {
                                Misfunciones.eliminarReceta(
                                    m_context,
                                    idReceta,
                                    urlReceta,
                                    tituloReceta
                                )

                            } else if (position == 1) {
                                Toast.makeText(m_context, "Cancelado", Toast.LENGTH_SHORT)
                                    .show()
                            }

                        }
                        .show()

                }

            }
            .show()


    }
}