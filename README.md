Spring Boot Starter Akka
========================
Spring Boot 2.0 starter for Akka.

### How to use

* please add following dependency in your pom.xml
```xml
          <dependency>
                 <groupId>org.mvnsearch.spring.boot</groupId>
                 <artifactId>akka-spring-boot-starter</artifactId>
                 <version>1.0.0-SNAPSHOT</version>
          </dependency>
```

* Write Akka Actor like spring with @ActorComponent

```
 @ActorComponent
 public class GreetingActor extends AbstractActor {
     @Autowired
     private GreetingService greetingService;
 
     @Override
     public Receive createReceive() {
         return receiveBuilder()
                 .match(String.class, text -> {
                     System.out.println(greetingService.greet(text));
                 })
                 .build();
     }
 
 }
```

* Add actor ref in init method

```
@RestController
public class PortalController extends AkkaSpringSupport {
    private ActorRef greetingActor;

    @PostConstruct
    public void init() {
        greetingActor = actorOf(GreetingActor.class);
    }

    @RequestMapping("/hello/{name}")
    public String hello(@PathVariable(name = "name") String name) {
        greetingActor.tell(name, ActorRef.noSender());
        return "sent";
    }
}
```

### Akka configuration

Configuration in application.properties will replace settings in Akka configuration file.

* Akka Actor
```properties
akka.actor-provider=local
```

* Akka Remote
```properties
akka.actor-provider=remote
akka.listen-port=2552
```

* Akka Cluster
```
akka.actor-provider=cluster
akka.listen-port=0
akka.cluster-seed-nodes=akka.tcp://ClusterSystem@127.0.0.1:2551,akka.tcp://ClusterSystem@127.0.0.1:2552
akka.extensions=akka.cluster.client.ClusterClientReceptionist
```
* Akka configuration file
```
akka.conf=classpath:/application.hocon
```

### FAQ

#####  How to set Akka configuration?
Please add following setting in your application.properties

```
akka.conf=classpath:/application.conf
```

### References

* Akka Quickstart with Java: https://developer.lightbend.com/guides/akka-quickstart-java/index.html
* Akka Chinese document: https://doc.yonyoucloud.com/doc/akka-doc-cn/2.3.6/scala/book/chapter1/01_what_is_akka.html
* Introduction to Spring with Akka: https://www.baeldung.com/akka-with-spring
* Reactive Programming with Akka: https://dzone.com/refcardz/reactive-programming-akka?chapter=1
