package com.crossasyst.snakesnladder.Logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameLogger {
	
	private static GameLogger gameLogger = null;
	private static Logger logger = null;
	
	private GameLogger()
	{
		logger = LogManager.getLogger();
		
	}
	
	public static GameLogger getGameLogger()
	{
		if(gameLogger == null)
			gameLogger = new GameLogger();
		return gameLogger;
	}
	
	public void log(String level, String msg)
	{
		if(level.equalsIgnoreCase("FATAL"))
			logger.fatal(msg);
		else if(level.equalsIgnoreCase("ERROR"))
			logger.error(msg);
		else if(level.equalsIgnoreCase("WARN"))
			logger.warn(msg);
		else if(level.equalsIgnoreCase("INFO"))
			logger.info(msg);
		else if(level.equalsIgnoreCase("DEBUG"))
			logger.debug(msg);
		else if(level.equalsIgnoreCase("TRACE"))
			logger.trace(msg);
		else if(level.equalsIgnoreCase("ALL"))
			logger.log(null, msg);
	}

}
