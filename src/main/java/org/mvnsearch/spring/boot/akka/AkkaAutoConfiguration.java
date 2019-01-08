package org.mvnsearch.spring.boot.akka;

import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

/**
 * Akka auto configuration for Spring
 *
 * @author linux_china
 */
@Configuration
@EnableConfigurationProperties(AkkaProperties.class)
public class AkkaAutoConfiguration implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    @Autowired
    private AkkaProperties akkaProperties;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean(destroyMethod = "terminate")
    public ActorSystem actorSystem() throws Exception {
        String actorSystemName = "akkaActorSystemSpring";
        Resource conf = akkaProperties.getConf();
        ActorSystem system;
        if (conf == null) {
            system = ActorSystem.create(actorSystemName);
        } else {
            Config config = ConfigFactory.parseURL(akkaProperties.getConf().getURL());
            system = ActorSystem.create(actorSystemName, config);
        }
        SpringAkkaExtension.SPRING_EXTENSION_PROVIDER.get(system).initialize(applicationContext);
        return system;
    }
}
