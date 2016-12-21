package org.kralandce.krapi.core.bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Events {

    private List<Event> events = new ArrayList<Event>();
    private LocalDate jour = null;

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public LocalDate getJour() {
        return jour;
    }

    public void setJour(LocalDate jour) {
        this.jour = jour;
    }

    public boolean add(Event e) {
        return events.add(e);
    }

}
