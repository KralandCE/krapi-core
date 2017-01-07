package org.kralandce.krapi.core.bean;

import org.kralandce.krapi.core.model.kraland.MKAccount;
import org.kralandce.krapi.core.model.kraland.MKCharacter;
import org.kralandce.krapi.core.model.kraland.MKCity;
import org.kralandce.krapi.core.model.kraland.MKJob;
import org.kralandce.krapi.core.model.kraland.MKSex;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkState;

public final class ImmutableKPeople implements KPeople {

    private final MKAccount.Identifier accountIdentifier;
    private final MKAccount.Name accountName;
    private final MKAccount.AvatarURL accountAvatarURL;
    private final MKAccount.PermissionLevel accountPermissionLevel;
    private final MKSex.Identifier sexIdentifier;
    private final MKJob.Identifier jobIdentifier;
    private final MKJob.AccumulatedLevel jobAccumulatedLevel;
    private final MKJob.Area jobArea;
    private final MKCity.Identifier cityAddressIdentifier;
    private final MKCharacter.WealthLevel characterWealthLevel;

    private ImmutableKPeople(Builder builder) {
        this.accountIdentifier = builder.accountIdentifier;
        this.accountName = builder.accountName;
        this.accountAvatarURL = builder.accountAvatarURL;
        this.accountPermissionLevel = builder.accountPermissionLevel;
        this.sexIdentifier = builder.sexIdentifier;
        this.jobIdentifier = builder.jobIdentifier;
        this.jobAccumulatedLevel = builder.jobAccumulatedLevel;
        this.jobArea = builder.jobArea;
        this.cityAddressIdentifier = builder.cityAddressIdentifier;
        this.characterWealthLevel = builder.characterWealthLevel;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Optional<MKAccount.Identifier> getAccountIdentifier() {
        return Optional.ofNullable(this.accountIdentifier);
    }

    @Override
    public Optional<MKAccount.Name> getAccountName() {
        return Optional.ofNullable(this.accountName);
    }

    @Override
    public Optional<MKAccount.AvatarURL> getAccountAvatarURL() {
        return Optional.ofNullable(this.accountAvatarURL);
    }

    @Override
    public Optional<MKAccount.PermissionLevel> getAccountPermissionLevel() {
        return Optional.ofNullable(this.accountPermissionLevel);
    }

    @Override
    public Optional<MKSex.Identifier> getSexIdentifier() {
        return Optional.ofNullable(this.sexIdentifier);
    }

    @Override
    public Optional<MKJob.Identifier> getJobIdentifier() {
        return Optional.ofNullable(this.jobIdentifier);
    }

    @Override
    public Optional<MKJob.AccumulatedLevel> getJobAccumulatedLevel() {
        return Optional.ofNullable(this.jobAccumulatedLevel);
    }

    @Override
    public Optional<MKJob.Area> getJobArea() {
        return Optional.ofNullable(this.jobArea);
    }

    @Override
    public Optional<MKCity.Identifier> getCityAddressIdentifier() {
        return Optional.ofNullable(this.cityAddressIdentifier);
    }

    @Override
    public Optional<MKCharacter.WealthLevel> getCharacterWealthLevel() {
        return Optional.ofNullable(this.characterWealthLevel);
    }

    public static final class Builder {
        private MKAccount.Identifier accountIdentifier;
        private MKAccount.Name accountName;
        private MKAccount.AvatarURL accountAvatarURL;
        private MKAccount.PermissionLevel accountPermissionLevel;
        private MKSex.Identifier sexIdentifier;
        private MKJob.Identifier jobIdentifier;
        private MKJob.AccumulatedLevel jobAccumulatedLevel;
        private MKJob.Area jobArea;
        private MKCity.Identifier cityAddressIdentifier;
        private MKCharacter.WealthLevel characterWealthLevel;

        private boolean build;

        private Builder() {
            this.accountIdentifier = null;
            this.accountName = null;
            this.accountAvatarURL = null;
            this.accountPermissionLevel = null;
            this.sexIdentifier = null;
            this.jobIdentifier = null;
            this.jobAccumulatedLevel = null;
            this.jobArea = null;
            this.cityAddressIdentifier = null;
            this.characterWealthLevel = null;
            this.build = false;
        }

        public void setAccountIdentifier(MKAccount.Identifier identifier) {
            this.accountIdentifier = identifier;
        }

        public void setAccountName(MKAccount.Name name) {
            this.accountName = name;
        }

        public void setAccountAvatarURL(MKAccount.AvatarURL avatarURL) {
            this.accountAvatarURL = avatarURL;
        }

        public void setAccountPermissionLevel(MKAccount.PermissionLevel permissionLevel) {
            this.accountPermissionLevel = permissionLevel;
        }

        public void setSexIdentifier(MKSex.Identifier identifier) {
            this.sexIdentifier = identifier;
        }

        public void setJobIdentifier(MKJob.Identifier identifier) {
            this.jobIdentifier = identifier;
        }

        public void setJobAccumulatedLevel(MKJob.AccumulatedLevel accumulatedLevel) {
            this.jobAccumulatedLevel = accumulatedLevel;
        }

        public void setJobArea(MKJob.Area area) {
            this.jobArea = area;
        }

        public void setCityAddressIdentifier(MKCity.Identifier identifier) {
            this.cityAddressIdentifier = identifier;
        }

        public void setCharacterWealthLevel(MKCharacter.WealthLevel wealthLevel) {
            this.characterWealthLevel = wealthLevel;
        }

        public ImmutableKPeople build() {
            checkState(!this.build);
            this.build = true;
            return new ImmutableKPeople(this);
        }
    }
}
