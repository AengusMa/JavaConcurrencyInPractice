package jcip.chapter02;

import jcip.annotations.NotThreadSafe;

/**
 * @author mawenlong
 * @date 2018/09/25
 *
 * 程序清单2-3：延迟初始化中的竞争条件（不要这么做）
 */
@NotThreadSafe
public class LazyInitRace {

  private ExpensiveObject instance = null;

  public ExpensiveObject getInstance() {
    if (instance == null) {
      instance = new ExpensiveObject();
    }
    return instance;
  }
}

class ExpensiveObject {

}