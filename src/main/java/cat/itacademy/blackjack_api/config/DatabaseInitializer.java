package cat.itacademy.blackjack_api.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;
import io.r2dbc.spi.ConnectionFactory;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final ConnectionFactory connectionFactory;

    @Override
    public void run(String... args) {
        try {
            log.info("Esperando para inicialización de base de datos...");

            Thread.sleep(5000);

            log.info("Iniciando inicialización de tablas...");

            ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
            initializer.setConnectionFactory(connectionFactory);

            ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
            populator.addScript(new ClassPathResource("schema.sql"));
            populator.addScript(new ClassPathResource("data.sql"));
            populator.setContinueOnError(true);

            initializer.setDatabasePopulator(populator);
            initializer.afterPropertiesSet();

            log.info("Base de datos inicializada correctamente");

        } catch (Exception e) {
            log.warn("Error durante inicialización de BD (puede ser normal si ya existe): {}", e.getMessage());

        }
    }
}