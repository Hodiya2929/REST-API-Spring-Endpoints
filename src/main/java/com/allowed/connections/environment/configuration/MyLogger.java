package com.allowed.connections.environment.configuration;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * This class 
 * @author עימנואל
 *
 */
public class MyLogger {

	    static private FileHandler fileTxt;
	    static private SimpleFormatter formatterTxt;

	    static public void setup() throws IOException {

	        // get the global logger to configure it
	        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	        // suppress the logging output to the console
	        logger.setUseParentHandlers(false);

	        logger.setLevel(Level.INFO);
	        fileTxt = new FileHandler("Logging.txt");

	        // create a TXT formatter
	        formatterTxt = new SimpleFormatter();
	        fileTxt.setFormatter(formatterTxt);
	        logger.addHandler(fileTxt);
	    }
	}
	


