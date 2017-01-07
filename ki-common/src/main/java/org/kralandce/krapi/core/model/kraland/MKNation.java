package org.kralandce.krapi.core.model.kraland;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ricorei
 */
public final class MKNation {
    private MKNation() {

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

            Identifier nationId = (Identifier) o;

            return this.id == nationId.id;
        }

        @Override
        public int hashCode() {
            return this.id;
        }
    }

    public static final class Name {
        private final String name;

        private Name(String nationName) {
            this.name = checkNotNull(nationName);
        }

        public static Name of(String nationName) {
            return new Name(nationName);
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

    public static final class AbbreviatedName {
        private final String name;

        private AbbreviatedName(String nationName) {
            this.name = checkNotNull(nationName);
        }

        public static AbbreviatedName of(String nationName) {
            return new AbbreviatedName(nationName);
        }

        public String asString() {
            return this.name;
        }

        @Override
        public boolean equals(Object o) {
            if( this == o ) return true;
            if( o == null || getClass() != o.getClass() ) return false;

            AbbreviatedName that = (AbbreviatedName) o;

            return this.name.equals(that.name);
        }

        @Override
        public int hashCode() {
            return this.name.hashCode();
        }
    }
}
