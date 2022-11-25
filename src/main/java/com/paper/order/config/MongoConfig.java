package com.paper.order.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.mongodb.MongoClientURI;
import com.mongodb.MongoClient;

@Configuration
@EnableMongoAuditing
public class MongoConfig {

	@Value("${spring.data.mongodb.uri}")
	private String uri;

	@Value("${spring.data.mongodb.database}")
	private String database;

	protected String getDatabaseName() {
		return this.database;
	}

	public MongoClient mongoClient() {
		MongoClientURI mongoClientURI = new MongoClientURI(uri);
		return new MongoClient(mongoClientURI);
	}

	@Primary
	@Bean("mongoMappingContext")
	public MongoMappingContext mongoMappingContext() {
		MongoMappingContext mappingContext = new MongoMappingContext();
		return mappingContext;
	}

	@Primary
	@Bean("mongoDbFactory")
	public MongoDatabaseFactory mongoDbFactory() {
		return new SimpleMongoClientDatabaseFactory(this.uri + this.getDatabaseName());
	}

	@Primary
	@Bean("mappingMongoConverter")
	public MappingMongoConverter mappingMongoConverter(@Qualifier("mongoDbFactory") MongoDatabaseFactory mongoDbFactory,
			@Qualifier("mongoMappingContext") MongoMappingContext mongoMappingContext) throws Exception {
		DefaultDbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory);
		MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));
		return converter;
	}

	@Primary
	@Bean(name = "mongoTemplate")
	public MongoTemplate getMongoTemplate(@Qualifier("mongoDbFactory") MongoDatabaseFactory mongoDbFactory)
			throws Exception {
		return new MongoTemplate(mongoDbFactory);
	}
}
