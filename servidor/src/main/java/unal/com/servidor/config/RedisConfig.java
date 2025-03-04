package unal.com.servidor.config;

import jakarta.annotation.PreDestroy;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Configuration
public class RedisConfig {

  Logger logger= org.slf4j.LoggerFactory.getLogger(RedisConfig.class);


  String redisHost = System.getenv().getOrDefault("REDIS_HOST", "host.docker.internal");
  String redisPassword = System.getenv().getOrDefault("REDIS_PASSWORD", "crypto");

  private JedisPool jedisPool;
  private int redisPort = 6379; // Puerto por defecto de Redis
  private int timeout = 2000;

  public RedisConfig() {
    configureJedisPool();
  }

  private void configureJedisPool() {
    GenericObjectPoolConfig<Jedis> poolConfig = new GenericObjectPoolConfig<>();
    // Configura el pool según tus necesidades
    poolConfig.setMaxTotal(10); // Número máximo de conexiones en el pool
    poolConfig.setMaxIdle(5);   // Número máximo de conexiones inactivas
    poolConfig.setMinIdle(1);   // Número mínimo de conexiones inactivas

    jedisPool = new JedisPool(poolConfig, redisHost, redisPort, timeout, redisPassword);
    logger.info("Conectado a Redis en {}:{}", redisHost, redisPort);
  }

  public Jedis getJedisResource() {
    return jedisPool.getResource();
  }

  @PreDestroy
  public void close() {
    if (jedisPool != null) {
      jedisPool.close();
      logger.info("Conexión a Redis cerrada.");
    }
  }
}

