package org.kralandce.krapi.core.parser;

/**
 * Params store as enum
 * @author Hello-Gitty
 *
 */
public enum EventParserParam {

    KI_SLAVE_LOGIN("kralandce.slave.login", ""), KI_SLAVE_PASS("kralandce.slave.password", ""), AUTHENFICATION("kralandce.is.authentification", "false");

    private final String val;
    private final String key;

    public String getVal() {
        return val;
    }

    public String getKey() {
        return key;
    }
    
    public boolean isVal() {
        return Boolean.parseBoolean(val);
    }

    private EventParserParam(String key, String val) {
        this.key = key;
        this.val = val;
    }



    
    
}
