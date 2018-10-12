package jcip.chapter08;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author mawenlong
 * @date 2018/10/12
 *
 * 程序清单8-1：在单线程Executor中任务发生死锁（不要这么做）
 */
public class ThreadDeadlock {

  ExecutorService exec = Executors.newSingleThreadExecutor();



}
