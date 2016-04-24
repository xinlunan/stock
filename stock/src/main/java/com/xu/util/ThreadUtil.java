package com.xu.util;

import java.util.List;

public class ThreadUtil {

    private static Boolean hasException = false;

    public static Boolean getHasException() {
        return hasException;
    }

    public static void setHasException(Boolean hasException) {
        ThreadUtil.hasException = hasException;
    }

    /**
     * 线程组等待
     * 
     * @param workers
     */
	public static void threadsJoin(List<Runnable> workers) {

		for (int i = 0; i < workers.size(); i++) {
			try {

				Thread worker = (Thread) workers.get(i);
				worker.join();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

	}

}
