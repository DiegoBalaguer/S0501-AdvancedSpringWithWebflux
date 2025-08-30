package cat.itacademy.blackjack_api.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;
import io.r2dbc.spi.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);
    private final ConnectionFactory connectionFactory;

    public DatabaseInitializer(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void run(String... args) {
        try {
            logger.info("Esperando para inicialización de base de datos...");

            Thread.sleep(5000);

            logger.info("Iniciando inicialización de tablas...");

            ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
            initializer.setConnectionFactory(connectionFactory);

            ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
            populator.addScript(new ClassPathResource("schema.sql"));
            populator.addScript(new ClassPathResource("data.sql"));
            populator.setContinueOnError(true);

            initializer.setDatabasePopulator(populator);
            initializer.afterPropertiesSet();

            logger.info("Base de datos inicializada correctamente");

        } catch (Exception e) {
            logger.warn("Error durante inicialización de BD (puede ser normal si ya existe): {}", e.getMessage());

        }
    }
}