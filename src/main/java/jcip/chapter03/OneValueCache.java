package jcip.chapter03;

import java.math.BigInteger;
import java.util.Arrays;
import jcip.annotations.Immutable;

/**
 * @author mawenlong
 * @date 2018/09/25
 *
 * 程序清单3-12：对数值及其因数分解结果进行缓存的不可变容器类
 */
@Immutable
public class OneValueCache {

  private final BigInteger lastNumber;
  private final BigInteger[] lastFactors;

  public OneValueCache(BigInteger lastNumber, BigInteger[] lastFactors) {
    this.lastNumber = lastNumber;
    this.lastFactors = Arrays.copyOf(lastFactors, lastFactors.length);
  }

  public BigInteger[] getFactors(BigInteger i) {
    if (lastNumber == null || !lastNumber.equals(i)) {
      return null;
    } else {
      return Arrays.copyOf(lastFactors, lastFactors.length);
    }
  }
}
