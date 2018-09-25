package jcip.chapter03;

import java.util.HashSet;
import java.util.Set;
import jcip.annotations.Immutable;

/**
 * @author mawenlong
 * @date 2018/09/25
 *
 * 程序清单3-11：在可变对象基础上构建的不可变类
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
}
