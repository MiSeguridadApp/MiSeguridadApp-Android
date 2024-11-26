package miseguridad.com.ui_

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.apisecurityapp.api.ApiClient
import com.example.apisecurityapp.model.OrganizacionRequest
import kotlinx.coroutines.launch
import miseguridad.com.R
import retrofit2.HttpException

class RegistrarOrganizacionActivity : AppCompatActivity() {

    private lateinit var nombreEditText: EditText
    private lateinit var tipoEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_organizacion)
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
        nombreEditText = findViewById(R.id.tipoorganizacionEditText)
        tipoEditText = findViewById(R.id.nombreEditText)
        saveButton = findViewById(R.id.button)

        saveButton.setOnClickListener {
            val tipo = tipoEditText.text.toString()
            val nombre = nombreEditText.text.toString()


            if (nombre.isEmpty() || tipo.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val organizacionRequest = OrganizacionRequest(
                tipo = tipo,
                nombre = nombre

            )
            registrarOrganizacion(organizacionRequest)
        }
    }

    private fun registrarOrganizacion(organizacionRequest: OrganizacionRequest) {
        lifecycleScope.launch {
            try {
                // Realizar la solicitud a la API para crear la organización
                val response = ApiClient.apiService.registrarOrganizacion(organizacionRequest)

                if (response.isSuccessful) {
                    // Si la respuesta es exitosa
                    Toast.makeText(this@RegistrarOrganizacionActivity, "Organización registrada con éxito", Toast.LENGTH_SHORT).show()
                } else {
                    // Si la respuesta no fue exitosa
                    Toast.makeText(this@RegistrarOrganizacionActivity, "Error al registrar la organización", Toast.LENGTH_SHORT).show()
                }
            } catch (e: HttpException) {
                Toast.makeText(this@RegistrarOrganizacionActivity, "Error en la solicitud: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@RegistrarOrganizacionActivity, "Error al registrar la organización", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
