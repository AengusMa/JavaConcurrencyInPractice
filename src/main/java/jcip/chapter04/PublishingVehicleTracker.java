package jcip.chapter04;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import jcip.annotations.ThreadSafe;

/**
 * @author mawenlong
 * @date 2018/09/27
 *
 * 程序清单4-12：安全发布底层状态的车辆追踪器
 */
@ThreadSafe
public class PublishingVehicleTracker {

  private final Map<String, SafePoint> locations;
  private final Map<String, SafePoint> unmodifiableMap;

  public PublishingVehicleTracker(Map<String, SafePoint> locations) {
    this.locations = new ConcurrentHashMap<>(locations);
    this.unmodifiableMap = Collections.unmodifiableMap(this.locations);
  }

  public Map<String, SafePoint> getLocations() {
    return unmodifiableMap;
  }

  public SafePoint getLocation(String id) {
    return locations.get(id);
  }

  public void setLocations(String id, int x, int y) {
    if (!locations.containsKey(id)) {
      throw new IllegalArgumentException("invalid vehicle name:" + id);
    }
    locations.get(id).set(x, y);
  }
}
