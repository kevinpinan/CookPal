package com.example.cookpal.Administrator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cookpal.R
import com.example.cookpal.databinding.ActivityListaRecetaClienteBinding
import com.example.cookpal.databinding.ActivityListarImgRecetasBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListarImgRecetas : AppCompatActivity() {

    private lateinit var binding:ActivityListarImgRecetasBinding
    private var idCategoria = ""
    private var tituloCategoria= ""

    private lateinit var recetasArrayList: ArrayList<ModeloReceta>
    private lateinit var adaptadorRecetasAdmin: AdaptadorRecetasAdmin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityListarImgRecetasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //error por lo que no se me carga las listas
        //setContentView(R.layout.activity_listar_img_recetas)

        //obtenemos el intent desde adaptador
        val intent=intent
        idCategoria=intent.getStringExtra("idCategoria")!!
        tituloCategoria=intent.getStringExtra("tituloCategoria")!!

        binding.txtCategoriaReceta.text=tituloCategoria

        binding.IbVolver.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        ListarRecetas()



    }

    private fun ListarRecetas() {
        recetasArrayList= ArrayList()

        val ref = FirebaseDatabase.getInstance().getReference("Recetas")
        ref.orderByChild("categoria").equalTo(idCategoria)
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    recetasArrayList.clear()

                    for(ds in snapshot.children){
                        val modelo=ds.getValue(ModeloReceta::class.java)

                        if(modelo!=null){
                            recetasArrayList.add(modelo)

                        }

                    }

                    adaptadorRecetasAdmin=AdaptadorRecetasAdmin(this@ListarImgRecetas,recetasArrayList)
                    binding.RvRecetasAdmin.adapter= adaptadorRecetasAdmin
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
}