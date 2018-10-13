package jcip.chapter08.audioplayer;

/**
 * @author mawenlong
 * @date 2018/10/13
 *
 * 具体命令角色类
 */
public class PlayCommand implements Command {

  private AudioPlayer myAudio;

  public PlayCommand(AudioPlayer audioPlayer) {
    myAudio = audioPlayer;
  }

  /**
   * 执行方法
   */
  @Override
  public void execute() {
    myAudio.play();
  }

}
