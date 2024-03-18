package com.hamilton.network

/**
 * Esta clase sellada representa la respuesta de un servicio, firebase, API REST, etc. donde se
 * declara la clase Error que almacenará los errores de la infraestructura y el caso de éxito
 * en la clase Success
 */
sealed class Resource {
    data class Success<T>(var data : T) : Resource()
    data class Error(var exception : Exception) : Resource()

}