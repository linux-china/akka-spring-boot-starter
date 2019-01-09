package org.mvnsearch.spring.boot.akka;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

import java.util.List;

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
    /**
     * actor provider: local, remote, cluster
     */
    private String actorProvider = "local";
    /**
     * akka remote local listen port
     */
    private Integer listenPort = -1;
    /**
     * cluster seed-nodes, such as akka.tcp://ClusterSystem@127.0.0.1:2551, akka.tcp://ClusterSystem@127.0.0.1:2552
     */
    private List<String> clusterSeedNodes;
    /**
     * akka extensions
     */
    private List<String> extensions;

    public Resource getConf() {
        return conf;
    }

    public void setConf(Resource conf) {
        this.conf = conf;
    }

    public String getActorProvider() {
        return actorProvider;
    }

    public void setActorProvider(String actorProvider) {
        this.actorProvider = actorProvider;
    }

    public Integer getListenPort() {
        return listenPort;
    }

    public void setListenPort(Integer listenPort) {
        this.listenPort = listenPort;
    }

    public List<String> getClusterSeedNodes() {
        return clusterSeedNodes;
    }

    public void setClusterSeedNodes(List<String> clusterSeedNodes) {
        this.clusterSeedNodes = clusterSeedNodes;
    }

    public List<String> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<String> extensions) {
        this.extensions = extensions;
    }
}
