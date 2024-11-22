package miseguridad.com.ui_

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.apisecurityapp.api.ApiClient
import com.example.apisecurityapp.model.IncidenciaRequest
import kotlinx.coroutines.launch
import miseguridad.com.R
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RegistrarIncidenciaActivity : AppCompatActivity() {

    private lateinit var ubicacionEditText: EditText
    private lateinit var tipoEditText: EditText
    private lateinit var estadoSpinner: Spinner
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_incidencia)
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

        ubicacionEditText = findViewById(R.id.coordenadasEditText)
        tipoEditText = findViewById(R.id.tipoincidenciaEditText)
        estadoSpinner = findViewById(R.id.spinner)
        saveButton = findViewById(R.id.button)

        // Configurar el Spinner con los valores "Registrado", "Atendido", "Solucionado"
        val estadoAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.estado_array,
            android.R.layout.simple_spinner_item
        )
        estadoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        estadoSpinner.adapter = estadoAdapter

        saveButton.setOnClickListener {
            val ubicacion = ubicacionEditText.text.toString()
            val tipo = tipoEditText.text.toString()
            val estado = estadoSpinner.selectedItemPosition + 1 // Estado basado en el índice (1-3)
            val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
            val idusuario = sharedPreferences.getInt("idusuario", -1)  // Valor por defecto -1 si no se encuentra el ID
            if (ubicacion.isEmpty() || tipo.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val incidenciaRequest = IncidenciaRequest(
                idusuario = idusuario,
                tipo = tipo,
                ubicacion = ubicacion,
                estado = estado,
            )
            crearIncidencia(incidenciaRequest)
        }
    }

    private fun crearIncidencia(incidenciaRequest: IncidenciaRequest) {
        lifecycleScope.launch {
            try {
                // Realizar la solicitud a la API para crear la incidencia
                val response = ApiClient.apiService.crearIncidencia(incidenciaRequest)

                if (response.isSuccessful) {
                    // Si la respuesta es exitosa
                    Toast.makeText(this@RegistrarIncidenciaActivity, "Incidencia creada con éxito", Toast.LENGTH_SHORT).show()
                } else {
                    // Si la respuesta no fue exitosa
                    Toast.makeText(this@RegistrarIncidenciaActivity, "Error al crear la incidencia", Toast.LENGTH_SHORT).show()
                }
            } catch (e: HttpException) {
                Toast.makeText(this@RegistrarIncidenciaActivity, "Error en la solicitud: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@RegistrarIncidenciaActivity, "Incidencia creada con éxito", Toast.LENGTH_SHORT).show()
            }
        }
    }
}