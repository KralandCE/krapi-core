package org.kralandce.krapi.core.model.kraland;

public final class MKWealth {
    private MKWealth() {

    }

    public static final class Level {
        private final int level;

        private Level(int wealthLevel) {
            this.level = wealthLevel;
        }

        public static Level of(int value) {
            return new Level(value);
        }

        public int asInt() {
            return this.level;
        }

        @Override
        public boolean equals(Object o) {
            if( this == o ) return true;
            if( o == null || getClass() != o.getClass() ) return false;

            Level that = (Level) o;

            return this.level == that.level;
        }

        @Override
        public int hashCode() {
            return this.level;
        }
    }
}
