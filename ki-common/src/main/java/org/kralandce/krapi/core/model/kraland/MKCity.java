package org.kralandce.krapi.core.model.kraland;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public final class MKCity {
    private MKCity() {

    }

    public static final class Identifier {
        private final int id;

        private Identifier(int identifier) {
            this.id = identifier;
        }

        public static Identifier of(int value) {
            return new Identifier(value);
        }

        public int asInt() {
            return this.id;
        }

        @Override
        public boolean equals(Object o) {
            if( this == o ) return true;
            if( o == null || getClass() != o.getClass() ) return false;

            Identifier cityId = (Identifier) o;

            return this.id == cityId.id;
        }

        @Override
        public int hashCode() {
            return this.id;
        }
    }

    public static final class Name {
        private final String name;

        private Name(String cityName) {
            checkNotNull(cityName);
            checkArgument(!cityName.isEmpty());
            this.name = cityName;
        }

        public static Name of(String cityName) {
            return new Name(cityName);
        }

        public String asString() {
            return this.name;
        }

        @Override
        public boolean equals(Object o) {
            if( this == o ) return true;
            if( o == null || getClass() != o.getClass() ) return false;

            Name cityName = (Name) o;

            return this.name.equals(cityName.name);
        }

        @Override
        public int hashCode() {
            return this.name.hashCode();
        }
    }
}
