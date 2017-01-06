package org.kralandce.krapi.core.model.event;

import org.kralandce.krapi.core.model.kraland.Kraland;

import java.util.Optional;

/**
 * @author Ricorei
 */
public interface KEvent {

    public Optional<Kraland.NationName> getNation();

    public Optional<Kraland.ProvinceName> getProvince();

    public Optional<Kraland.CityName> getCity();

    public Optional<KralandEvent.Type> getType();

    public Optional<KralandEvent.Content> getContent();

    public Optional<KralandEvent.Date> getDate();
}
