package org.example.framework.config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:config.properties"})
public interface EnvironmentConfig extends Config {

    @Key("base.url")
    String baseUrl();
}
