package net.quackster.icarus.pooling;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

public class ThreadPooling {

	private ScheduledExecutorService scheduledThreadPool;

	public ThreadPooling() {
		this.scheduledThreadPool = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
	}

	public Future<?> register(Runnable runnable) {
		return this.scheduledThreadPool.submit(runnable);
	}

	public ScheduledExecutorService getScheduledThreadPool() {
		return this.scheduledThreadPool;
	}
}
