package jcip.chapter04;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import jcip.annotations.ThreadSafe;

/**
 * @author mawenlong
 * @date 2018/09/27
 *
 * 程序清单4-7：将线程安全委托给ConcurrentHashMap
 */
@ThreadSafe
public class DelegatingVehicleTracker {

  private final ConcurrentMap<String, Point> locations;
  private final Map<String, Point> unmodifiableMap;

  public DelegatingVehicleTracker(Map<String, Point> points) {
    locations = new ConcurrentHashMap<>(points);
    unmodifiableMap = Collections.unmodifiableMap(locations);
  }

  public Map<String, Point> getLocations() {
    return unmodifiableMap;
  }

  public Point getLocation(String id) {
    return locations.get(id);
  }

  public void setLocation(String id, int x, int y) {
    if (locations.replace(id, new Point(x, y)) == null) {
      throw new IllegalArgumentException("invalid vehicle name: " + id);
    }
  }

  /**
   * 程序清单4-8：返回locations的静态拷贝而非实时拷贝
   */
  public Map<String, Point> getLocationsAsStatic() {
    return Collections.unmodifiableMap(
        new HashMap<String, Point>(locations));
  }
}
