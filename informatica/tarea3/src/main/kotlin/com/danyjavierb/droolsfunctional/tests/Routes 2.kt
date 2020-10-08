package com.danyjavierb.droolsfunctional.tests

import com.danyjavierb.droolsfunctional.domain.materias.*
import com.danyjavierb.droolsfunctional.infra.persistence.MateriasRepository
import com.danyjavierb.droolsfunctional.infra.rules.ProgresoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class Materias2Controller(
        private val materiasRepository: MateriasRepository,
        private val progresoService: ProgresoService
) {

    fun getProgreso (avg :Float): Progreso {
        if( avg >= 5) return Progreso.PERFECTO
        else if (avg < 5 && avg >= 4) return Progreso.BUENO
        else if ( avg < 4 && avg >= 3) return Progreso.REGULAR
        else return Progreso.MALO
    }

    @GetMapping("api/progreso")
    fun getProgresoMateria(): ResponseEntity<String> {

        return object : GetMateriasUseCase {
            override val getMaterias = materiasRepository::getMaterias
        }.run {
            GetMateriasCommand.runUseCase()
        }.map {
           ResponseEntity.ok( it.map { ResponseProgreso(
                   it.nombre,
                   getProgreso(it.notas.toList().average().toFloat())
                  ,it.notas.toList().average().toFloat())
            }.toList().joinToString())
        }.unsafeRunSync()
    }
    @GetMapping("api/materias")
    fun getMateriasJson(): ResponseEntity<List<Materia>> {

        return object : GetMateriasUseCase {
            override val getMaterias = materiasRepository::getMaterias
        }.run {
            GetMateriasCommand.runUseCase()
        }.map {
            ResponseEntity.ok( it)
        }.unsafeRunSync()
    }
}