package miseguridad.com.ui_

import android.content.Intent
import android.os.Bundle
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

    private lateinit var tipoEditText: EditText
    private lateinit var ubicacionEditText: EditText
    private lateinit var estadoSpinner: Spinner
    private lateinit var guardarButton: Button

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
        tipoEditText = findViewById(R.id.tipoincidenciaEditText)
        ubicacionEditText = findViewById(R.id.coordenadasEditText)
        estadoSpinner = findViewById(R.id.spinner)
        guardarButton = findViewById(R.id.button)

        guardarButton.setOnClickListener {
            val tipo = tipoEditText.text.toString()
            val ubicacion = ubicacionEditText.text.toString()
            val fecha = obtenerFechaActual()
            val estado = estadoSpinner.selectedItemPosition // Obtén el estado seleccionado en el Spinner
            val idUsuario = 1 // Asegúrate de obtener el ID del usuario logueado

            if (tipo.isEmpty() || ubicacion.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Crear la incidencia
            val incidenciaRequest = IncidenciaRequest(
                idusuario = idUsuario,
                fecha = fecha,  // Fecha actual
                ubicacion = ubicacion,
                estado = estado + 1, // Ajusta el estado si es necesario
                tipo = tipo
            )

            // Enviar la incidencia al servidor
            crearIncidencia(incidenciaRequest)
        }
    }

    private fun crearIncidencia(incidenciaRequest: IncidenciaRequest) {
        lifecycleScope.launch {
            try {
                // Realizar la solicitud a la API y obtener una respuesta
                val response = ApiClient.apiService.crearIncidencia(incidenciaRequest)

                if (response.isSuccessful) {
                    val incidenciaResponse = response.body() // Obtiene el cuerpo de la respuesta
                    if (incidenciaResponse != null) {
                        // Muestra el mensaje de éxito
                        Toast.makeText(this@RegistrarIncidenciaActivity, incidenciaResponse.message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Error en la respuesta
                    Toast.makeText(this@RegistrarIncidenciaActivity, "Error al crear la incidencia", Toast.LENGTH_SHORT).show()
                }

            } catch (e: HttpException) {
                // Error en la solicitud HTTP
                Toast.makeText(this@RegistrarIncidenciaActivity, "Error en la solicitud", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                // Error de conexión o servidor
                Toast.makeText(this@RegistrarIncidenciaActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun obtenerFechaActual(): String {
        // Obtenemos la fecha actual con el formato esperado "yyyy-MM-dd"
        val formatoFecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatoFecha.format(Date())
    }
}