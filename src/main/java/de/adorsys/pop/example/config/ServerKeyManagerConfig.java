package de.adorsys.pop.example.config;

import javax.annotation.PostConstruct;

import org.adorsys.encobject.filesystem.FsPersistenceFactory;
import org.adorsys.encobject.serverdata.AbstractServerKeyManagerConfig;
import org.adorsys.jjwk.serverkey.ServerKeyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ServerKeyManagerConfig extends AbstractServerKeyManagerConfig {

	@Autowired
	private FsPersistenceFactory persFactory;

    @Bean
    public ServerKeyManager getServerKeyManager() {
        return serverKeyManager;
    }

	@Override
	protected FsPersistenceFactory getFsPersistenceFactory() {
		return persFactory;
	}
	
    /**
     * Read the masterId and master password from environment properties.
     * <p>
     * If this information is not available, we will generate it and store it a a common location
     * where all server can read.
     * <p>
     * In order for the server to be restarted, we will need those information either as part
     * of the environment properties, or available on a dedicated file system.
     */
    @PostConstruct
    public void postConstruct() {
    	initServerKeyManager(JcloudConfig.APP_NAME);
    }
    
}
