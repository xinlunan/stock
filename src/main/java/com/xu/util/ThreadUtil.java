package com.xu.util;

import java.util.List;

public class ThreadUtil {

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
