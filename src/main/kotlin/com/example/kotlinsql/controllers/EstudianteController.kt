package com.example.kotlinsql.controllers

import com.example.kotlinsql.dto.EstudianteCreateRequest
import com.example.kotlinsql.dto.EstudianteUpdateRequest
import com.example.kotlinsql.model.Estudiante
import com.example.kotlinsql.services.EstudianteService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/estudiantes")
class EstudianteController {

    @Autowired
    lateinit var estudianteService: EstudianteService

    @Operation(summary = "Obtener todos los estudiantes", description = "Devuelve una lista de todos los estudiantes registrados.")
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "Lista de estudiantes",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Estudiante::class))]
        )]
    )
    @GetMapping
    fun obtenerTodos(): List<Estudiante> = estudianteService.obtenerTodos()

    @Operation(summary = "Crear estudiante", description = "Crea un nuevo estudiante con los datos proporcionados.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Estudiante creado exitosamente",
                content = [Content(mediaType = "text/plain", examples = [ExampleObject(value = "Estudiante creado correctamente")])]),
            ApiResponse(responseCode = "400", description = "Datos inválidos",
                content = [Content(mediaType = "text/plain", examples = [ExampleObject(value = "Error al crear el estudiante")])])
        ]
    )
    @PostMapping
    fun crear(@Valid @RequestBody request: EstudianteCreateRequest): String {
        val filas = estudianteService.crear(request)
        return if (filas > 0) "Estudiante creado correctamente" else "Error al crear el estudiante"
    }

    @Operation(summary = "Actualizar estudiante", description = "Actualiza los datos de un estudiante.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Estudiante actualizado exitosamente",
                content = [Content(mediaType = "text/plain", examples = [ExampleObject(value = "Estudiante actualizado correctamente")])]),
            ApiResponse(responseCode = "404", description = "Estudiante no encontrado",
                content = [Content(mediaType = "text/plain", examples = [ExampleObject(value = "No se realizaron cambios o el estudiante no fue encontrado")])])
        ]
    )
    @PutMapping("/{documento}")
    fun actualizar(@PathVariable documento: String, @Valid @RequestBody request: EstudianteUpdateRequest): String {
        val filas = estudianteService.actualizar(documento, request)
        return if (filas > 0) "Estudiante actualizado correctamente" else "No se realizaron cambios o el estudiante no fue encontrado"
    }

    @Operation(summary = "Eliminar estudiante", description = "Elimina un estudiante por su número de documento.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Estudiante eliminado",
                content = [Content(mediaType = "text/plain", examples = [ExampleObject(value = "Estudiante eliminado correctamente")])]),
            ApiResponse(responseCode = "404", description = "Estudiante no encontrado",
                content = [Content(mediaType = "text/plain", examples = [ExampleObject(value = "Estudiante no encontrado")])])
        ]
    )
    @DeleteMapping("/{documento}")
    fun eliminar(@PathVariable documento: String): String {
        val filas = estudianteService.eliminar(documento)
        return if (filas > 0) "Estudiante eliminado correctamente" else "Estudiante no encontrado"
    }
}
