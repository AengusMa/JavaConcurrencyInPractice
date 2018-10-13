package jcip.chapter08.audioplayer;

/**
 * @author mawenlong
 * @date 2018/10/13
 */
public class StopCommand implements Command {

  private AudioPlayer myAudio;

  public StopCommand(AudioPlayer audioPlayer) {
    myAudio = audioPlayer;
  }

  @Override
  public void execute() {
    myAudio.stop();
  }

}
