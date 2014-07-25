package com.stratio.connector.mongodb.core.configuration;


import java.util.List;

import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.stratio.connector.meta.ConfigurationImplem;
import com.stratio.meta.common.connector.IConfiguration;
import com.stratio.meta.common.exceptions.InitializationException;


/**
 * The configuration for Mongo.
 */
 
public class MongoClientConfiguration implements IConfiguration{
	
	/**
     * The MongoClient options
     */
	private MongoClientOptions clientOptions = null;
	
	/**
     * List of servers (mongos or replica set members)
     */
	private List<String> seeds = null;

	

	public MongoClientConfiguration(IConfiguration iconfiguration) throws InitializationException{
		ConfigurationImplem configuration = (ConfigurationImplem) iconfiguration;
		configureSeeds(configuration);
		configureClientOptions(configuration);
	}


	public void configureSeeds (ConfigurationImplem configuration) throws InitializationException{
		
		if( (seeds = configuration.getSeeds()) == null ){
			throw new InitializationException("there is no seeds");
		}

	}

	public void configureClientOptions(ConfigurationImplem configuration) {

		int acceptableLatencyDifference = configuration.exist("mongo.acceptableLatencyDifference") ? Integer.decode(configuration.getProperty("mongo.acceptableLatencyDifference")) : 15;
		int maxConnectionsPerHost = configuration.exist("mongo.maxConnectionsPerHost") ? Integer.decode(configuration.getProperty("mongo.maxConnectionsPerHost")) : 100;
		int connectTimeout = configuration.exist("mongo.connectTimeout") ? Integer.decode(configuration.getProperty("mongo.connectTimeout")) : 10000;
		int maxConnectionIdleTime = configuration.exist("mongo.maxConnectionIdleTime") ? Integer.decode(configuration.getProperty("mongo.maxConnectionIdleTime")) : 0; //por defecto 0 sinlimite?? seguro??


		ReadPreference readPreference = ReadPreference.primary();
		if(configuration.exist("mongo.readPreference")){
			final String tagReadPreference = configuration.getProperty("mongo.readPreference");
			if( ! tagReadPreference.equalsIgnoreCase("primary")){
				if(tagReadPreference.equalsIgnoreCase("primaryPreferred"))  ReadPreference.primaryPreferred();	
				else if(tagReadPreference.equalsIgnoreCase("secondary"))  ReadPreference.secondary();
				else if(tagReadPreference.equalsIgnoreCase("secondaryPreferred"))  ReadPreference.secondaryPreferred();
				else if(tagReadPreference.equalsIgnoreCase("nearest"))  ReadPreference.nearest();
			}
		}

		WriteConcern writeConcern = WriteConcern.ACKNOWLEDGED;
		if(configuration.exist("mongo.writeConcern")){
			
			final String tagWriteConcern = configuration.getProperty("mongo.writeConcern");
			if( ! tagWriteConcern.equalsIgnoreCase("acknowledged")){
				if(tagWriteConcern.equalsIgnoreCase("UNACKNOWLEDGED"))  writeConcern = WriteConcern.UNACKNOWLEDGED;	
				else if(tagWriteConcern.equalsIgnoreCase("REPLICA_ACKNOWLEDGED"))   writeConcern = WriteConcern.REPLICA_ACKNOWLEDGED;	
				else if(tagWriteConcern.equalsIgnoreCase("JOURNALED"))   writeConcern = WriteConcern.JOURNALED;	
			}
			

		//MongoClientOptions
		clientOptions = new MongoClientOptions.Builder()
		.acceptableLatencyDifference(acceptableLatencyDifference)
		.connectionsPerHost(maxConnectionsPerHost)
		.connectTimeout(connectTimeout)
		.maxConnectionIdleTime(maxConnectionIdleTime)
		.readPreference(readPreference)
		.writeConcern(writeConcern)
		.build();

		//ListaCredenciales=>siempre trata de conectar con ellas=>si no necesario =>error

		//setReadPreference=>isConnected depende de esto=> que de el error si no se puede...
			
			
		}
		
	//setWriteConcern
	//	WriteConcern.UNACKNOWLEDGED Write operations that use this write concern will return as soon as the message is written to the socket
	//	WriteConcern.ACKNOWLEDGED	Write operations that use this write concern will return as soon as the message is written to the socket
	//	WriteConcern.REPLICA_ACKNOWLEDGED	Tries to write to two separate nodes. Same as the above, but will throw an exception if two writes are not possible.
	//	WriteConcern.JOURNALED	Same as WriteConcern.ACKNOWLEDGED, but also waits for write to be written to the journal.

		
	}

	public MongoClientOptions getMongoClientOptions() {
		return clientOptions;
	}

	public List<String> getSeeds(){
		return seeds;
	}
	
	//	public  ConnectorConfigurationOptions getConnectorConfigurationOptions(){
	//		
	//	}

}



