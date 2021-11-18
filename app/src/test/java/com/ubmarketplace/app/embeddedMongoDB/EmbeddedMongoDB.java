package com.ubmarketplace.app.embeddedMongoDB;

import com.mongodb.client.MongoClients;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import lombok.Getter;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Random;

import static com.ubmarketplace.app.TestStatic.TEST_MONGO_DATABASE_NAME;
import static com.ubmarketplace.app.TestStatic.TEST_MONGO_URL;

public class EmbeddedMongoDB {
    // Just in case you need a backup plan, you can use this class to get an EmbeddedMongoDB
    // To start:
    // embeddedMongoDB = new EmbeddedMongoDB()
    // mongoTemplate = embeddedMongoDB.getMongoTemplate()
    // To stop:
    // embeddedMongoDB.stop()

    private MongodExecutable mongodExecutable;
    private final Random random = new Random();

    @Getter
    private MongoTemplate mongoTemplate;

    public EmbeddedMongoDB() throws Exception {
        initialize("localhost", randomPort());
    }

    public EmbeddedMongoDB(String ip, int port) throws Exception {
        initialize(ip, port);
    }

    public void stop() {
        mongodExecutable.stop();
    }

    public void initialize(String ip, int port) throws Exception {
        ImmutableMongodConfig mongodConfig = MongodConfig
                .builder()
                .version(Version.Main.DEVELOPMENT)
                .net(new Net(ip, port, Network.localhostIsIPv6()))
                .build();

        MongodStarter starter = MongodStarter.getDefaultInstance();
        mongodExecutable = starter.prepare(mongodConfig);
        mongodExecutable.start();
        mongoTemplate = new MongoTemplate(MongoClients.create(String.format(TEST_MONGO_URL, ip, port)), TEST_MONGO_DATABASE_NAME);
    }

    public int randomPort() {
        int minPort = 1024;
        int maxPort = 49151;
        return random.nextInt(maxPort - minPort) + minPort;
    }
}
