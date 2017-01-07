package org.kralandce.krapi.core.model.kraland;

/**
 * @author Ricorei
 */
public final class MKCharacter {
    private MKCharacter() {

    }

    public static final class WealthLevel {
        private final int level;

        private WealthLevel(int wealthLevel) {
            this.level = wealthLevel;
        }

        public static WealthLevel of(int value) {
            return new WealthLevel(value);
        }

        public int asInt() {
            return this.level;
        }

        @Override
        public boolean equals(Object o) {
            if( this == o ) return true;
            if( o == null || getClass() != o.getClass() ) return false;

            WealthLevel that = (WealthLevel) o;

            return this.level == that.level;
        }

        @Override
        public int hashCode() {
            return this.level;
        }
    }
}
