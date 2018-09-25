package jcip.chapter03;

/**
 * @author mawenlong
 * @date 2018/09/25
 *
 * 程序清单3-14：在没有足够同步的情况下发布对象（不要这么做）
 */
public class StuffIntoPublic {

  public Holder holder;

  public void initialize() {
    holder = new Holder(42);
  }
}
