package jcip.chapter02;

import java.math.BigInteger;
import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import jcip.annotations.NotThreadSafe;

/**
 * @author mawenlong
 * @date 2018/09/25
 *
 * 程序清单2-2：在没有同步的情况下统计以处理请求数量的Servlet（不要这么做）
 */
@NotThreadSafe
public class UnsafeCountingFactorizer extends GenericServlet implements Servlet {

  private long count = 0;

  public long getCount() {
    return count;
  }

  @Override
  public void service(ServletRequest servletRequest, ServletResponse servletResponse) {
    BigInteger i = extractFromRequest(servletRequest);
    BigInteger[] factors = factor(i);
    ++count;
    encodeIntoResponse(servletResponse, factors);
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
