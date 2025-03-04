package unal.com.servidor.repository;



import unal.com.servidor.error.RouteStopException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IDataRespository {
  Map<String, String> getServiceData(String key);
  void updateServiceData(String key, Map<String, String> data);
  void deleteServiceData(String key);
  void updateField(String key, String field, String value);
  List<Map<String, String>> getServiceDataFromRedisWithPipeline() throws RouteStopException;
  Boolean existKey(String key);
  Set<String> getKeys (String pattern);
  void addToList(String key, String value);
  List<String> getList(String key);

}
