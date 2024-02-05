package com.example.cookpal.Cliente

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cookpal.Administrator.Misfunciones
import com.example.cookpal.R
import com.example.cookpal.databinding.ActivityDetalleRecetaClienteBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DetalleReceta_cliente : AppCompatActivity() {

    private lateinit var binding : ActivityDetalleRecetaClienteBinding
    private var idReceta = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetalleRecetaClienteBinding.inflate(layoutInflater)

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
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val categoria = "${snapshot.child("categoria").value}"
                    val ingredientes = "${snapshot.child("ingredientes").value}"
                    val titulo = "${snapshot.child("titulo").value}"
                    val tiempo = "${snapshot.child("tiempo").value}"
                    val url = "${snapshot.child("url").value}"

                  //  Misfunciones.CargarCategoria(categoria, binding.TxtCategoriaDetalleRecetaCliente)
                    // Cargar la imagen usando Glide
                    Misfunciones.cargarImgUrl(url, binding.icRecetaItem)
                    //seteamos informacion de ingredientes
                    binding.TxtTituloDetalleRecetaCliente.text = titulo
                    binding.descricionDCliente.text = ingredientes



                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

    }
}