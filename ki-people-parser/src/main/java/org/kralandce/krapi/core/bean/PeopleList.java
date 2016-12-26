package org.kralandce.krapi.core.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Hello-Gitty
 *
 */
public class PeopleList {

    private String title;
    private List<People> peoples = new ArrayList<People>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<People> getPeoples() {
        return peoples;
    }

    public boolean add(People e) {
        return peoples.add(e);
    }

}
