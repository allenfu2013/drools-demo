package org.allen.drools;

import org.allen.drools.domain.Message;
import org.allen.drools.utils.KnowledgeSessionHelper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

public class Test1 {

    StatelessKieSession sessionStateless = null;
    KieSession sessionStateful = null;
    static KieContainer kieContainer;

    @BeforeClass
    public static void beforeClass() {
        kieContainer = KnowledgeSessionHelper.createRuleBase();
    }

    @Test
    public void test() {
        sessionStateful = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "ksession-rules");
        Message message = new Message();
        message.setStatus(Message.HELLO);
        message.setMessage("hello drools");
        sessionStateful.insert(message);
        sessionStateful.fireAllRules();
    }
}
