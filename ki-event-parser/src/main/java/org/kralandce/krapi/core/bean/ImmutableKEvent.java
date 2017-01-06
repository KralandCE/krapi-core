package org.kralandce.krapi.core.bean;

import org.kralandce.krapi.core.model.event.KEvent;
import org.kralandce.krapi.core.model.event.KralandEvent;
import org.kralandce.krapi.core.model.kraland.Kraland;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkState;

/**
 * @author Ricorei
 */
public final class ImmutableKEvent implements KEvent {

    private final Kraland.NationName nation;
    private final Kraland.ProvinceName province;
    private final Kraland.CityName city;
    private final KralandEvent.Content data;
    private final KralandEvent.Type type;
    private final KralandEvent.Date date;

    private ImmutableKEvent(Builder builder) {
        this.date = builder.date;
        this.nation = builder.nation;
        this.city = builder.city;
        this.province = builder.province;
        this.type = builder.type;
        this.data = builder.data;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Optional<Kraland.NationName> getNation() {
        return Optional.ofNullable(this.nation);
    }

    @Override
    public Optional<Kraland.ProvinceName> getProvince() {
        return Optional.ofNullable(this.province);
    }

    @Override
    public Optional<Kraland.CityName> getCity() {
        return Optional.ofNullable(this.city);
    }

    @Override
    public Optional<KralandEvent.Type> getType() {
        return Optional.ofNullable(this.type);
    }

    @Override
    public Optional<KralandEvent.Content> getContent() {
        return Optional.ofNullable(this.data);
    }

    @Override
    public Optional<KralandEvent.Date> getDate() {
        return Optional.ofNullable(this.date);
    }

    public static final class Builder {
        private Kraland.NationName nation;
        private Kraland.ProvinceName province;
        private Kraland.CityName city;
        private KralandEvent.Content data;
        private KralandEvent.Type type;
        private KralandEvent.Date date;

        private boolean build;

        private Builder() {
            this.date = null;
            this.nation = null;
            this.province = null;
            this.city = null;
            this.type = null;
            this.data = null;
            this.build = false;
        }

        public void setDate(KralandEvent.Date eventDate) {
            this.date = eventDate;
        }

        public void setNation(Kraland.NationName eventNation) {
            this.nation = eventNation;
        }

        public void setProvince(Kraland.ProvinceName eventProvince) {
            this.province = eventProvince;
        }

        public void setCity(Kraland.CityName eventCity) {
            this.city = eventCity;
        }

        public void setData(KralandEvent.Content eventData) {
            this.data = eventData;
        }

        public void setType(KralandEvent.Type eventType) {
            this.type = eventType;
        }

        public ImmutableKEvent build() {
            checkState(!this.build);
            this.build = true;
            return new ImmutableKEvent(this);
        }
    }
}
