package com.parquimetro.parquimetro_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.annotation.PostConstruct;

@Configuration
@EnableMongoRepositories(basePackages = "com.parquimetro.parquimetro_api.repositories")
@EnableMongoAuditing
public class MongoConfig {

    @Autowired
    private MongoClient mongoClient;

    @PostConstruct
    public void checkIndexes() {
        // Ensure indexes are created
        mongoClient.getDatabase("parquimetrodb")
                .listCollections()
                .forEach(collection -> {
                    String collectionName = collection.getString("name");
                    mongoClient.getDatabase("parquimetrodb")
                            .getCollection(collectionName)
                            .listIndexes()
                            .forEach(index -> {
                                System.out.println("Collection: " + collectionName +
                                        ", Index: " + index.toJson());
                            });
                });
    }
}