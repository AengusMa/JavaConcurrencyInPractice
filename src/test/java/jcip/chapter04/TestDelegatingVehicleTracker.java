package jcip.chapter04;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

/**
 * @author mawenlong
 * @date 2018/09/27
 */
public class TestDelegatingVehicleTracker {

  @Test
  public void test() {
    Point p = new Point(3, 4);
    Map<String, Point> map = new HashMap<>();
    map.put("test1", p);
    DelegatingVehicleTracker dt = new DelegatingVehicleTracker(map);
    System.out.println("原来位置：" + dt.getLocations().get("test1"));
    System.out.println("原来位置：" + dt.getLocation("test1"));

    dt.setLocation("test1", 8, 8);

    System.out.println("修改后位置：" + dt.getLocations().get("test1"));
    System.out.println("修改后位置：" + dt.getLocation("test1"));

  }
}
