package com.example.apisecurityapp.model

data class IncidenciaRequest(
    val idusuario: Int,
    val ubicacion: String,
    val estado: Int = 1,
    val tipo: String
)
