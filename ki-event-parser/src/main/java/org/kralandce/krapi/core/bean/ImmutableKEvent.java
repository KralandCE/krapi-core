package org.kralandce.krapi.core.bean;

import org.kralandce.krapi.core.model.kraland.MKCity;
import org.kralandce.krapi.core.model.kraland.MKEvent;
import org.kralandce.krapi.core.model.kraland.MKNation;
import org.kralandce.krapi.core.model.kraland.MKProvince;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkState;

public final class ImmutableKEvent implements KEvent {

    private final MKNation.Identifier nation;
    private final MKProvince.Name province;
    private final MKCity.Name city;
    private final MKEvent.Content data;
    private final MKEvent.Type type;
    private final MKEvent.Date date;

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
    public Optional<MKNation.Identifier> getNation() {
        return Optional.ofNullable(this.nation);
    }

    @Override
    public Optional<MKProvince.Name> getProvince() {
        return Optional.ofNullable(this.province);
    }

    @Override
    public Optional<MKCity.Name> getCity() {
        return Optional.ofNullable(this.city);
    }

    @Override
    public Optional<MKEvent.Type> getType() {
        return Optional.ofNullable(this.type);
    }

    @Override
    public Optional<MKEvent.Content> getContent() {
        return Optional.ofNullable(this.data);
    }

    @Override
    public Optional<MKEvent.Date> getDate() {
        return Optional.ofNullable(this.date);
    }

    public static final class Builder {
        private MKNation.Identifier nation;
        private MKProvince.Name province;
        private MKCity.Name city;
        private MKEvent.Content data;
        private MKEvent.Type type;
        private MKEvent.Date date;

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

        public void setDate(MKEvent.Date eventDate) {
            this.date = eventDate;
        }

        public void setNation(MKNation.Identifier eventNation) {
            this.nation = eventNation;
        }

        public void setProvince(MKProvince.Name eventProvince) {
            this.province = eventProvince;
        }

        public void setCity(MKCity.Name eventCity) {
            this.city = eventCity;
        }

        public void setData(MKEvent.Content eventData) {
            this.data = eventData;
        }

        public void setType(MKEvent.Type eventType) {
            this.type = eventType;
        }

        public ImmutableKEvent build() {
            checkState(!this.build);
            this.build = true;
            return new ImmutableKEvent(this);
        }
    }
}
