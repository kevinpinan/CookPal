package com.example.cookpal.Administrator

import android.app.AlertDialog
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cookpal.R
import com.example.cookpal.databinding.ActivityActualizarRecetaBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ActualizarReceta : AppCompatActivity() {

    private lateinit var binding : ActivityActualizarRecetaBinding

    private var idReceta = ""

    //llsitarse la categori
    private lateinit var progressDialog : ProgressDialog
    private lateinit var catTituloArrayList: ArrayList<String>
    private lateinit var catIdArraylist : ArrayList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityActualizarRecetaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idReceta = intent.getStringExtra("idReceta")!!

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere porfavor")
        progressDialog.setCanceledOnTouchOutside(false)

        cargarCategorias()
        cargarDatos()
        binding.IbVolver.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.TViewCategoriaReceta.setOnClickListener{
            dialogCategoria()
        }
        binding.btnActualizarReceta.setOnClickListener {
            validarDatos()

        }
    }

    private fun cargarDatos() {
        val ref = FirebaseDatabase.getInstance().getReference("Recetas")
        ref.child(idReceta)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val titulo = snapshot.child("titulo").value.toString()
                    val ingredientes = snapshot.child("ingredientes").value.toString()
                    idSeleccionado = snapshot.child("categoria").value.toString()

                    //seteamos
                    binding.EtTituloReceta.setText(titulo)
                    binding.EtDescripcionReceta.setText(ingredientes)

                    val refCateg = FirebaseDatabase.getInstance().getReference("Categorias")
                    refCateg.child(idSeleccionado)
                        .addListenerForSingleValueEvent(object : ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                //traemos u obetnemos la categoria
                                val categoria = snapshot.child("categoria").value
                                //setear
                                binding.TViewCategoriaReceta.text = categoria.toString()
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }
                        })
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }


            })
    }

    private var titulo = ""
    private var ingredientes = ""

    private fun validarDatos() {
        //extraer datos
        titulo = binding.EtTituloReceta.text.toString().trim()
        ingredientes = binding.EtDescripcionReceta.text.toString().trim()

        if (titulo.isEmpty()){
            Toast.makeText(this,"Ingrese titulo", Toast.LENGTH_SHORT).show()
        }
        else if (ingredientes.isEmpty()){
            Toast.makeText(this,"Ingrese los ingredientes", Toast.LENGTH_SHORT).show()
        }
        else if(idSeleccionado.isEmpty()){
            Toast.makeText(this,"Seleccione una categoria", Toast.LENGTH_SHORT).show()

        }else{
            actualizarInformacion()
        }
    }

    private fun actualizarInformacion() {
        progressDialog.setMessage("Actualizar Informacion")
        progressDialog.show()
        val hashMap = HashMap<String, Any>()
        hashMap["titulo"] = "$titulo"
        hashMap["ingredientes"] = "$ingredientes"
        hashMap["categoria"] = "$idSeleccionado"

        val ref = FirebaseDatabase.getInstance().getReference("Recetas")
        ref.child(idReceta)
            .updateChildren(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this,"la actualizacion fue exitosa", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(this,"la actualizacion fallor por $e,message", Toast.LENGTH_SHORT).show()

            }

    }

    private var idSeleccionado = ""
    private var tituloSeleccionado = ""
    private fun dialogCategoria() {
        val categoriaArray = arrayOfNulls<String>(catTituloArrayList.size)
        for (i in catTituloArrayList.indices){
            categoriaArray[i] = catTituloArrayList[i]

        }
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Seleccione una Categoria")
            .setItems(categoriaArray){dialog, position->
                idSeleccionado = catIdArraylist[position]
                tituloSeleccionado = catTituloArrayList[position]

                binding.TViewCategoriaReceta.text = tituloSeleccionado

            }
            .show()



    }

    private fun cargarCategorias() {
        catTituloArrayList = ArrayList()
        catIdArraylist = ArrayList()
        val ref = FirebaseDatabase.getInstance().getReference("Categorias")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //limpiar las listas antes de agregar
                catTituloArrayList.clear()
                catIdArraylist.clear()
                for (ds in snapshot.children){
                    val id = ""+ds.child("id").value
                    val categoria = ""+ds.child("categoria").value

                    catTituloArrayList.add(categoria)
                    catIdArraylist.add(id)

                }

            }

            override fun onCancelled(error: DatabaseError) {//
            }
        })
    }
}