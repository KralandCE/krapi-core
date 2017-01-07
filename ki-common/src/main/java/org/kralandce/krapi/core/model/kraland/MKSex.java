package org.kralandce.krapi.core.model.kraland;

import static com.google.common.base.Preconditions.checkNotNull;

public final class MKSex {

    private MKSex() {

    }

    public static final class Name {
        private final String name;

        private Name(String sexName) {
            this.name = checkNotNull(sexName);
        }

        public static Name of(String sexName) {
            return new Name(sexName);
        }

        public String asString() {
            return this.name;
        }

        @Override
        public boolean equals(Object o) {
            if( this == o ) return true;
            if( o == null || getClass() != o.getClass() ) return false;

            Name that = (Name) o;

            return this.name.equals(that.name);
        }

        @Override
        public int hashCode() {
            return this.name.hashCode();
        }
    }

    public static final class Identifier {
        private final int id;

        private Identifier(int sexId) {
            this.id = sexId;
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

            Identifier that = (Identifier) o;

            return this.id == that.id;
        }

        @Override
        public int hashCode() {
            return this.id;
        }
    }
}
