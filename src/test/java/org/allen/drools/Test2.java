package org.allen.drools;

import com.alibaba.fastjson.JSON;
import org.allen.drools.domain.Message;
import org.allen.drools.utils.KnowledgeSessionHelper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.rule.FactHandle;

public class Test2 {

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
        sessionStateful.addEventListener(new RuleRuntimeEventListener() {
            @Override
            public void objectInserted(ObjectInsertedEvent objectInsertedEvent) {
                System.out.println("Object inserted, obj: " + JSON.toJSONString(objectInsertedEvent.getObject()));
            }

            @Override
            public void objectUpdated(ObjectUpdatedEvent objectUpdatedEvent) {
                System.out.println("Object was updated, new obj: " + JSON.toJSONString(objectUpdatedEvent.getObject()));
            }

            @Override
            public void objectDeleted(ObjectDeletedEvent objectDeletedEvent) {
                System.out.println("Object retracted, old obj: " + JSON.toJSONString(objectDeletedEvent.getOldObject()));
            }
        });

        Message message = new Message();
        message.setStatus(Message.HELLO);
        message.setMessage("hello drools");
        FactHandle handle = sessionStateful.insert(message);
        message.setMessage("good bye");
        sessionStateful.update(handle, message);
        sessionStateful.fireAllRules();

        sessionStateful.delete(handle);
        sessionStateful.fireAllRules();
    }
}
