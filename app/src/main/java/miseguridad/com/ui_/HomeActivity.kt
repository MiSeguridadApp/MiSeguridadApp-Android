package miseguridad.com.ui_

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import miseguridad.com.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_admin)

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
    }
}