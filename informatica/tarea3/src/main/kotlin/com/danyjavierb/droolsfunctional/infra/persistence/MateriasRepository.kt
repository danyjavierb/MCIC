package com.danyjavierb.droolsfunctional.infra.persistence

import arrow.fx.IO
import com.danyjavierb.droolsfunctional.domain.materias.Materia
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File



open class MateriasRepository(private val mapper:ObjectMapper){

    fun getMaterias(): IO<List<Materia>> = IO {
        mapper.readValue<List<Materia>>(   File("./src/main/resources/static/materias.json").readText(Charsets.UTF_8))
    }
}