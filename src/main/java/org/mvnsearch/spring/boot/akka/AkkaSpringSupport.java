package org.mvnsearch.spring.boot.akka;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Akka spring support to fetch Actor
 *
 * @author linux_china
 */
public class AkkaSpringSupport {
    @Autowired
    private ActorSystem actorSystem;

    protected ActorRef actorOf(Class<? extends Actor> actorClass) {
        return actorSystem.actorOf(SpringAkkaExtension.SPRING_EXTENSION_PROVIDER.get(actorSystem).props(actorClass), actorClass.getCanonicalName());
    }
}
