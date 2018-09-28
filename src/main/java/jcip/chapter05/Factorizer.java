package jcip.chapter05;

import java.math.BigInteger;
import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import jcip.annotations.ThreadSafe;

/**
 * @author mawenlong
 * @date 2018/09/28
 *
 * 程序清单5-20：在因式分解servlet中使用Memoizer来缓存结果
 */
@ThreadSafe
public class Factorizer extends GenericServlet implements Servlet {

  private final Computable<BigInteger, BigInteger[]> c = new Computable<BigInteger, BigInteger[]>() {
    @Override
    public BigInteger[] compute(BigInteger arg) {
      return factor(arg);
    }
  };
  private final Computable<BigInteger, BigInteger[]> cache = new Memoizer<>(c);

  @Override
  public void service(ServletRequest req, ServletResponse res) {
    try {
      BigInteger i = extractFromRequest(req);
      encodeIntoResponse(res, cache.compute(i));
    } catch (InterruptedException e) {
      encodeError(res, "factorization interrupted");
    }
  }

  void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
  }

  void encodeError(ServletResponse resp, String errorString) {
  }

  BigInteger extractFromRequest(ServletRequest req) {
    return new BigInteger("7");
  }

  BigInteger[] factor(BigInteger i) {
    // Doesn't really factor
    return new BigInteger[]{i};
  }
}
