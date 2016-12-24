package org.kralandce.krapi.core.parser;

/**
 * Params store as enum
 * @author Hello-Gitty
 *
 */
public enum EventParserParam {

    KI_SLAVE_LOGIN("kralandce.slave.login", ""), KI_SLAVE_PASS("kralandce.slave.password", ""), AUTHENFICATION("kralandce.is.authentification", "false");

    private final String defaut;
    private String value = null;
    private final String key;

    public String getKey() {
        return key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        if (value != null) {
            return value;
        }
        return defaut;
    }
    
    public boolean isValue() {
        return Boolean.parseBoolean(getValue());
    }

    private EventParserParam(String key, String defaut) {
        this.defaut = defaut;
        this.key = key;
    }


    
    
}
