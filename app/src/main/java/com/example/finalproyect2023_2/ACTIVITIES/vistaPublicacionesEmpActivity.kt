package com.example.finalproyect2023_2.ACTIVITIES

import PostulacionesAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproyect2023_2.ENTITIES.Postulacion
import com.example.finalproyect2023_2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class vistaPublicacionesEmpActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PostulacionesAdapter
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_publicaciones_emp)

        recyclerView = findViewById(R.id.RecyclerViewPostulacionesEmp)
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            val idEmpresa = currentUser.uid

            firestore.collection("Postulaciones")
                .whereEqualTo("idPostulante", idEmpresa)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    // Verificar si hay postulaciones para el usuario actual
                    if (!querySnapshot.isEmpty) {
                        val postulacionesList = ArrayList<Postulacion>()
                        for (document in querySnapshot.documents) {
                            val postulacion = document.toObject(Postulacion::class.java)
                            if (postulacion != null) {
                                postulacionesList.add(postulacion)
                            }
                        }
                        adapter = PostulacionesAdapter(postulacionesList)
                        recyclerView.adapter = adapter
                        recyclerView.layoutManager = LinearLayoutManager(this)
                    } else {
                        // El usuario no tiene postulaciones
                        // Realiza aquí la lógica necesaria para manejar el caso sin postulaciones
                    }
                }
                .addOnFailureListener { e ->
                    // Manejar errores al obtener las postulaciones
                }
        }else {
            // El usuario no está autenticado, realiza una acción adecuada
            // por ejemplo, mostrar un mensaje o redirigir a la pantalla de inicio de sesión
        }
    }
}