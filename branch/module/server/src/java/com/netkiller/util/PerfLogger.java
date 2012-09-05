package com.netkiller.util;

public class PerfLogger {
	
	private Long startTime;
	private Long endTime;
	private AppLogger logger;
	private String identifier;
	
	
	public PerfLogger(AppLogger logger, String identifier)	{
		this.logger = logger;
		this.identifier = identifier;
	}
	
	public  void startTime()	{
		startTime = System.currentTimeMillis();
	}
	
	public void stopTime()	{
		endTime = System.currentTimeMillis();
		logger.debug("Time taken is "+(endTime-startTime));
		System.out.println("Time taken for "+identifier+" is "+(endTime-startTime));
	}
	
	
	

}
