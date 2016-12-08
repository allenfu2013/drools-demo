# drools 规则引擎

规则文件既可以是drl文件，也可以是xml文件。下面以drl文件为例：

## rule

* 属性部分
* 条件部分 LHS
* 结果部分 RHS

```xml
package org.allen.drools

import org.allen.drools.domain.Message;

rule "rule1"
    salience 1 //属性部分
    when
       //条件不符  LHS
       Message()
    then
       //结果部分 RHS
       System.out.println("Hello Drools");
end
```

## kmodule.xml

kmodule.xml文件存放在src/main/resources/META-INF/文件夹下。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<kmodule xmlns="http://jboss.org/kie/6.0.0/kmodule">
    <kbase name="rules" packages="rules">
        <ksession name="ksession-rules"/>
    </kbase>
    <kbase name="dtables" packages="dtables">
        <ksession name="ksession-dtables"/>
    </kbase>
</kmodule>
```

* 一个kmodule里面包含了多个kbase
* 每一个kbase都有一个name，可以取任意字符串，但是不能重名。
* 然后都有一个packages，可以看到packages里面的字符串其实就是src/main/resources下面的文件夹的名称，或者叫包名，规则引擎会根据这里定义的包来查找规则定义文件。可以同时定义多个包，以逗号分隔开来就行。
* 每一个kbase下面可以包含多个ksession，当然本例中都只定义了一个。
* 每一个ksession都有一个name，名字也可以是任意字符串，但是也不能重复。
* kbase和ksession里面的name属性是全局不能重复的。
* kbase和ksession中其实还有很多其它的属性

## FireAllRules

*