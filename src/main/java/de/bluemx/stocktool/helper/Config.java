package de.bluemx.stocktool.helper;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ConfigurationConverter;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
import java.util.Map;

@Singleton
public class Config {
    private Map<Object,Object> config;

    @Inject
    public Config() {
    }

    @Inject
    void init() {
        Configurations configs = new Configurations();
        try {
            Configuration config = configs.properties(new File("extract.properties"));
            // access configuration properties
            this.config = ConfigurationConverter.getMap(config);
        } catch (ConfigurationException cex) {
            // Something went wrong
        }
    }

    public Map<Object,Object> getConfig() {
        return config;
    }

}
