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

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @PostConstruct
    public void init() {
        //todo register actors on cluster
        //applicationContext.getBean()
    }

    @Bean(destroyMethod = "terminate")
    public ActorSystem actorSystem() throws Exception {
        String actorSystemName = "akkaActorSystemSpring";
        Resource conf = akkaProperties.getConf();
        ActorSystem system;
        if (conf == null) {
            system = ActorSystem.create(actorSystemName);
        } else {
            system = ActorSystem.create(actorSystemName, combinedConfig());
        }
        SpringAkkaExtension.SPRING_EXTENSION_PROVIDER.get(system).initialize(applicationContext);
        return system;
    }

    /**
     * combine config from config file & settings
     *
     * @return AKKA config
     * @throws Exception exception
     */
    private Config combinedConfig() throws Exception {
        List<String> configItems = new ArrayList<>();
        if (akkaProperties.getActorProvider() != null) {
            configItems.add("akka.actor.provider=\"" + akkaProperties.getActorProvider() + "\"");
            if (!akkaProperties.getActorProvider().equals("local")) {
                configItems.add("akka.remote.netty.tcp.port=" + akkaProperties.getListenPort());
            }
        }
        if (akkaProperties.getClusterSeedNodes() != null && !akkaProperties.getClusterSeedNodes().isEmpty()) {
            configItems.add("akka.cluster.seed-nodes=" + akkaProperties.getClusterSeedNodes().stream()
                    .map(s -> "\"" + s + "\"")
                    .collect(Collectors.joining(",", "[", "]")));
        }
        if (akkaProperties.getExtensions() != null && !akkaProperties.getExtensions().isEmpty()) {
            configItems.add("akka.extensions=" + akkaProperties.getExtensions().stream()
                    .map(s -> "\"" + s + "\"")
                    .collect(Collectors.joining(",", "[", "]")));
        }
        Config appConfig = ConfigFactory.parseString(String.join("\r", configItems));
        if (akkaProperties.getConf() != null) {
            Config regularConfig = ConfigFactory.parseURL(akkaProperties.getConf().getURL());
            appConfig = appConfig.withFallback(regularConfig);
        }
        return appConfig;
    }
}
