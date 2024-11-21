package com.example.apisecurityapp.api

import com.example.apisecurityapp.model.IncidenciaRequest
import com.example.apisecurityapp.model.IncidenciaResponse
import com.example.apisecurityapp.model.LoginRequest
import com.example.apisecurityapp.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login") // Cambia la URL según sea necesario
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>


    @POST("api/incidencia") // Cambia la URL según el endpoint correcto
    suspend fun crearIncidencia(@Body incidenciaRequest: IncidenciaRequest): Response<IncidenciaResponse>
}