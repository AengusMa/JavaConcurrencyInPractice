package jcip.chapter08.command;

/**
 * @author mawenlong
 * @date 2018/10/12
 *
 * 接收者角色类
 */
public class Receiver {

  /**
   * 真正执行命令相应的操作
   */
  public void action() {
    System.out.println("执行操作");
  }
}
