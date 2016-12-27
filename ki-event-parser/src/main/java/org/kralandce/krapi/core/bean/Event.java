package org.kralandce.krapi.core.bean;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * What an event look like
 * 
 * @author Hello-Gitty
 *
 */
public class Event {

    private LocalDateTime dateHeure;
    private Optional<String> empire;
    private Optional<String> province;
    private Optional<String> ville;
    private String data;

    public LocalDateTime getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(LocalDateTime dateHeure) {
        this.dateHeure = dateHeure;
    }

    public Optional<String> getEmpire() {
        return empire;
    }

    public void setEmpire(Optional<String> empire) {
        this.empire = empire;
    }

    public Optional<String> getProvince() {
        return province;
    }

    public void setProvince(Optional<String> province) {
        this.province = province;
    }

    public Optional<String> getVille() {
        return ville;
    }

    public void setVille(Optional<String> ville) {
        this.ville = ville;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
