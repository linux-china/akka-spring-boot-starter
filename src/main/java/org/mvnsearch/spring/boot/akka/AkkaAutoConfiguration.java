package org.mvnsearch.spring.boot.akka;

import akka.actor.ActorSystem;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Akka auto configuration for Spring
 *
 * @author linux_china
 */
@Configuration
public class AkkaAutoConfiguration implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean(destroyMethod = "terminate")
    public ActorSystem actorSystem() {
        ActorSystem system = ActorSystem.create("akka-spring-starter");
        SpringAkkaExtension.SPRING_EXTENSION_PROVIDER.get(system).initialize(applicationContext);
        return system;
    }
}
