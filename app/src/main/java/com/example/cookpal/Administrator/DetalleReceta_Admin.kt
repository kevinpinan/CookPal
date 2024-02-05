package com.example.cookpal.Administrator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cookpal.R
import com.example.cookpal.databinding.ActivityDetalleRecetaAdminBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DetalleReceta_Admin : AppCompatActivity() {
    lateinit var binding : ActivityDetalleRecetaAdminBinding
    private var idReceta = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityDetalleRecetaAdminBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        idReceta = intent.getStringExtra("idReceta")!!
        binding.IbVolver.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        cargarDetalleReceta()
    }

    private fun cargarDetalleReceta() {
        val ref = FirebaseDatabase.getInstance().getReference("Recetas")
        ref.child(idReceta)
            .addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val categoria = "${snapshot.child("categoria").value}"
                    val ingredientes = "${snapshot.child("ingredientes").value}"
                    val tiempo = "${snapshot.child("tiempo").value}"
                    val titulo = "${snapshot.child("titulo").value}"
                    val url = "${snapshot.child("url").value}"

                    //Misfunciones.CargarCategoria(categoria, binding.TxtCategoriaDetalleRecetaAdmin)
                    Misfunciones.cargarImgUrl(url, binding.icRecetaItem)
                    //seteamos informacion de ingredientes
                    binding.TxtTituloDetalleRecetaAdmin.text = titulo
                    binding.descricionDAdmin.text = ingredientes


                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
}