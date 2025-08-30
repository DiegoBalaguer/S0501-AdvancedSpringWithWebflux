package cat.itacademy.blackjack_api.config.connections;

import cat.itacademy.blackjack_api.config.properties.MongoDbProperties;
import cat.itacademy.blackjack_api.config.properties.MySqlProperties;
import cat.itacademy.blackjack_api.config.properties.SSHProperties;
import com.jcraft.jsch.*;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Service;

@Service
public class SSHManagerService {

    private static final Logger logger = LogManager.getLogger(SSHManagerService.class.getName());
        private final SSHProperties sshProp;
    private final MySqlProperties mySqlProp;
    private final MongoDbProperties mongoDbProp;

    // SSH connection
    private final boolean sshIsEnable;
    private final String sshHost;
    private final int sshPort;
    private final String sshUser;
    private final String sshPassword;
    private final String sshPrivateKeyPath;
    private final String sshPrivateKeyPassword;

    // MySql connection
    private final boolean mysqlSshIsEnable;
    private final int mysqlLocalPort;
    private final String mysqlHost;
    private final int mysqlRemotePort;

    // Mongodb connection
    private final boolean mongoDbSshIsEnable;
    private final int mongoDbLocalPort;
    private final String mongoDbHost;
    private final int mongoDbRemotePort;

    private Session session;
    private JSch jsch;

    public SSHManagerService(SSHProperties sshProperties, MySqlProperties mySqlProperties, MongoDbProperties mongoDbProperties) {
        this.sshProp = sshProperties;
        this.mySqlProp = mySqlProperties;
        this.mongoDbProp = mongoDbProperties;

        // SSH connection
        sshIsEnable = sshProp.isEnable();
        sshHost = sshProp.getHost();
        sshPort = sshProp.getHostPort();
        sshUser = sshProp.getUser();
        sshPassword = sshProp.getPassword();
        sshPrivateKeyPath = sshProp.getPrivateKeyPath();
        sshPrivateKeyPassword = sshProp.getPrivateKeyPassword();

        // MySql connection
        mysqlSshIsEnable = mySqlProp.isSshEnable();
        mysqlLocalPort = mySqlProp.getLocalPort();
        mysqlHost = mySqlProp.getHost();
        mysqlRemotePort = mySqlProp.getRemotePort();

        // Mongodb connection
        mongoDbSshIsEnable = mongoDbProp.isSshEnable();
        mongoDbLocalPort = mongoDbProp.getLocalPort();
        mongoDbHost = mongoDbProp.getHost();
        mongoDbRemotePort = mongoDbProp.getRemotePort();
    }

    @PostConstruct
    public void startSSHTunnel() {
        if (!sshIsEnable) {
            return;
        }
        try {
            jsch = new JSch();

            session = jsch.getSession(sshUser, sshHost, sshPort);
            session.setPassword(sshPassword);
            session.setConfig("StrictHostKeyChecking", "no");
            logger.info("Conectando al servidor SSH {}:{} con usuario {}", sshHost, sshPort, sshUser);
            session.connect();
            logger.info("Conexión SSH establecida.");

            //MySql ssh connection
            if (mysqlSshIsEnable) {
                int assignedPort = session.setPortForwardingL(mysqlLocalPort, mysqlHost, mysqlRemotePort);
                logger.info("Túnel SSH establecido: localhost:{} -> {}:{}", assignedPort, mysqlHost, mysqlRemotePort);
            }

            //MongoDb ssh connection
            if (mongoDbSshIsEnable) {
                int assignedPortMongoDb = session.setPortForwardingL(mongoDbLocalPort, mongoDbHost, mongoDbRemotePort);
                logger.info("Túnel SSH establecido: localhost:{} -> {}:{}", assignedPortMongoDb, mongoDbHost, mongoDbRemotePort);
            }
        } catch (JSchException e) {
            logger.error("Error al iniciar el túnel SSH: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to start SSH tunnel", e);
        }
    }

    @PreDestroy
    public void stopSSHTunnel() {
        if (session != null && session.isConnected()) {
            session.disconnect();
            logger.info("SSH Session Closed");
        }
    }
}
