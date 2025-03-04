package unal.com.servidor.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.resps.ScanResult;
import unal.com.servidor.config.RedisConfig;
import unal.com.servidor.error.RouteStopException;
import unal.com.servidor.util.ErrorList;


import java.util.*;

@Repository
public class DataRespository implements IDataRespository {


  private final Jedis jedis;
  private final ErrorList errorList;

  @Autowired
  public DataRespository(RedisConfig redisConfig, ErrorList errorList) {
    this.jedis = redisConfig.getJedisResource();
    this.errorList = errorList;
  }

  @Override
  public Map<String, String> getServiceData(String key) {
    return jedis.hgetAll(key);
  }

  @Override
  public void updateServiceData(String key, Map<String, String> data) {
    jedis.hset(key, data);
  }

  @Override
  public void deleteServiceData(String key) {
    jedis.del(key);
  }

  @Override
  public void updateField(String key, String field, String value) {
    jedis.hset(key, field, value);
  }
  /**
   * Método que utiliza un pipeline para obtener los datos de múltiples claves.
   *
   * @return Lista de mapas de clave-valor para cada clave proporcionada.
   */

  @Override
  public List<Map<String, String>> getServiceDataFromRedisWithPipeline() throws RouteStopException {
    List<Map<String, String>> servicesDataList = new ArrayList<>();

    try {
      // Obtener todas las claves primero
      List<String> hashKeys = new ArrayList<>();
      String cursor = "0";

      do {
        // Escanear todas las claves de Redis
        ScanResult<String> scanResult = jedis.scan(cursor);
        for (String key : scanResult.getResult()) {
          // Verificar si la clave es de tipo hash antes de iniciar el pipeline
          if ("hash".equals(jedis.type(key))) {
            hashKeys.add(key);
          }
        }
        cursor = scanResult.getCursor();
      } while (!cursor.equals("0"));

      // Utilizar un pipeline para agrupar las operaciones de Redis
      Pipeline pipeline = jedis.pipelined();
      List<Response<Map<String, String>>> responses = new ArrayList<>();

      // Ejecutar hgetAll en todas las claves que son de tipo hash
      for (String key : hashKeys) {
        responses.add(pipeline.hgetAll(key));
      }

      // Ejecutar todas las operaciones en el pipeline
      pipeline.sync();

      // Procesar los resultados
      for (Response<Map<String, String>> response : responses) {
        servicesDataList.add(response.get());
      }

    } catch (Exception e) {
      // Manejar errores aquí si es necesario
      throw new RouteStopException("Interrupted while stopping route with id "+ e.getMessage() , errorList.createErrorList( "DataRespository","stopAndRemoveRoute", 500));
    }

    return servicesDataList;
  }

  @Override
  public Boolean existKey(String key) {
    return jedis.exists(key);
  }

  @Override
  public Set<String> getKeys(String pattern) {
    return jedis.keys(pattern);
  }

  @Override
  public void addToList(String key, String value) {
    jedis.rpush(key, value);
  }

  @Override
  public List<String> getList(String key) {
    if (!jedis.exists(key)) {

      return Collections.emptyList();
    }
    String type = jedis.type(key);


    if (!"list".equals(type)) {

      return Collections.emptyList();
    }

    long listSize = jedis.llen(key);


    if (listSize > 0) {
      return jedis.lrange(key, 0, -1);
    } else {
      return Collections.emptyList();
    }
  }

}

