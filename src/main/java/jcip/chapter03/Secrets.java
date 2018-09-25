package jcip.chapter03;

import java.util.HashSet;
import java.util.Set;

/**
 * @author mawenlong
 * @date 2018/09/25
 *
 * 程序清单3-5：发布对象
 */
public class Secrets {

  public static Set<Secret> knownSecrets;

  public void initialize() {
    knownSecrets = new HashSet<Secret>();
  }
}
class Secret {

}