package org.kralandce.krapi.core.parser;

/**
 * Params store as enum
 * @author Hello-Gitty
 *
 */
public enum EventParserParam {

    KI_SLAVE_LOGIN(""), KI_SLAVE_PASS("");

    private final String val;

    public String getVal() {
        return val;
    }

    private EventParserParam(String val) {
        this.val = val;
    }

}
