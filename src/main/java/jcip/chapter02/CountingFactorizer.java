package jcip.chapter02;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;
import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import jcip.annotations.ThreadSafe;

/**
 * @author mawenlong
 * @date 2018/09/25
 *
 * 程序清单 2-4：使用AtomicLong类型的变量来统计以请求的数量
 */
@ThreadSafe
public class CountingFactorizer extends GenericServlet implements Servlet {

  private final AtomicLong count = new AtomicLong(0);

  public AtomicLong getCount() {
    return count;
  }

  @Override
  public void service(ServletRequest req, ServletResponse res) {
    BigInteger i = extractFromRequest(req);
    BigInteger[] factors = factor(i);
    count.incrementAndGet();
    encodeIntoResponse(res, factors);
  }


  void encodeIntoResponse(ServletResponse res, BigInteger[] factors) {
  }

  BigInteger extractFromRequest(ServletRequest req) {
    return new BigInteger("7");
  }

  BigInteger[] factor(BigInteger i) {
    // Doesn't really factor
    return new BigInteger[]{i};
  }
}
