package com.mycompany.serializers;

import org.apache.log4j.Logger;
import org.junit.Test;

public class TestLogger {

    private static final Logger log = Logger.getLogger(TestLogger.class);

    @Test
    public void tesLogger() {
        log.fatal("23");
    }
}
