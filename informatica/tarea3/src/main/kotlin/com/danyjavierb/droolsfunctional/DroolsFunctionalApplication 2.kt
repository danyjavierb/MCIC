package com.danyjavierb.droolsfunctional

import com.danyjavierb.droolsfunctional.infra.persistence.MateriasRepository
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.kie.api.KieServices
import org.kie.api.builder.KieBuilder
import org.kie.api.builder.KieFileSystem
import org.kie.api.builder.KieModule
import org.kie.api.builder.KieRepository
import org.kie.api.runtime.KieContainer
import org.kie.internal.io.ResourceFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@SpringBootApplication
class DroolsFunctionalApplication: WebMvcConfigurer {

    private val drlFiles = arrayOf("progreso.drl")
    private val kieServices = KieServices.Factory.get()

    @Bean
    fun materiasRepository(mapper:ObjectMapper) = MateriasRepository(mapper)

    @Bean
    fun objectMapper(): ObjectMapper? {
        val mapper = jacksonObjectMapper()
        mapper.registerKotlinModule()
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true)
        return mapper
    }

    @Bean
    fun kieContainer(): KieContainer? {
        val kieRepository: KieRepository = kieServices.repository

        kieRepository.addKieModule { kieRepository.defaultReleaseId }
        val kieServices: KieServices = KieServices.Factory.get()
        //Load Rules and Ecosystem Definitions
        val kieFileSystem: KieFileSystem = kieServices.newKieFileSystem()
        for (ruleFile in drlFiles) {
            kieFileSystem.write(ResourceFactory.newClassPathResource(ruleFile))
        }
        //Generate Modules and all internal Structures
        val kieBuilder: KieBuilder = kieServices.newKieBuilder(kieFileSystem)
        kieBuilder.buildAll()

        val kieModule: KieModule = kieBuilder.kieModule
        return kieServices.newKieContainer(kieModule.releaseId)
    }

}
fun main(args: Array<String>) {
    runApplication<DroolsFunctionalApplication>(*args)
}

