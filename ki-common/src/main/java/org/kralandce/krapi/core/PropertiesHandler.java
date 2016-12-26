package org.kralandce.krapi.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesHandler {

    final static Logger logger = LoggerFactory.getLogger(PropertiesHandler.class);
    
    public static <E extends Enum<E> & KrapiParam> void load(Class<E> o) throws IOException {
        String propsFileName = "krapi-" +o.getSimpleName() +".properties";
        InputStream input = PropertiesHandler.class.getClassLoader().getResourceAsStream(propsFileName);

        Properties props = null;
        if (input != null) {
            props = new Properties();
            props.load(input);
        }

        for (E param : o.getEnumConstants()) {
            if (props != null) {
                String val = (String) props.get(param.getKey());
                if (val != null && val.length() > 0) {
                    param.setValue(val);
                }
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

