package de.adorsys.pop.example.config;

import javax.annotation.PostConstruct;

import org.adorsys.encobject.filesystem.FsPersistenceFactory;
import org.adorsys.encobject.serverdata.JcloudConstants;
import org.adorsys.envutils.EnvProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JcloudConfig {
	public static final String APP_NAME = "popexample";

	private FsPersistenceFactory persFactory;

	@PostConstruct
	public void postConstruct() {
		String baseDir = EnvProperties.getEnvOrSysProp(JcloudConstants.JCLOUD_FS_PERSISTENCE_DIR, "./target/"+APP_NAME);
		persFactory = new FsPersistenceFactory(baseDir);
	}
	
    @Bean
	public FsPersistenceFactory getPersFactory() {
		return persFactory;
	}
}
