
package com.example.cookpal.Administrator

import android.app.Application
import android.app.ProgressDialog
import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage


class Misfunciones:Application() {
    override fun onCreate() {
        super.onCreate()
    }

    companion object{
        fun CargarCategoria(categoriaId: String, categoriaTv: TextView){
            val ref =FirebaseDatabase.getInstance().getReference("Categorias")
            ref.child(categoriaId)
                .addListenerForSingleValueEvent(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val categoria="${snapshot.child("categoria").value}"
                        categoriaTv.text=categoria
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })

        }
        fun cargarImgUrl(imageUrl: String, imageView: ImageView) {
            // Cargar la imagen desde Firebase Storage usando Glide
            Glide.with(imageView.context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL) // Opcional: especifica la estrategia de almacenamiento en caché
                .into(imageView)
        }

        fun eliminarReceta(context: Context, idReceta : String, urlReceta : String, tituloReceta : String){
            val progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Espere por favor")
            progressDialog.setMessage("Eliminado $tituloReceta")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()

            val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(urlReceta)
            storageReference.delete()
                .addOnSuccessListener {
                    val ref = FirebaseDatabase.getInstance().getReference("Recetas")
                    ref.child(idReceta)
                        .removeValue()
                        .addOnSuccessListener {
                            Toast.makeText(context,"la receta se elimino", Toast.LENGTH_SHORT).show()


                        }
                        .addOnFailureListener {e->
                            progressDialog.dismiss()
                            Toast.makeText(context,"fallo la eliminacion debio a ${e.message}", Toast.LENGTH_SHORT).show()

                        }
                }
                .addOnFailureListener { e->
                    progressDialog.dismiss()
                    Toast.makeText(context,"fallo la eliminacion debio a ${e.message}", Toast.LENGTH_SHORT).show()


                }

        }

    }
}
