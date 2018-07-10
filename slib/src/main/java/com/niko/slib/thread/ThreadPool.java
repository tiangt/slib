package com.niko.slib.thread;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by niko on 15/10/23.
 */
public class ThreadPool {
    public static final int THREAD_POOL_SIZE = 8;
    private static Handler mMainHandler = new Handler(Looper.getMainLooper());

    private static ExecutorService mInstance = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    public static void newTask(Runnable runnable) {
        mInstance.execute(runnable);
    }

    public static void newUITask(Runnable runnable) {
        mMainHandler.post(runnable);
    }

    public static void newUITask(Runnable runnable, long delay) {
        mMainHandler.postDelayed(runnable,delay);
    }

    public static <T> void newTask(final AsyncWork<T> asyncWork) {
        newTask(new Runnable() {
            @Override
            public void run() {
                try {
                    final T object = asyncWork.doHardWork();
                    newUITask(new Runnable() {
                        @Override
                        public void run() {
                            asyncWork.onCallback(object, true, null);
                        }
                    });
                } catch (final Exception e) {
                    newUITask(new Runnable() {
                        @Override
                        public void run() {
                            asyncWork.onCallback(null, false, e);
                        }
                    });
                }
            }
        });
    }

    public interface AsyncWork<T>{
        T doHardWork();
        void onCallback(T result, boolean success, Exception e);
    }
}
