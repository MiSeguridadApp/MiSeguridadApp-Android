package com.example.apisecurityapp.model

import java.util.Date

data class UsuarioRequest(
    val nombre: String,
    val apellidos: String,
    val fechaNacimiento: String, // Usar formato "yyyy-MM-dd"
    val email: String,
    val contrasena: String,
    val organizacion: Organizacion, // Modelo anidado
    val perfil: Int // 1: Admin, 2: Usuario
){
    override fun toString(): String {
        return "UsuarioRequest(nombre='$nombre', apellidos='$apellidos', fechaNacimiento='$fechaNacimiento', email='$email', password='$contrasena', organizacion='$organizacion', perfil='$perfil')"
    }
}

data class Organizacion(
    val idorganizacion: Long
)
