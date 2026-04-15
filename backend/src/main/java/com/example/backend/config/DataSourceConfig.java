package com.example.backend.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

//@Configuration
//@Profile("!test")
public class DataSourceConfig {

    private static final String TRUSTSTORE_PASSWORD = "aivenchangeit";

    @Bean
    @Primary
    public DataSource dataSource(
            @Value("${spring.datasource.url}") String jdbcUrl,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password:}") String password
    ) throws Exception {
        if (password == null || password.isBlank()) {
            throw new IllegalStateException(
                    "Set environment variable SPRING_DATASOURCE_PASSWORD to the Aiven MySQL password."
            );
        }
        Path truststoreFile = buildTruststoreFromClasspathPem();

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.addDataSourceProperty("sslMode", "VERIFY_CA");
        config.addDataSourceProperty("trustCertificateKeyStoreUrl", truststoreFile.toUri().toString());
        config.addDataSourceProperty("trustCertificateKeyStorePassword", TRUSTSTORE_PASSWORD);
        config.addDataSourceProperty("trustCertificateKeyStoreType", "PKCS12");

        return new HikariDataSource(config);
    }

    private static Path buildTruststoreFromClasspathPem() throws Exception {
        Path truststoreFile = Files.createTempFile("aiven-truststore-", ".p12");
        truststoreFile.toFile().deleteOnExit();

        try (InputStream pem = new ClassPathResource("ssl/aiven-ca.pem").getInputStream()) {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate ca = (X509Certificate) cf.generateCertificate(pem);
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(null, null);
            ks.setCertificateEntry("aiven-ca", ca);
            try (OutputStream os = Files.newOutputStream(truststoreFile)) {
                ks.store(os, TRUSTSTORE_PASSWORD.toCharArray());
            }
        }
        return truststoreFile;
    }
}

