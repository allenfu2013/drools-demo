package org.allen.drools.demo;

import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.definition.KnowledgePackage;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;

import java.io.*;
import java.util.Collection;

/**
 * Created by allen on 16/8/19.
 */
public class Sample2 {

    private static KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    private static Collection<KnowledgePackage> pkgs;
    private static KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
    private static StatefulKnowledgeSession ksession;

    public static void main(String[] args) throws Exception {
        InputStream is = Sample2.class.getResourceAsStream("/sample.drl");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String ruleText = "";
        String line = null;
        while ((line = br.readLine()) != null) {
            ruleText += line + "\n";
        }
        System.out.println(ruleText);
        long t3 = System.currentTimeMillis();
        Resource myResource = ResourceFactory.newReaderResource(new StringReader(ruleText));
        kbuilder.add(myResource, ResourceType.DRL);
        long t4 = System.currentTimeMillis();
        System.out.println(t4-t3);

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

        ksession.fireAllRules();
        ksession.dispose();
    }
}
