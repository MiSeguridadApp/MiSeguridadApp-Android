package com.example.apisecurityapp.api

import com.example.apisecurityapp.model.IncidenciaRequest
import com.example.apisecurityapp.model.IncidenciaResponse
import com.example.apisecurityapp.model.LoginRequest
import com.example.apisecurityapp.model.LoginResponse
import com.example.apisecurityapp.model.Organizacion
import com.example.apisecurityapp.model.OrganizacionRequest
import com.example.apisecurityapp.model.OrganizacionResponse
import com.example.apisecurityapp.model.UsuarioRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login") // Cambia la URL según sea necesario
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>


    @POST("api/incidencias") // Cambia la URL según el endpoint correcto
    suspend fun crearIncidencia(@Body incidenciaRequest: IncidenciaRequest): Response<IncidenciaResponse>

    @GET("api/incidencias")
    suspend fun obtenerIncidencias(): Response<List<IncidenciaResponse>>

    @POST("api/organizaciones") // Cambia la URL según el endpoint correcto
    suspend fun registrarOrganizacion(@Body organizacionRequest: OrganizacionRequest): Response<OrganizacionResponse>

    @GET("api/organizaciones")
    suspend fun obtenerOrganizaciones(): Response<List<OrganizacionResponse>>


    @POST("api/usuarios") // Ajusta según tu endpoint
    suspend fun registrarUsuario(@Body usuarioRequest: UsuarioRequest): Response<String>
}