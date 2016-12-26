package org.kralandce.krapi.core.bean;

import java.util.HashMap;
import java.util.Map;

public class People {

    private int id;
    private String nom;
    private String avatar;
    
    /**
     * To store profile Element
     */
    private Map<String, String> profil = new HashMap<String, String>();

    public Map<String, String> getProfil() {
        return profil;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
