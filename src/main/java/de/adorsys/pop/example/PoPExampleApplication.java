package de.adorsys.pop.example;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Security;

import org.adorsys.envutils.EnvProperties;
import org.adorsys.jjwk.serverkey.ServerKeyPropertiesConstants;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
public class PoPExampleApplication {

	public static void main(String[] args) throws UnknownHostException {
		turnOffEncPolicy();

		Security.addProvider(new BouncyCastleProvider());

		String keystorePassword = EnvProperties.getEnvOrSysProp(ServerKeyPropertiesConstants.KEYSTORE_PASSWORD, true);
		if (StringUtils.isBlank(keystorePassword)) {
			keystorePassword = RandomStringUtils.randomAlphanumeric(16);
			System.setProperty(ServerKeyPropertiesConstants.KEYSTORE_PASSWORD, keystorePassword);
			System.setProperty(ServerKeyPropertiesConstants.RESET_KEYSTORE, "true");
			LoggerFactory.getLogger(PoPExampleApplication.class)
					.info("Newly generated Keystore Password: " + keystorePassword);
		}

		ConfigurableApplicationContext app = SpringApplication.run(PoPExampleApplication.class, args);
		ConfigurableEnvironment env = app.getEnvironment();
		String protocol = "http";
		if (env.getProperty("server.ssl.key-store") != null) {
			protocol = "https";
		}
		LoggerFactory.getLogger(PoPExampleApplication.class)
				.info("\n----------------------------------------------------------\n\t"
						+ "Application '{}' is running! Access URLs:\n\t" + "Local: \t\t{}://localhost:{}\n\t"
						+ "External: \t{}://{}:{}\n\t"
						+ "Profile(s): \t{}\n----------------------------------------------------------",
						env.getProperty("spring.application.name", "Multibanking Mock"), protocol,
						env.getProperty("server.port", "8080"), protocol, InetAddress.getLocalHost().getHostAddress(),
						env.getProperty("server.port", "8080"), env.getActiveProfiles());

	}

	public static void turnOffEncPolicy() {
		// Warning: do not do this for productive code. Download and install the
		// jce unlimited strength policy file
		// see
		// http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html
		try {
			Field field = Class.forName("javax.crypto.JceSecurity").getDeclaredField("isRestricted");
			field.setAccessible(true);
			field.set(null, java.lang.Boolean.FALSE);
		} catch (ClassNotFoundException | NoSuchFieldException | SecurityException | IllegalArgumentException
				| IllegalAccessException ex) {
			ex.printStackTrace(System.err);
		}
	}

}
