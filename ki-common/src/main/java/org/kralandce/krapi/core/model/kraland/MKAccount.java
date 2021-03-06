package org.kralandce.krapi.core.model.kraland;

import static com.google.common.base.Preconditions.checkNotNull;

public final class MKAccount {

    private MKAccount() {

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

            Identifier accountId = (Identifier) o;

            return this.id == accountId.id;
        }

        @Override
        public int hashCode() {
            return this.id;
        }
    }

    public static final class PermissionLevel {
        private final int level;

        private PermissionLevel(int accountLevel) {
            this.level = accountLevel;
        }

        public static PermissionLevel of(int value) {
            return new PermissionLevel(value);
        }

        public int asInt() {
            return this.level;
        }

        @Override
        public boolean equals(Object o) {
            if( this == o ) return true;
            if( o == null || getClass() != o.getClass() ) return false;

            PermissionLevel that = (PermissionLevel) o;

            return this.level == that.level;
        }

        @Override
        public int hashCode() {
            return this.level;
        }
    }

    public static final class Name {
        private final String name;

        private Name(String accountName) {
            this.name = checkNotNull(accountName);
        }

        public static Name of(String accountName) {
            return new Name(accountName);
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

    public static final class Gender {
        private final String name;

        private Gender(String genderName) {
            this.name = checkNotNull(genderName);
        }

        public static Gender of(String genderName) {
            return new Gender(genderName);
        }

        public String asString() {
            return this.name;
        }

        @Override
        public boolean equals(Object o) {
            if( this == o ) return true;
            if( o == null || getClass() != o.getClass() ) return false;

            Gender that = (Gender) o;

            return this.name.equals(that.name);
        }

        @Override
        public int hashCode() {
            return this.name.hashCode();
        }
    }

    public static final class AvatarURL {
        private final String url;

        private AvatarURL(String avatarURL) {
            this.url = checkNotNull(avatarURL);
        }

        public static AvatarURL of(String avatarURL) {
            return new AvatarURL(avatarURL);
        }

        public String asString() {
            return this.url;
        }

        @Override
        public boolean equals(Object o) {
            if( this == o ) return true;
            if( o == null || getClass() != o.getClass() ) return false;

            AvatarURL avatarURL = (AvatarURL) o;

            return this.url.equals(avatarURL.url);
        }

        @Override
        public int hashCode() {
            return this.url.hashCode();
        }
    }
}
