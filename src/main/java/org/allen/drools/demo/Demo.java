package org.allen.drools.demo;

import org.allen.drools.domain.Message;
import org.kie.api.io.ResourceType;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderError;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;

import java.util.ArrayList;
import java.util.List;

public class Demo {

    public static void main(String[] args) {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource("sample.drl", Sample.class), ResourceType.DRL);
        kbuilder.add(ResourceFactory.newClassPathResource("sample2.drl", Sample.class), ResourceType.DRL);

        KnowledgeBuilderErrors errors = kbuilder.getErrors();
        //编译规则过程中发现规则是否有错误
        if (errors.size() > 0) {
            for (KnowledgeBuilderError error : errors) {
                System.out.println("规则中存在错误，错误消息如下：");
                System.err.println(error);
            }
            throw new IllegalArgumentException("Could not parse knowledge.");
        }

        System.out.println("package size=" + kbuilder.getKnowledgePackages().size());

        // 创建规则构建库
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        // 构建器加载的资源文件包放入构建库
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

        StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();

        Message message = new Message();
        message.setMessage("hello world");
        message.setStatus(Message.HELLO);

        List<Message> messageList = new ArrayList<Message>();
        ksession.setGlobal("messageList", messageList);

        ksession.insert(message);
        ksession.fireAllRules();

        System.out.println("messageList size=" + messageList.size());
    }


}
