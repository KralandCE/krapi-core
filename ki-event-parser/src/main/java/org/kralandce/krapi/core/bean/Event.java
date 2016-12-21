package org.kralandce.krapi.core.bean;

import java.time.LocalDateTime;

/**
 * What an event look like
 * 
 * @author Hello-Gitty
 *
 */
public class Event {

    private LocalDateTime dateHeure;
    private String empire;
    private String province;
    private String ville;
    private String data;

    public LocalDateTime getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(LocalDateTime dateHeure) {
        this.dateHeure = dateHeure;
    }

    public String getEmpire() {
        return empire;
    }

    public void setEmpire(String empire) {
        this.empire = empire;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
