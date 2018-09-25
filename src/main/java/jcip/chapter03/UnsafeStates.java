package jcip.chapter03;

import java.util.Arrays;

/**
 * @author mawenlong
 * @date 2018/09/25
 *
 * 程序清单3-6：使内部的可变状态逸出（不要这么做）
 */
public class UnsafeStates {

  private String[] states = new String[]{"AK", "AL"};

  public String[] getStates() {
    return states;
  }

  public static void main(String[] args) {
    UnsafeStates safe = new UnsafeStates();
    System.out.println(Arrays.toString(safe.getStates()));
    safe.getStates()[1] = "c";
    System.out.println(Arrays.toString(safe.getStates()));

  }
}
