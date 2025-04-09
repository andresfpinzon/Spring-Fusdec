package com.example.kotlinsql.controllers

import com.example.kotlinsql.dto.CalificacionCreateRequest
import com.example.kotlinsql.dto.CalificacionUpdateRequest
import com.example.kotlinsql.model.Calificacion
import com.example.kotlinsql.services.CalificacionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.*
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/calificaciones")
class CalificacionController {

    @Autowired
    lateinit var calificacionService: CalificacionService

    @Operation(summary = "Obtener todas las calificaciones", description = "Devuelve una lista de todas las calificaciones registradas.")
    @ApiResponse(
        responseCode = "200",
        description = "Lista de calificaciones",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = Calificacion::class))]
    )
    @GetMapping
    fun obtenerTodas(): List<Calificacion> = calificacionService.obtenerTodas()

    @Operation(summary = "Crear una nueva calificación")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Calificación creada correctamente",
            content = [Content(mediaType = "text/plain", examples = [ExampleObject(value = "Calificación creada correctamente")])]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Datos inválidos",
            content = [Content(mediaType = "text/plain", examples = [ExampleObject(value = "Error al crear calificación")])]
        )
    )
    @PostMapping
    fun crear(@Valid @RequestBody request: CalificacionCreateRequest): String {
        val res = calificacionService.crear(request)
        return if (res > 0) "Calificación creada correctamente" else "Error al crear calificación"
    }

    @Operation(summary = "Actualizar una calificación por ID")
    @PutMapping("/{id}")
    fun actualizar(@PathVariable id: Int, @Valid @RequestBody request: CalificacionUpdateRequest): String {
        val res = calificacionService.actualizar(id, request)
        return if (res > 0) "Calificación actualizada correctamente" else "No se realizaron cambios o ID no encontrado"
    }

    @Operation(summary = "Eliminar una calificación por ID")
    @DeleteMapping("/{id}")
    fun eliminar(@PathVariable id: Int): String {
        val res = calificacionService.eliminar(id)
        return if (res > 0) "Calificación eliminada correctamente" else "Calificación no encontrada"
    }
}
