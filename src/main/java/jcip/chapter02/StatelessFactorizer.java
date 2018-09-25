package jcip.chapter02;

import java.math.BigInteger;
import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import jcip.annotations.ThreadSafe;

/**
 * @author mawenlong
 * @date 2018/09/25
 *
 * 程序清单2-1：一个无状态的Servlet
 */
@ThreadSafe
public class StatelessFactorizer extends GenericServlet implements Servlet {

  @Override
  public void service(ServletRequest servletRequest, ServletResponse servletResponse) {
    BigInteger i = extractFromRequest(servletRequest);
    BigInteger[] factors = factor(i);
    encodeIntoResponse(servletResponse, factors);
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
