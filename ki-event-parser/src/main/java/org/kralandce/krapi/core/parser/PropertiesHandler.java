package org.kralandce.krapi.core.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesHandler {

    final static Logger logger = LoggerFactory.getLogger(PropertiesHandler.class);
       
    private static final String PROPS_FILE = "kralandce.properties";

    public static void load() throws IOException {
        InputStream input = PropertiesHandler.class.getClassLoader().getResourceAsStream(PROPS_FILE);
        if (input == null) {
            return;
        }
        Properties props = new Properties();
        props.load(input);

        for (EventParserParam param : EventParserParam.values()) {
            String val = (String) props.get(param.getKey());
            if (val != null && val.length() > 0) {
                param.setValue(val);
            }
            String valSys = System.getProperty(param.getKey());
            if (valSys != null && valSys.length() > 0) {
                logger.warn(
                    "Param override by command line " + param.name() + ": " + valSys);
                param.setValue(valSys);
            }
        }


    }
}

