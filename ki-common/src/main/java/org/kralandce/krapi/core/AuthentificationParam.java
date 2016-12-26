package org.kralandce.krapi.core;

public enum AuthentificationParam implements KrapiParam {
    
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

    private AuthentificationParam(String key, String defaut) {
        this.defaut = defaut;
        this.key = key;
    }

    public String getPropsFileName() {
        return null;
    }


}
