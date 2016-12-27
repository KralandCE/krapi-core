package org.kralandce.krapi.core.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Hello-Gitty
 *
 */
public class CybermondeDatas {

    private Set<String> provinces = new HashSet<String>();
    private Set<String> villes = new HashSet<String>();
    private Set<String> empires = new HashSet<String>();

    public boolean addEmpire(String e) {
        return empires.add(e);
    }

    public boolean addVilles(String e) {
        return empires.add(e);
    }

    public boolean addProvinces(String e) {
        return empires.add(e);
    }

    public Set<String> getProvinces() {
        return provinces;
    }

    public Set<String> getVilles() {
        return villes;
    }

    public Set<String> getEmpires() {
        return empires;
    }

}
