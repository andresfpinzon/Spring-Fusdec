package com.example.kotlinsql.controllers

import com.example.kotlinsql.dto.CertificadoCreateRequest
import com.example.kotlinsql.dto.CertificadoUpdateRequest
import com.example.kotlinsql.model.Certificado
import com.example.kotlinsql.services.CertificadoService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import io.swagger.v3.oas.annotations.*
import io.swagger.v3.oas.annotations.responses.*
import io.swagger.v3.oas.annotations.media.*

@RestController
@RequestMapping("/certificados")
class CertificadoController {

    @Autowired
    lateinit var certificadoService: CertificadoService

    @Operation(summary = "Obtener todos los certificados")
    @ApiResponse(
        responseCode = "200",
        description = "Lista de certificados",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = Certificado::class))]
    )
    @GetMapping
    fun obtenerCertificados(): List<Certificado> = certificadoService.obtenerTodos()

    @Operation(summary = "Crear nuevo certificado")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Certificado creado exitosamente",
            content = [Content(mediaType = "text/plain", examples = [ExampleObject(value = "Certificado creado correctamente")])]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Error en los datos",
            content = [Content(mediaType = "text/plain", examples = [ExampleObject(value = "Error al crear el certificado")])]
        )
    )
    @PostMapping
    fun crearCertificado(@Valid @RequestBody certificado: CertificadoCreateRequest): String {
        val resultado = certificadoService.crear(certificado)
        return if (resultado > 0) "Certificado creado correctamente" else "Error al crear el certificado"
    }

    @Operation(summary = "Actualizar certificado")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Certificado actualizado",
            content = [Content(mediaType = "text/plain", examples = [ExampleObject(value = "Certificado actualizado correctamente")])]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Certificado no encontrado",
            content = [Content(mediaType = "text/plain", examples = [ExampleObject(value = "No se realizaron cambios o el certificado no fue encontrado")])]
        )
    )
    @PutMapping("/{id}")
    fun actualizarCertificado(
        @PathVariable id: Int,
        @Valid @RequestBody certificado: CertificadoUpdateRequest
    ): String {
        val actualizado = certificadoService.actualizar(id, certificado)
        return if (actualizado > 0) "Certificado actualizado correctamente" else "No se realizaron cambios o el certificado no fue encontrado"
    }

    @Operation(summary = "Eliminar certificado")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Certificado eliminado",
            content = [Content(mediaType = "text/plain", examples = [ExampleObject(value = "Certificado eliminado")])]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Certificado no encontrado",
            content = [Content(mediaType = "text/plain", examples = [ExampleObject(value = "Certificado no encontrado")])]
        )
    )
    @DeleteMapping("/{id}")
    fun eliminarCertificado(@PathVariable id: Int): String {
        val resultado = certificadoService.eliminar(id)
        return if (resultado > 0) "Certificado eliminado" else "Certificado no encontrado"
    }
}
