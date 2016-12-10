package org.allen.drools.utils;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

public class KnowledgeSessionHelper {

    public static KieContainer createRuleBase() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kieContainer = ks.getKieClasspathContainer();
        return kieContainer;
    }

    // StatelessKieSession隔离了每次与规则引擎的交互，不会维护会话的状态
    public static StatelessKieSession getStatelessKnowledgeSession(KieContainer kieContainer, String sessionName) {
        StatelessKieSession kieSession = kieContainer.newStatelessKieSession(sessionName);
        return kieSession;
    }

    // KieSession会在多次与规则引擎进行交换中，维护会话的状态
    public static KieSession getStatefulKnowledgeSession(KieContainer kieContainer, String sessionName) {
        KieSession kieSession = kieContainer.newKieSession(sessionName);
        return kieSession;
    }
}
