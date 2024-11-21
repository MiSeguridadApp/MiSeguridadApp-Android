package miseguridad.com.ui_

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import miseguridad.com.R

class VisualizarIncidenciasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizar_incidencias)  // Asegúrate de tener el archivo XML correspondiente

        val btnBack: ImageView = findViewById(R.id.btnBack)

        // Establecer el listener para el clic en el ícono de logout
        btnBack.setOnClickListener {
            // Crear un Intent para navegar a LoginActivity
            val intent = Intent(this, HomeActivity::class.java)

            // Iniciar LoginActivity
            startActivity(intent)

            // Cerrar la actividad actual (HomeActivity)
            finish()
        }

    }
}