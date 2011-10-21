package net.sf.jacclog.service.importer.internal.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(UncaughtExceptionHandler.class);

	@Override
	public void uncaughtException(final Thread thread, final Throwable throwable) {
		LOG.warn("An unexpected error has occurred in Thread '" + thread.getName() + "'.", throwable);
	}

}
