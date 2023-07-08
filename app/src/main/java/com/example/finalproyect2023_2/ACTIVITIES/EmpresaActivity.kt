package com.example.finalproyect2023_2.ACTIVITIES

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import com.example.finalproyect2023_2.ADAPTERS.JobOffersAdapter
import com.example.finalproyect2023_2.ENTITIES.JobOffer
import com.example.finalproyect2023_2.R
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class EmpresaActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: JobOffersAdapter
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empresa)

        viewPager = findViewById(R.id.viewPager)
        firestore = FirebaseFirestore.getInstance()

        val query = firestore.collection("job_offers").orderBy("timestamp", Query.Direction.DESCENDING)
        val options = FirestoreRecyclerOptions.Builder<JobOffer>()
            .setQuery(query, JobOffer::class.java)
            .build()

        adapter = JobOffersAdapter(options, applicationContext)
        viewPager.adapter = adapter

        val imageView1: ImageView = findViewById(R.id.imageView7)
        val imageView2: ImageView = findViewById(R.id.imageView8)
        val imageView3: ImageView = findViewById(R.id.imageView9)
        val imageView4: ImageView = findViewById(R.id.imageView10)

        imageView3.setOnClickListener {
            // cierro mi sesion
            signOut();
            finish()
        }
/*        imageView2.setOnClickListener {
            // Redirige a otra vista PERFIL cuando se hace clic en imageView2
            val intent = Intent(this, PERFIL::class.java)
            startActivity(intent)
        }
*/
        imageView1.setOnClickListener {
            // Redirige a otra vista VERPUBLICACIONES cuando se hace clic en imageView2
            val intent = Intent(this, vistaPublicacionesEmpActivity::class.java)
            startActivity(intent)
        }
    /*

        imageView4.setOnClickListener {
            // Redirige a otra vista PUBLICAR cuando se hace clic en imageView2
            val intent = Intent(this, PUBLICAR::class.java)
            startActivity(intent)
        }
*/
    }
    private fun signOut() {
        // Obtener la instancia de FirebaseAuth
        val auth = FirebaseAuth.getInstance()

        // Cerrar sesión
        auth.signOut()

        // Realizar cualquier acción adicional después de cerrar sesión
        // Por ejemplo, redirigir a la pantalla de inicio de sesión
        // o mostrar un mensaje de éxito al usuario
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}