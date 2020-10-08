package com.danyjavierb.droolsfunctional.domain.materias


data class Materia @JvmOverloads constructor(
        val id: Int,
        val nombre: String,
        val notas: List<Float>,
        val promedio: Number?

) {
    companion object
}

enum class Progreso {
    BUENO, MALO, REGULAR, PERFECTO
}

data class ResponseProgreso(
        val nombre: String,
        val progreso: Progreso,
        val promedio: Float?
) {
    override fun toString(): String {
        return "Materia: ${this.nombre}, Progreso: ${this.progreso.name}"
    }
}