package org.mvnsearch.spring.boot.akka;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import org.springframework.context.ApplicationContext;

/**
 * Spring actor producer
 *
 * @author linux_china
 */
public class SpringActorProducer implements IndirectActorProducer {

    private ApplicationContext applicationContext;
    private Class<? extends Actor> actorClass;

    public SpringActorProducer(ApplicationContext applicationContext, Class<? extends Actor> actorClass) {
        this.applicationContext = applicationContext;
        this.actorClass = actorClass;
    }

    @Override
    public Actor produce() {
        return applicationContext.getBean(actorClass);
    }

    @Override
    public Class<? extends Actor> actorClass() {
        return actorClass;
    }
}
