package miseguridad.com.ui_

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import miseguridad.com.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_autoridad)

        // Encontrar el botón de logout por su ID
        val btnLogout: ImageView = findViewById(R.id.btnLogout)

        // Establecer el listener para el clic en el ícono de logout
        btnLogout.setOnClickListener {
            // Crear un Intent para navegar a LoginActivity
            val intent = Intent(this, LoginActivity::class.java)

            // Iniciar LoginActivity
            startActivity(intent)

            // Cerrar la actividad actual (HomeActivity)
            finish()
        }


        val btnVisualizar: Button = findViewById(R.id.btnVisualizar)

        btnVisualizar.setOnClickListener {
            val intent = Intent(this, VisualizarIncidenciasActivity::class.java)

            // Iniciar RegistrarIncidenciaActivity
            startActivity(intent)
        }



        val btnCargarIncidencias: Button = findViewById(R.id.btnRegistrarIncidencia)

        btnCargarIncidencias.setOnClickListener {
            val intent = Intent(this, RegistrarIncidenciaActivity::class.java)

            // Iniciar RegistrarIncidenciaActivity
            startActivity(intent)
        }

        val btnModificar: Button = findViewById(R.id.btnModificar)

        btnModificar.setOnClickListener {
            val intent = Intent(this, ModificarIncidenciaActivity::class.java)

            startActivity(intent)
        }

        val btnRegistrarUsuario: Button = findViewById(R.id.btnRegistrarUsuario)

        btnRegistrarUsuario.setOnClickListener {
            val intent = Intent(this, RegistrarUsuarioActivity::class.java)

            startActivity(intent)
        }
    }
}