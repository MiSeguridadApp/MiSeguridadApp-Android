package com.example.apisecurityapp.model

data class IncidenciaResponse(
    val id: Long,
    val idusuario: Int,
    val fecha: String,  // Puede ser LocalDate en lugar de String, dependiendo de la estructura de tu backend
    val ubicacion: String,
    val estado: Int,
    val tipo: String
)
