package jcip.chapter08.audioplayer;


/**
 * @author mawenlong
 * @date 2018/10/13
 *
 * 系统需要一个代表宏命令的接口，以定义出具体宏命令所需要的接口。
 *
 * 宏命令<br/> 所谓宏命令简单点说就是包含多个命令的命令，是一个命令的组合。
 *
 * 设想茱丽的录音机有一个记录功能，可以把一个一个的命令记录下来，再在任何需要的时候重新把这些记录下来的命令一次性执行，
 *
 * 这就是所谓的宏命令集功能。因此，茱丽的录音机系统现在有四个键，分别为播音、倒带、停止和宏命令功能。
 *
 * 此时系统的设计与前面的设计相比有所增强，主要体现在Julia类现在有了一个新方法，用以操作宏命令键。
 */
public interface MacroCommand extends Command {

  /**
   * 宏命令聚集的管理方法 可以添加一个成员命令
   */
  public void add(Command cmd);

  /**
   * 宏命令聚集的管理方法 可以删除一个成员命令
   */
  public void remove(Command cmd);
}
