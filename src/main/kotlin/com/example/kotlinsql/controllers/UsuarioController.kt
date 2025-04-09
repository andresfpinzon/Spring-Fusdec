package com.example.kotlinsql.controllers

import com.example.kotlinsql.dto.UsuarioCreateRequest
import com.example.kotlinsql.dto.UsuarioUpdateRequest
import com.example.kotlinsql.model.Usuario
import com.example.kotlinsql.services.UsuarioService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema



@RestController


class UsuarioController {

    @Autowired
    lateinit var usuarioService: UsuarioService


    @Operation(summary = "Obtener todos los usuarios", description = "Devuelve una lista de todos los usuarios registrados.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Lista de usuarios",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = Usuario::class))]
            )
        ]
    )
    @GetMapping
    fun obtenerUsuarios(): List<Usuario> {
        return usuarioService.obtenerTodos()
    }


    @Operation(
        summary = "Crear nuevo usuario",
        description = "Crea un nuevo usuario con los datos proporcionados."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Usuario creado exitosamente",
                content = [Content(mediaType = "text/plain", examples = [ExampleObject(value = "Usuario creado correctamente")])]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Error en los datos enviados",
                content = [Content(mediaType = "text/plain", examples = [ExampleObject(value = "Error al crear el usuario")])]
            )
        ]
    )
    @PostMapping
    fun crearUsuario(@Valid @RequestBody usuario: UsuarioCreateRequest): String {
        val resultado = usuarioService.crear(usuario)
        return if (resultado > 0) "Usuario creado correctamente" else "Error al crear el usuario"
    }



    @Operation(
        summary = "Actualizar un usuario",
        description = "Actualiza los datos de un usuario existente según su número de documento."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Usuario actualizado exitosamente",
                content = [Content(mediaType = "text/plain", examples = [ExampleObject(value = "Usuario actualizado correctamente")])]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Usuario no encontrado o sin cambios",
                content = [Content(mediaType = "text/plain", examples = [ExampleObject(value = "No se realizaron cambios o el usuario no fue encontrado")])]
            )
        ]
    )
    @PutMapping("/{documento}")
    fun actualizarUsuario(
        @PathVariable documento: String,
        @Valid @RequestBody usuario: UsuarioUpdateRequest
    ): String {
        val filasAfectadas = usuarioService.actualizar(documento, usuario)
        return if (filasAfectadas > 0)
            "Usuario actualizado correctamente"
        else
            "No se realizaron cambios o el usuario no fue encontrado"
    }

    @Operation(
        summary = "Eliminar un usuario",
        description = "Elimina un usuario de la base de datos según su número de documento."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Usuario eliminado exitosamente",
                content = [Content(mediaType = "text/plain", examples = [ExampleObject(value = "Usuario eliminado")])]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Usuario no encontrado",
                content = [Content(mediaType = "text/plain", examples = [ExampleObject(value = "Usuario no encontrado")])]
            )
        ]
    )
    @DeleteMapping("/{documento}")
    fun eliminarUsuario(@PathVariable documento: String): String {
        val resultado = usuarioService.eliminarPorDocumento(documento)
        return if (resultado > 0) "Usuario eliminado" else "Usuario no encontrado"
    }
}
