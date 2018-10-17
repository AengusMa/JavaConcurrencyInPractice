package jcip.chapter10;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author mawenlong
 * @date 2018/10/17
 *
 * 程序清单10-2：动态的锁顺序死锁
 */
public class DynamicOrderDeadlock {

  /**
   * 容易发生死锁
   */
  public static void transferMoney(Account fromAccount, Account toAccount, DollarAmount amount)
      throws InsufficientFundsException {
    synchronized (fromAccount) {
      synchronized (toAccount) {
        if (fromAccount.getBalance().compareTo(amount) < 0) {
          throw new InsufficientFundsException();
        } else {
          fromAccount.debit(amount);
          toAccount.credit(amount);
        }
      }
    }
  }

  static class DollarAmount implements Comparable<DollarAmount> {
    // Needs implementation

    public DollarAmount(int amount) {
    }

    public DollarAmount add(DollarAmount d) {
      return null;
    }

    public DollarAmount subtract(DollarAmount d) {
      return null;
    }

    public int compareTo(DollarAmount dollarAmount) {
      return 0;
    }
  }

  static class Account {

    private DollarAmount balance;
    private final int acctNo;
    private static final AtomicInteger sequence = new AtomicInteger();

    public Account() {
      acctNo = sequence.incrementAndGet();
    }

    void debit(DollarAmount d) {
      balance = balance.subtract(d);
    }

    void credit(DollarAmount d) {
      balance = balance.add(d);
    }

    DollarAmount getBalance() {
      return balance;
    }

    int getAcctNo() {
      return acctNo;
    }
  }

  static class InsufficientFundsException extends Exception {

  }
}
