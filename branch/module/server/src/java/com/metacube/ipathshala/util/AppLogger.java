/**
 * 
 */
package com.metacube.ipathshala.util;

import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


/**
 * Application Logging class, a wrapper on Log4J logger.
 *
 * @author vnarang
 */
public class AppLogger {
	
	private final Logger logger;

	private AppLogger(String name) {
		logger = Logger.getLogger(name);
	}

	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}
	
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	public static AppLogger getLogger(Class<?> clazz) {
		return getLogger(clazz.getName());
	}

	public static AppLogger getLogger(String loggerName) {
		return new AppLogger(loggerName);
	}

	public void fatal(String msg) {
		logger.fatal(msg);
	}

	public void fatal(String msg, Throwable t) {
		logger.fatal(msg, t);
	}
	
	public void trace(String msg) {
		logger.trace(msg);
	}
	
	public void debug(String msg) {
		if (logger.isDebugEnabled())	{
			logger.debug(msg);
		}
	}

	public void error(String msg) {
		logger.error(msg);
	}

	public void error(Throwable t) {
		error(t.getMessage(), t);
	}

	public void info(String msg) {
		if (logger.isInfoEnabled())	{
			logger.info(msg);
		}
	}

	public void info(String msg, Throwable t) {
		if (logger.isInfoEnabled())	{
			logger.info(msg, t);
		}
	}
	
	public void error(String msg, Throwable t) {
		logger.error(msg, t);
	}

	public void warn(String msg) {
		logger.warn(msg);
	}

	public void warn(String msg, Throwable t) {
		logger.warn(msg, t);
	}
	
	public void setLogLevel(String level) {
		logger.setLevel(Level.toLevel(level));
	}

	public void setLogLevel(Level level) {
		logger.setLevel(level);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, String> getLoggers() {
		Map<String, String> loggers = new TreeMap<String, String>();
		for (Enumeration<Logger> loggerEnum = Logger.getRootLogger().getLoggerRepository().getCurrentLoggers()
				; loggerEnum.hasMoreElements(); ) {
			Logger logger = (Logger)loggerEnum.nextElement();
			Level level = logger.getLevel();
			if (level != null ) {
				loggers.put(logger.getName(), level.toString());
			}
		}
		return loggers;
	}
}