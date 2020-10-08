package com.danyjavierb.droolsfunctional.infra.rules


import com.danyjavierb.droolsfunctional.domain.materias.Materia
import com.danyjavierb.droolsfunctional.domain.materias.ResponseProgreso
import org.drools.core.impl.KnowledgeBaseFactory
import org.kie.api.KieServices
import org.kie.api.io.ResourceType
import org.kie.api.runtime.KieContainer
import org.kie.internal.builder.KnowledgeBuilderFactory
import org.kie.internal.io.ResourceFactory
import org.springframework.stereotype.Service


@Service
class ProgresoService(private val kieContainer:KieContainer) {
    private val kieServices = KieServices.Factory.get()
    fun executeRules(materias:List<Materia>):List<ResponseProgreso> {

        val kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder()
        kbuilder.add(ResourceFactory.newClassPathResource("progreso.drl"), ResourceType.DRL)

        val kbase = KnowledgeBaseFactory.newKnowledgeBase()
        kbase.addPackages(kbuilder.knowledgePackages)

        val kieSession = kbase.newKieSession();
        materias.forEach {
            kieSession.insert(it);
        }
        kieSession.fireAllRules();
        val responses = kieSession.getObjects (){
            it is ResponseProgreso
        }
        kieSession.dispose();
       return responses.map {
           it as ResponseProgreso
       }
    }
}