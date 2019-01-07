package org.mvnsearch.spring.boot.akka;

import akka.actor.AbstractExtensionId;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.actor.Props;
import org.springframework.context.ApplicationContext;

/**
 * Spring Akka extension
 *
 * @author linux_china
 */
public class SpringAkkaExtension extends AbstractExtensionId<SpringAkkaExtension.SpringExt> {
    public static final SpringAkkaExtension SPRING_EXTENSION_PROVIDER = new SpringAkkaExtension();

    @Override
    public SpringExt createExtension(ExtendedActorSystem system) {
        return new SpringExt();
    }

    public static class SpringExt implements Extension {
        private volatile ApplicationContext applicationContext;

        public void initialize(ApplicationContext applicationContext) {
            this.applicationContext = applicationContext;
        }

        public Props props(Class actorClass) {
            return Props.create(
                    SpringActorProducer.class, applicationContext, actorClass);
        }

    }
}