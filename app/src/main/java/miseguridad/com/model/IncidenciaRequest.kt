package com.example.apisecurityapp.model

data class IncidenciaRequest(
    val idusuario: Int,
    val fecha: String,
    val ubicacion: String,
    val estado: Int,
    val tipo: String
)
