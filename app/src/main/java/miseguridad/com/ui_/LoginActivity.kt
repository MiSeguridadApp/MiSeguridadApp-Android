package miseguridad.com.ui_

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.apisecurityapp.api.ApiClient
import com.example.apisecurityapp.model.LoginRequest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import miseguridad.com.R

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa tu usuario y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Log.d("LoginActivity", "Usuario: $username , $password" )

            val loginRequest = LoginRequest(username, password)
            loginUser(loginRequest)
        }
    }

    private fun loginUser(loginRequest: LoginRequest) {
        lifecycleScope.launch {
            try {
                Log.d("LoginActivity", "Enviando solicitud a la API...")

                // Realizar la solicitud a la API y obtener un Response<LoginResponse>
                val response = ApiClient.apiService.login(loginRequest)

                // Verificar si la respuesta fue exitosa
                if (response.isSuccessful) {
                    val loginResponse = response.body() // Obtiene el cuerpo de la respuesta

                    // Verificar si el cuerpo de la respuesta no es null
                    if (loginResponse != null) {
                        // Verifica si el mensaje indica un login exitoso
                        if (loginResponse.message == "Combinación de nombre de usuario/contraseña no válida") {
                            // Si el mensaje de la respuesta es un error (login fallido)
                            Log.e("LoginActivity", "Login fallido: ${loginResponse.message}")
                            Toast.makeText(this@LoginActivity, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                        } else {
                            // Login exitoso
                            Log.d("LoginActivity", "Login exitoso: ${loginResponse.message}")
                            Toast.makeText(this@LoginActivity, "Login exitoso", Toast.LENGTH_SHORT).show()

                            // Guardar el ID del usuario en SharedPreferences
                            val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putInt("idusuario", loginResponse.userId)  // Guarda el ID del usuario
                            editor.apply()  // Aplica los cambios

                            // Navegar a HomeActivity (o la actividad que corresponda)
                            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                            finish()  // Finaliza el LoginActivity para que no regrese al login
                        }
                    } else {
                        // Si la respuesta no contiene un cuerpo válido
                        Log.e("LoginActivity", "Respuesta vacía o nula")
                        Toast.makeText(this@LoginActivity, "Error en la respuesta", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Si la respuesta no fue exitosa (por ejemplo, error 401 o 404)
                    Log.e("LoginActivity", "Login fallido: ${response.message()}")
                    Toast.makeText(this@LoginActivity, "Error en la solicitud", Toast.LENGTH_SHORT).show()
                }

            } catch (e: HttpException) {
                Log.e("LoginActivity", "Error HTTP: ${e.message()}")
                Toast.makeText(this@LoginActivity, "Error en la solicitud", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e("LoginActivity", "Error de conexión o servidor: ${e.message}")
                Toast.makeText(this@LoginActivity, "Error de conexión o servidor", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

