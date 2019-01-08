package org.mvnsearch.spring.boot.akka;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

/**
 * Akka properties
 *
 * @author linux_china
 */
@ConfigurationProperties(
        prefix = "akka"
)
public class AkkaProperties {
    /**
     * akka configuration, such as classpath:/akka-actor-system-1.conf
     */
    private Resource conf;


    public Resource getConf() {
        return conf;
    }

    public void setConf(Resource conf) {
        this.conf = conf;
    }
}
