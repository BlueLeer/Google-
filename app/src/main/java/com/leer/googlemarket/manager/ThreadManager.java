package com.leer.googlemarket.manager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Leer on 2017/5/31.
 */

public class ThreadManager {
    private static ThreadManager mThreadManager;
    private ThreadPoolExecutor executor;
    private ExecutorService executorService;

    private ThreadManager() {
    }

    public static ThreadManager getInstance() {
        if (mThreadManager == null) {
            mThreadManager = new ThreadManager();
        }

        return mThreadManager;
    }

    /**
     * 在线程池中执行一个任务
     *
     * @param r 待执行的Runnable对象
     */
    public void execute(Runnable r) {
        if (executor == null) {
            //获取运行设备的"核"的数量
            int nucleusCount = Runtime.getRuntime().availableProcessors();
            int corePoolSize = nucleusCount * 3 + 1;

            // 参1:核心线程数;参2:最大线程数;参3:线程休眠时间;参4:时间单位;
            // 参5:线程队列;参6:生产线程的工厂;参7:线程异常处理策略
            executor = new ThreadPoolExecutor(corePoolSize, corePoolSize * 2,
                    1, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(),
                    Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        }

        executor.execute(r);

    }


//    public void execute2(Runnable r) {
//        if (executorService == null) {
//            executorService = Executors.newFixedThreadPool(10);
//        }
//        executorService.execute(r);
//    }

    /**
     * 暂停(删除)一个任务
     *
     * @param r 待执行的Runnable对象
     */
    public void cancel(Runnable r) {
        //从下载的队列中移除下载任务
        executor.getQueue().remove(r);
    }
}
