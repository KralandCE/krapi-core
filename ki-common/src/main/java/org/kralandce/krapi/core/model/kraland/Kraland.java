package org.kralandce.krapi.core.model.kraland;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A namespace for common data from Kraland .<br/>
 * Every class is immutable with proper hashcode and equals implementation.<br/>
 * Static constructors are provided and are designed to fail-fast.<br/>
 * <p>
 * Basic classes are wrappers around primitive types or String. Used as alias to avoid silent data corruption or parameter mismatch.<br/>
 * Advanced classes associate a name with an identifier that should strictly correspond to kraland owns identifiers and nothing else ( like
 * a database index which is <b>wrong</b>).
 * </p>
 * <p>
 * Warning : This is not a fit-all data implementation : Everything specific to a module should be handled in that module
 * ( dates, raw data, ever-changing implementations etc...).
 * </p>
 *
 * TODO : Names and identifiers for items, buildings, jobs, character level, account level
 *
 * @author Ricorei
 */
public final class Kraland {
    private Kraland() {

    }

    public static final class AccountId {
        private final int id;

        public AccountId(int identifier) {
            checkArgument(identifier > 0);
            this.id = identifier;
        }

        public int asInt() {
            return this.id;
        }

        @Override
        public boolean equals(Object o) {
            if( this == o ) return true;
            if( o == null || getClass() != o.getClass() ) return false;

            AccountId accountId = (AccountId) o;

            return this.id == accountId.id;
        }

        @Override
        public int hashCode() {
            return this.id;
        }
    }

    public static final class NationId {
        private final int id;

        public NationId(int identifier) {
            checkArgument(identifier >= 0);
            this.id = identifier;
        }

        public int asInt() {
            return this.id;
        }

        @Override
        public boolean equals(Object o) {
            if( this == o ) return true;
            if( o == null || getClass() != o.getClass() ) return false;

            NationId nationId = (NationId) o;

            return this.id == nationId.id;
        }

        @Override
        public int hashCode() {
            return this.id;
        }
    }

    public static final class ProvinceId {
        private final int id;

        public ProvinceId(int identifier) {
            checkArgument(identifier >= 0);
            this.id = identifier;
        }

        public int asInt() {
            return this.id;
        }

        @Override
        public boolean equals(Object o) {
            if( this == o ) return true;
            if( o == null || getClass() != o.getClass() ) return false;

            ProvinceId that = (ProvinceId) o;

            return this.id == that.id;
        }

        @Override
        public int hashCode() {
            return this.id;
        }
    }

    public static final class CityId {
        private final int id;

        public CityId(int identifier) {
            checkArgument(identifier >= 0);
            this.id = identifier;
        }

        public int asInt() {
            return this.id;
        }

        @Override
        public boolean equals(Object o) {
            if( this == o ) return true;
            if( o == null || getClass() != o.getClass() ) return false;

            CityId cityId = (CityId) o;

            return this.id == cityId.id;
        }

        @Override
        public int hashCode() {
            return this.id;
        }
    }

    public static final class GenderId {
        private final int id;

        public GenderId(int identifier) {
            checkArgument(identifier >= 0);
            this.id = identifier;
        }

        public int asInt() {
            return this.id;
        }

        @Override
        public boolean equals(Object o) {
            if( this == o ) return true;
            if( o == null || getClass() != o.getClass() ) return false;

            GenderId genderId = (GenderId) o;

            return this.id == genderId.id;
        }

        @Override
        public int hashCode() {
            return this.id;
        }
    }

    public static final class AccountName {
        public static final AccountName EMPTY = of("");
        private final String name;

        private AccountName(String accountName) {
            this.name = checkNotNull(accountName);
        }

        public static AccountName of(String accountName) {
            return new AccountName(accountName);
        }

        public String asString() {
            return this.name;
        }

        @Override
        public boolean equals(Object o) {
            if( this == o ) return true;
            if( o == null || getClass() != o.getClass() ) return false;

            AccountName that = (AccountName) o;

            return this.name.equals(that.name);
        }

        @Override
        public int hashCode() {
            return this.name.hashCode();
        }
    }

    public static final class NationName {
        public static final NationName EMPTY = of("");
        private final String name;

        private NationName(String nationName) {
            this.name = checkNotNull(nationName);
        }

        public static NationName of(String nationName) {
            return new NationName(nationName);
        }

        public String asString() {
            return this.name;
        }

        @Override
        public boolean equals(Object o) {
            if( this == o ) return true;
            if( o == null || getClass() != o.getClass() ) return false;

            NationName that = (NationName) o;

            return this.name.equals(that.name);
        }

        @Override
        public int hashCode() {
            return this.name.hashCode();
        }
    }

    public static final class AbbreviatedNationName {
        public static final AbbreviatedNationName EMPTY = of("");
        private final String name;

        private AbbreviatedNationName(String nationName) {
            checkNotNull(nationName);
            checkArgument(nationName.length() == 2);
            checkArgument(Character.isUpperCase(nationName.charAt(0)));
            checkArgument(Character.isUpperCase(nationName.charAt(1)));
            this.name = nationName;
        }

        public static AbbreviatedNationName of(String nationName) {
            return new AbbreviatedNationName(nationName);
        }

        public String asString() {
            return this.name;
        }

        @Override
        public boolean equals(Object o) {
            if( this == o ) return true;
            if( o == null || getClass() != o.getClass() ) return false;

            AbbreviatedNationName that = (AbbreviatedNationName) o;

            return this.name.equals(that.name);
        }

        @Override
        public int hashCode() {
            return this.name.hashCode();
        }
    }

    public static final class ProvinceName {
        public static final ProvinceName EMPTY = of("");
        private final String name;

        private ProvinceName(String provinceName) {
            this.name = checkNotNull(provinceName);
        }

        public static ProvinceName of(String provinceName) {
            return new ProvinceName(provinceName);
        }

        public String asString() {
            return this.name;
        }

        @Override
        public boolean equals(Object o) {
            if( this == o ) return true;
            if( o == null || getClass() != o.getClass() ) return false;

            ProvinceName that = (ProvinceName) o;

            return this.name.equals(that.name);
        }

        @Override
        public int hashCode() {
            return this.name.hashCode();
        }
    }

    public static final class CityName {
        public static final CityName EMPTY = of("");
        private final String name;

        private CityName(String cityName) {
            this.name = checkNotNull(cityName);
        }

        public static CityName of(String cityName) {
            return new CityName(cityName);
        }

        public String asString() {
            return this.name;
        }

        @Override
        public boolean equals(Object o) {
            if( this == o ) return true;
            if( o == null || getClass() != o.getClass() ) return false;

            CityName cityName = (CityName) o;

            return this.name.equals(cityName.name);
        }

        @Override
        public int hashCode() {
            return this.name.hashCode();
        }
    }

    public static final class GenderName {
        public static final GenderName EMPTY = of("");
        private final String name;

        private GenderName(String genderName) {
            this.name = checkNotNull(genderName);
        }

        public static GenderName of(String genderName) {
            return new GenderName(genderName);
        }

        public String asString() {
            return this.name;
        }

        @Override
        public boolean equals(Object o) {
            if( this == o ) return true;
            if( o == null || getClass() != o.getClass() ) return false;

            GenderName that = (GenderName) o;

            return this.name.equals(that.name);
        }

        @Override
        public int hashCode() {
            return this.name.hashCode();
        }
    }

    public static final class Nation {
        private final NationId nationId;
        private final NationName nationName;

        private Nation(NationId id, NationName name) {
            this.nationId = checkNotNull(id);
            this.nationName = checkNotNull(name);
        }

        public static Nation of(NationId id, NationName name) {
            return new Nation(id, name);
        }

        public NationName name() {
            return this.nationName;
        }

        public NationId id() {
            return this.nationId;
        }

        @Override
        public boolean equals(Object o) {
            if( this == o ) return true;
            if( o == null || getClass() != o.getClass() ) return false;

            Nation nation = (Nation) o;

            return this.nationId.equals(nation.nationId) && this.nationName.equals(nation.nationName);
        }

        @Override
        public int hashCode() {
            int result = this.nationName.hashCode();
            result = 31 * result + this.nationId.hashCode();
            return result;
        }
    }

    public static final class Province {
        private final ProvinceId provinceId;
        private final ProvinceName provinceName;

        private Province(ProvinceId id, ProvinceName name) {
            this.provinceId = checkNotNull(id);
            this.provinceName = checkNotNull(name);
        }

        public static Province of(ProvinceId id, ProvinceName name) {
            return new Province(id, name);
        }

        public ProvinceName name() {
            return this.provinceName;
        }

        public ProvinceId id() {
            return this.provinceId;
        }

        @Override
        public boolean equals(Object o) {
            if( this == o ) return true;
            if( o == null || getClass() != o.getClass() ) return false;

            Province province = (Province) o;

            return this.provinceId.equals(province.provinceId) && this.provinceName.equals(province.provinceName);
        }

        @Override
        public int hashCode() {
            int result = this.provinceName.hashCode();
            result = 31 * result + this.provinceId.hashCode();
            return result;
        }
    }

    public static final class City {
        private final CityId cityId;
        private final CityName cityName;

        private City(CityId id, CityName name) {
            this.cityId = checkNotNull(id);
            this.cityName = checkNotNull(name);
        }

        public static City of(CityId id, CityName name) {
            return new City(id, name);
        }

        public CityName name() {
            return this.cityName;
        }

        public CityId id() {
            return this.cityId;
        }

        @Override
        public boolean equals(Object o) {
            if( this == o ) return true;
            if( o == null || getClass() != o.getClass() ) return false;

            City city = (City) o;

            return this.cityId.equals(city.cityId) && this.cityName.equals(city.cityName);
        }

        @Override
        public int hashCode() {
            int result = this.cityName.hashCode();
            result = 31 * result + this.cityId.hashCode();
            return result;
        }
    }

    public static final class Gender {
        private final GenderId genderId;
        private final GenderName genderName;

        private Gender(GenderId id, GenderName name) {
            this.genderId = checkNotNull(id);
            this.genderName = checkNotNull(name);
        }

        public static Gender of(GenderId id, GenderName name) {
            return new Gender(id, name);
        }

        public GenderName name() {
            return this.genderName;
        }

        public GenderId id() {
            return this.genderId;
        }

        @Override
        public boolean equals(Object o) {
            if( this == o ) return true;
            if( o == null || getClass() != o.getClass() ) return false;

            Gender gender = (Gender) o;

            return this.genderId.equals(gender.genderId) && this.genderName.equals(gender.genderName);
        }

        @Override
        public int hashCode() {
            int result = this.genderName.hashCode();
            result = 31 * result + this.genderId.hashCode();
            return result;
        }
    }

    public static final class Account {
        private final AccountId accountId;
        private final AccountName accountName;

        private Account(AccountId id, AccountName name) {
            this.accountId = checkNotNull(id);
            this.accountName = checkNotNull(name);
        }

        public static Account of(AccountId id, AccountName name) {
            return new Account(id, name);
        }

        public AccountName name() {
            return this.accountName;
        }

        public AccountId id() {
            return this.accountId;
        }

        @Override
        public boolean equals(Object o) {
            if( this == o ) return true;
            if( o == null || getClass() != o.getClass() ) return false;

            Account that = (Account) o;

            return this.accountId.equals(that.accountId) && this.accountName.equals(that.accountName);
        }

        @Override
        public int hashCode() {
            int result = this.accountName.hashCode();
            result = 31 * result + this.accountId.hashCode();
            return result;
        }
    }
}
