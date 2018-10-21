package jcip.chapter11;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import jcip.annotations.Immutable;

/**
 * @author mawenlong
 * @date 2018/10/21
 */
@Immutable
public class ThreeStooges {

  private final Set<String> stooges = new HashSet<>();

  public ThreeStooges() {
    stooges.add("Moe");
    stooges.add("Larry");
    stooges.add("Curly");
  }

  public boolean isStooge(String name) {
    return stooges.contains(name);
  }

  /**
   * 程序清单11-3：可通过锁消除优化去掉锁获取操作
   */
  public String getStoogeNames() {
    List<String> stooges = new Vector<String>();
    stooges.add("Moe");
    stooges.add("Larry");
    stooges.add("Curly");
    return stooges.toString();
  }
}
