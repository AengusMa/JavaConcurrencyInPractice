package jcip.chapter08.audioplayer;

/**
 * @author mawenlong
 * @date 2018/10/13
 */
public class RewindCommand implements Command {

  private AudioPlayer myAudio;

  public RewindCommand(AudioPlayer audioPlayer) {
    myAudio = audioPlayer;
  }

  @Override
  public void execute() {
    myAudio.rewind();
  }

}