package jcip.chapter08.audioplayer;

/**
 * @author mawenlong
 * @date 2018/10/13
 *
 * 抽象命令角色类
 */
public interface Command {

  /**
   * 执行方法
   */
  public void execute();
}
