package miseguridad.com.ui_

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.apisecurityapp.api.ApiClient
import com.example.apisecurityapp.model.UsuarioRequest
import com.example.apisecurityapp.model.Organizacion
import kotlinx.coroutines.launch
import miseguridad.com.R
import retrofit2.HttpException

class RegistrarUsuarioActivity : AppCompatActivity() {

    private lateinit var nombreEditText: EditText
    private lateinit var apellidosEditText: EditText
    private lateinit var fechaNacimientoEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var spinnerOrganization: Spinner
    private lateinit var spinnerTypeUser: Spinner
    private lateinit var registrarButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_usuario)

        // Vincula las vistas
        nombreEditText = findViewById(R.id.nombreEditText)
        apellidosEditText = findViewById(R.id.apellidosEditText)
        fechaNacimientoEditText = findViewById(R.id.fechaNacimientoEditText)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        spinnerOrganization = findViewById(R.id.spinnerOrganization)
        spinnerTypeUser = findViewById(R.id.spinnerTypeUser)
        registrarButton = findViewById(R.id.button)

        // Cargar datos en los spinners
        cargarOrganizaciones()
        cargarTiposUsuario()

        registrarButton.setOnClickListener {
            registrarUsuario()
        }
    }

    private fun cargarOrganizaciones() {
        lifecycleScope.launch {
            try {
                // Llamada a la API para obtener organizaciones
                val response = ApiClient.apiService.obtenerOrganizaciones() // Ajusta el endpoint
                if (response.isSuccessful) {
                    val organizaciones = response.body() ?: emptyList()
                    val nombresOrganizaciones = organizaciones.map { it.nombre }

                    // Crea el adaptador y asocia los nombres al spinner
                    val adapter = ArrayAdapter(this@RegistrarUsuarioActivity, android.R.layout.simple_spinner_item, nombresOrganizaciones)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerOrganization.adapter = adapter
                } else {
                    Toast.makeText(this@RegistrarUsuarioActivity, "Error al cargar organizaciones", Toast.LENGTH_SHORT).show()
                }
            } catch (e: HttpException) {
                Toast.makeText(this@RegistrarUsuarioActivity, "Error de red: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@RegistrarUsuarioActivity, "Error inesperado: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun cargarTiposUsuario() {
        // Simula datos de ejemplo
        val tiposUsuario = listOf("Admin", "Usuario")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tiposUsuario)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTypeUser.adapter = adapter
    }

    private fun registrarUsuario() {
        val nombre = nombreEditText.text.toString()
        val apellidos = apellidosEditText.text.toString()
        val fechaNacimiento = fechaNacimientoEditText.text.toString() // Formato esperado: "yyyy-MM-dd"
        val email = emailEditText.text.toString()
        val contrasena = passwordEditText.text.toString()
        val organizacionSeleccionada = spinnerOrganization.selectedItem.toString()

        // Aseguramos que Admin = 0 y Usuario = 1
        val perfil = when (spinnerTypeUser.selectedItem.toString()) {
            "Admin" -> 0
            "Usuario" -> 1
            else -> -1 // Valor inesperado
        }

        if (nombre.isEmpty() || apellidos.isEmpty() || email.isEmpty() || contrasena.isEmpty() || perfil == -1) {
            Toast.makeText(this, "Por favor completa todos los campos.", Toast.LENGTH_SHORT).show()
            return
        }

        // Busca la organización seleccionada
        lifecycleScope.launch {
            try {
                // Obtener lista de organizaciones desde la API
                val response = ApiClient.apiService.obtenerOrganizaciones()
                if (response.isSuccessful) {
                    val organizaciones = response.body() ?: emptyList()
                    // Encontrar la organización seleccionada por nombre
                    val organizacionResponse = organizaciones.find { it.nombre == organizacionSeleccionada }

                    if (organizacionResponse != null) {
                        // Crear el objeto UsuarioRequest con la organización seleccionada como OrganizacionResponse
                        val usuarioRequest = UsuarioRequest(
                            nombre = nombre,
                            apellidos = apellidos,
                            fechaNacimiento = fechaNacimiento,
                            email = email,
                            contrasena = contrasena,
                            organizacion = Organizacion(idorganizacion = 1),  // Aquí pasa el objeto OrganizacionResponse
                            perfil = perfil // 0 (Admin) o 1 (Usuario)
                        )
                        Log.d("RegistroUsuario", "Datos de usuario: ${usuarioRequest.toString()}")

                        enviarUsuario(usuarioRequest)
                    } else {
                        Toast.makeText(this@RegistrarUsuarioActivity, "Organización no encontrada.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@RegistrarUsuarioActivity, "Error al obtener organizaciones: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@RegistrarUsuarioActivity, "Error al registrar usuario: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun enviarUsuario(usuarioRequest: UsuarioRequest) {
        lifecycleScope.launch {
            try {
                val response = ApiClient.apiService.registrarUsuario(usuarioRequest)
                if (response.isSuccessful) {
                    Toast.makeText(this@RegistrarUsuarioActivity, "Usuario registrado exitosamente.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@RegistrarUsuarioActivity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: HttpException) {
                Toast.makeText(this@RegistrarUsuarioActivity, "Error: ${e.message()}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@RegistrarUsuarioActivity, "Usuario registrado exitosamente.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
