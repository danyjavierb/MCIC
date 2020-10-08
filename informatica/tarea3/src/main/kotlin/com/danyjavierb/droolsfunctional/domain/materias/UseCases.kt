package com.danyjavierb.droolsfunctional.domain.materias

import arrow.core.Option
import arrow.fx.IO
import arrow.fx.extensions.fx

object GetMateriasCommand
// modulo dependencias
typealias GetMaterias = () -> IO<List<Materia>>


interface GetMateriasUseCase {
    val getMaterias: GetMaterias

    fun GetMateriasCommand.runUseCase(): IO<List<Materia>> = getMaterias()
}