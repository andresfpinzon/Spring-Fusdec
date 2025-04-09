package com.example.kotlinsql.services

import com.example.kotlinsql.dto.AsistenciaCreateRequest
import com.example.kotlinsql.dto.AsistenciaUpdateRequest
import com.example.kotlinsql.model.Asistencia
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Service

@Service
class AsistenciaService(private val jdbcTemplate: JdbcTemplate) {

    val rowMapper = RowMapper<Asistencia> { rs, _ ->
        Asistencia(
            id = rs.getInt("id"),
            titulo = rs.getString("titulo"),
            fecha = rs.getString("fecha"),
            usuarioId = rs.getString("usuario_id"),
            estado = rs.getBoolean("estado"),
            createdAt = rs.getString("created_at")
        )
    }

    fun obtenerTodas(): List<Asistencia> {
        return jdbcTemplate.query("SELECT * FROM asistencia", rowMapper)
    }

    fun crear(request: AsistenciaCreateRequest): Int {
        val sql = """
            INSERT INTO asistencia (titulo, fecha, usuario_id) 
            VALUES (?, ?, ?)
        """.trimIndent()
        return jdbcTemplate.update(sql, request.titulo, request.fecha, request.usuarioId)
    }

    fun actualizar(id: Int, request: AsistenciaUpdateRequest): Int {
        val campos = mutableListOf<String>()
        val valores = mutableListOf<Any>()

        request.titulo?.let { campos.add("titulo = ?"); valores.add(it) }
        request.fecha?.let { campos.add("fecha = ?"); valores.add(it) }
        request.estado?.let { campos.add("estado = ?"); valores.add(it) }

        if (campos.isEmpty()) return 0

        val sql = "UPDATE asistencia SET ${campos.joinToString(", ")} WHERE id = ?"
        valores.add(id)
        return jdbcTemplate.update(sql, *valores.toTypedArray())
    }

    fun eliminar(id: Int): Int {
        return jdbcTemplate.update("DELETE FROM asistencia WHERE id = ?", id)
    }
}
