package org.kralandce.krapi.core.bean;

import org.kralandce.krapi.core.model.kraland.MKCity;
import org.kralandce.krapi.core.model.kraland.MKEvent;
import org.kralandce.krapi.core.model.kraland.MKNation;
import org.kralandce.krapi.core.model.kraland.MKProvince;

import java.util.Optional;

public interface KEvent {

    public Optional<MKNation.Identifier> getNation();

    public Optional<MKProvince.Name> getProvince();

    public Optional<MKCity.Name> getCity();

    public Optional<MKEvent.Type> getType();

    public Optional<MKEvent.Content> getContent();

    public Optional<MKEvent.Date> getDate();
}
