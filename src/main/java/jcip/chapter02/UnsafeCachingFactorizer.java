package jcip.chapter02;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;
import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import jcip.annotations.NotThreadSafe;

/**
 * @author mawenlong
 * @date 2018/09/25
 *
 * 程序清单2-5：该Servlet在没有足够原子性保证的情况下对其最近计算结果进行缓存（不要这么做）
 */
@NotThreadSafe
public class UnsafeCachingFactorizer extends GenericServlet implements Servlet {

  private final AtomicReference<BigInteger> lastNumber = new AtomicReference<>();
  private final AtomicReference<BigInteger[]> lastFactors = new AtomicReference<>();

  @Override
  public void service(ServletRequest req, ServletResponse res) {
    BigInteger i = extractFromRequest(req);
    if (i.equals(lastNumber.get())) {
      encodeIntoResponse(res, lastFactors.get());
    } else {
      BigInteger[] factors = factor(i);
      lastNumber.set(i);
      lastFactors.set(factors);
      encodeIntoResponse(res, factors);
    }
  }

  void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
  }

  BigInteger extractFromRequest(ServletRequest req) {
    return new BigInteger("7");
  }

  BigInteger[] factor(BigInteger i) {
    // Doesn't really factor
    return new BigInteger[]{i};
  }
}
