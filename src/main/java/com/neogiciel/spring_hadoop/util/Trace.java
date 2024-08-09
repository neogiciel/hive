package com.neogiciel.spring_hadoop.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Trace
 */
public class Trace {

    boolean logInFile = false;

    /*
     * Logger
     */
    static Logger logger = LoggerFactory.getLogger(Trace.class);

    /*
     * Singleton
     */
    static {
        init();
    }

    /*
     * init()
     */
    private static void init() {
        logger.info("\u001B[32m========================================");
        logger.info("\u001B[32m============ APPLICATION ===============");
        logger.info("\u001B[32m========================================");

    }

    /*
     * info
     */
    public static void info(String message) {
        logger.info("\u001B[32m" + message);

    }

    /*
     * debug
     */
    public static void debug(String message) {
        logger.info("\u001B[34m" + message);
    }

    /*
     * Error
     */
    public static void error(String message) {
        logger.info("\u001B[31m" + message);
    }

}
