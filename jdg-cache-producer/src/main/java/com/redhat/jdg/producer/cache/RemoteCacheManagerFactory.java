package com.redhat.jdg.producer.cache;

import java.io.IOException;

import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.marshall.ProtoStreamMarshaller;
import org.infinispan.protostream.SerializationContext;
import org.infinispan.protostream.annotations.ProtoSchemaBuilder;
import org.infinispan.protostream.annotations.ProtoSchemaBuilderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Import POJO here......
import com.redhat.jdg.pojo.Product;

/**
 * Factory for {@link RemoteCacheManager RemoteCacheManagers}.
 * 
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 *
 */
public class RemoteCacheManagerFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(RemoteCacheManagerFactory.class);
	
	private static final String HOTROD_HOST;

	private static final String HOTROD_PORT;


	
	// Load the db driver class.
	static {
		HOTROD_HOST = System.getProperty("hotrod.hostname");
		HOTROD_PORT = System.getProperty("hotrod.port");
	}
	public RemoteCacheManager getRemoteCacheFactory(String className) {
		
		// HotRod ConfigurationBuilder.
		ConfigurationBuilder cb = new ConfigurationBuilder();
		// Make sure to register the ProtoStreamMarshaller.
		cb.addServer().host(HOTROD_HOST).port(Integer.parseInt(HOTROD_PORT)).marshaller(new ProtoStreamMarshaller());

		RemoteCacheManager rcm = new RemoteCacheManager(cb.build());

		SerializationContext serCtx = ProtoStreamMarshaller.getSerializationContext(rcm);

		ProtoSchemaBuilder protoSchemaBuilder = new ProtoSchemaBuilder();
		try {
			Class clazz = Class.forName(className);
			
			String generatedSchema = protoSchemaBuilder.fileName(clazz.getSimpleName()+".proto")
					.packageName(clazz.getPackage().getName())
					.addClass(clazz).build(serCtx);
		} catch (ProtoSchemaBuilderException psbe) {
			String message = "Error building ProtoBuf Schema.";
			LOGGER.error(message, psbe);
			throw new RuntimeException(message, psbe);
		} catch (ClassNotFoundException cnf) {
			String message = "Class not found";
			LOGGER.error(message, cnf);
			throw new RuntimeException(message, cnf);			
		} catch (IOException ioe) {
			String message = "I/O error while registering ProtoBufs.";
			LOGGER.error(message, ioe);
			throw new RuntimeException(message, ioe);
		}

		return rcm;
	}

}
