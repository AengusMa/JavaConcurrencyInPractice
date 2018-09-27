package jcip.chapter04;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import jcip.annotations.GuardedBy;
import jcip.annotations.ThreadSafe;

/**
 * @author mawenlong
 * @date 2018/09/26
 *
 * 程序清单4-4：基于监视器模式的车辆追踪,每次调用getLocation就要复制数据会大大降低性能
 */
@ThreadSafe
public class MonitorVehicleTracker {

  @GuardedBy("this")
  private final Map<String, MutablePoint> locations;

  public MonitorVehicleTracker(Map<String, MutablePoint> locations) {
    this.locations = deepCopy(locations);
  }

  public synchronized Map<String, MutablePoint> getLocations() {
    return deepCopy(locations);
  }

  public synchronized MutablePoint getLocation(String id) {
    MutablePoint loc = locations.get(id);
    return loc == null ? null : new MutablePoint(loc);
  }

  public synchronized void setLocation(String id, int x, int y) {
    MutablePoint loc = locations.get(id);
    if (loc == null) {
      throw new IllegalArgumentException("No such ID:" + id);
    }
    loc.x = x;
    loc.y = y;
  }

  private static Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> m) {
    Map<String, MutablePoint> result = new HashMap<>();
    for (String id : m.keySet()) {
      result.put(id, new MutablePoint(m.get(id)));
    }
    return Collections.unmodifiableMap(result);
  }
}
