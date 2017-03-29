package com.redhat.jdg.producer;

import java.util.Map;

import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.commons.api.BasicCache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.jdg.producer.cache.RemoteCacheManagerFactory;
import com.redhat.jdg.producer.db.DbConnectionFactory;

import com.redhat.jdg.pojo.Product;
import com.redhat.jdg.producer.repository.ProductRepository;

/**
 * Loads {@link Product} data from a <code>PostgreSQL</code> database and stores the data in
 * <code>ProtoBuf</code> format in <code>JBoss Data Grid</code>.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 * @author <a href="mailto:cojan.van.ballegooijen@redhat.com">Cojan van Ballegooijen</a>
 */
public class CacheDataProducer {

	private static final Logger LOGGER = LoggerFactory.getLogger(CacheDataProducer.class);

	private static final String ISPN_CACHE_NAME;

	private static final DbConnectionFactory dbConnectionFactory = new DbConnectionFactory();

	private static RemoteCacheManager rcm = null;

	private static final RemoteCacheManagerFactory rcmFactory = new RemoteCacheManagerFactory();

	private static final ProductRepository productrepo = new ProductRepository();
	
	// Read system properties.
	static {
		ISPN_CACHE_NAME=System.getProperty("cache.name");
	}
	

	public static void main(String[] args) {
		LOGGER.info("Loading data from database and storing in JBoss Data Grid.");

		Map<String, Product> products = productrepo.getProduct();
		LOGGER.info("Found " + products.size() + " rows.");
		LOGGER.info("Pushing data to Infinspan.");
		
		putInIspn("com.redhat.jdg.pojo.Product",ISPN_CACHE_NAME, products);
		
		LOGGER.info("Data stored in JBoss Data Grid.");
	}

	/**
	 * Puts the data in the given {@link Map} in <code>JBoss Data Grid</code>
	 * 
	 * 
	 * @param cacheName
	 * 
	 * @param keyValues
	 */
	private static <T> void putInIspn(String clazzName,String cacheName, Map<String, T> keyValues) {

		RemoteCacheManager remoteCacheManager = rcmFactory.getRemoteCacheFactory(clazzName);
		BasicCache<String, T> cache = remoteCacheManager.getCache(cacheName);

		keyValues.forEach((key, value) -> {
			cache.put(key, value);
		});
		remoteCacheManager.stop();
	}

}
