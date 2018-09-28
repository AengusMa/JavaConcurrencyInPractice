package jcip.chapter06;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author mawenlong
 * @date 2018/09/28
 *
 * 程序清单6-17：在预定时间内请求旅游报价
 */
public class TimeBudget {

  private static ExecutorService exec = Executors.newCachedThreadPool();

  public List<TravelQuote> getRankedTravelQuotes(TravelInfo travelInfo,
      Set<TravelCompany> companies, Comparator<TravelQuote> ranking, long time, TimeUnit unit)
      throws InterruptedException {
    List<QuoteTask> tasks = new ArrayList<>();
    for (TravelCompany company : companies) {
      tasks.add(new QuoteTask(company, travelInfo));
    }

    List<Future<TravelQuote>> futures = exec.invokeAll(tasks, time, unit);

    List<TravelQuote> quotes = new ArrayList<>(tasks.size());
    Iterator<QuoteTask> taskIterator = tasks.iterator();
    for (Future<TravelQuote> f : futures) {
      QuoteTask task = taskIterator.next();
      try {
        quotes.add(f.get());
      } catch (ExecutionException e) {
        quotes.add(task.getFailureQuote(e.getCause()));
      } catch (CancellationException e) {
        quotes.add(task.getTimeoutQuote(e));
      }
    }
    Collections.sort(quotes, ranking);
    return quotes;
  }
}

class QuoteTask implements Callable<TravelQuote> {

  private final TravelCompany company;
  private final TravelInfo travelInfo;

  public QuoteTask(TravelCompany company, TravelInfo travelInfo) {
    this.company = company;
    this.travelInfo = travelInfo;
  }

  TravelQuote getFailureQuote(Throwable t) {
    return null;
  }

  TravelQuote getTimeoutQuote(CancellationException e) {
    return null;
  }

  @Override
  public TravelQuote call() throws Exception {
    return company.solicitQuote(travelInfo);
  }

}

interface TravelCompany {

  TravelQuote solicitQuote(TravelInfo travelInfo) throws Exception;
}

interface TravelQuote {

}

interface TravelInfo {

}
