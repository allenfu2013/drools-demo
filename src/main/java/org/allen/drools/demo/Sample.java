package org.allen.drools.demo;

import org.kie.api.io.ResourceType;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.definition.KnowledgePackage;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;

import java.util.Collection;

public class Sample {
    private static KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    private static Collection<KnowledgePackage> pkgs;
    private static KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
    private static StatefulKnowledgeSession ksession;

    public static void main(final String[] args) throws Exception {
        initDrools();
        fireRules();
    }

    private static void initDrools() throws Exception {

        long t1 = System.currentTimeMillis();
        kbuilder.add(ResourceFactory.newClassPathResource("sample.drl", Sample.class), ResourceType.DRL);
        long t2 = System.currentTimeMillis();
        System.out.println(t2-t1);


        // Check the builder for errors
        if (kbuilder.hasErrors()) {
            System.out.println(kbuilder.getErrors().toString());
            throw new RuntimeException("Unable to compile drl\".");
        }

        // get the compiled packages (which are serializable)
        pkgs = kbuilder.getKnowledgePackages();

        // add the packages to a knowledgebase (deploy the knowledge packages).
        kbase.addKnowledgePackages(pkgs);

        ksession = kbase.newStatefulKnowledgeSession();
    }

    private static void fireRules() {
        ksession.fireAllRules();
        ksession.dispose();
    }

}
