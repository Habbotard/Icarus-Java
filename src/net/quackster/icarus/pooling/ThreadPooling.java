package net.quackster.icarus.pooling;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

public class ThreadPooling {

	private NonBlockingExecutor nonBlockingExecutor;
	private ScheduledExecutorService scheduledThreadPool;

	public ThreadPooling() {
		this.scheduledThreadPool = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
		this.nonBlockingExecutor = new NonBlockingExecutor(this.scheduledThreadPool);
	}

	public Future<?> register(Runnable runnable) {
		return this.scheduledThreadPool.submit(runnable);
	}

	public ScheduledExecutorService getScheduledThreadPool() {
		return this.scheduledThreadPool;
	}

	public NonBlockingExecutor getNonBlockingExecutor() {
		return nonBlockingExecutor;
	}
}

/*package net.quackster.icarus.pooling;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

public class ThreadPooling {

	private ExecutorService fixedThreadPool;
	private ScheduledExecutorService scheduledThreadPool;

	public ThreadPooling() {
		this.fixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		this.scheduledThreadPool = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
	}

	public Future<?> register(Runnable runnable) {
		return fixedThreadPool.submit(runnable);
	}

	public ExecutorService getFixedThreadPool() {
		return fixedThreadPool;
	}

	public ScheduledExecutorService getScheduledThreadPool() {
		return scheduledThreadPool;
	}
}*/