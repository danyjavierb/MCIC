package com.danyjavierb.droolsfunctional.http.materias

import com.danyjavierb.droolsfunctional.domain.materias.*
import com.danyjavierb.droolsfunctional.infra.persistence.MateriasRepository
import com.danyjavierb.droolsfunctional.infra.rules.ProgresoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class MateriasController(
        private val materiasRepository: MateriasRepository,
        private val progresoService: ProgresoService
) {

    @GetMapping("/progreso")
    fun getProgresoMateria(): ResponseEntity<String> {

        return object : GetMateriasUseCase {
            override val getMaterias = materiasRepository::getMaterias
        }.run {
            GetMateriasCommand.runUseCase()
        }.map {
            ResponseEntity.ok(progresoService.executeRules(it).joinToString())
        }.unsafeRunSync()
    }
}