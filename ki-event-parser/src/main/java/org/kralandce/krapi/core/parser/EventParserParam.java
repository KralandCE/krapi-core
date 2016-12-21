package org.kralandce.krapi.core.parser;

/**
 * Params store as enum
 * @author Hello-Gitty
 *
 */
public enum EventParserParam {

    KI_SLAVE_LOGIN("sbleune45"), KI_SLAVE_PASS("slave"), AUTHENFICATION("false");

    private final String val;

    public String getVal() {
        return val;
    }

    public boolean isVal() {
        return Boolean.getBoolean(val);
    }

    private EventParserParam(String val) {
        this.val = val;
    }

}
